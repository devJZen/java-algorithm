import os
import sys
import time
import subprocess
import json
from pathlib import Path
from google import genai

CRITERIA = [
    ("정확성",        "알고리즘 로직이 테스트 케이스를 올바르게 통과하는가?"),
    ("시간복잡도",    "문제에 적합한 Big-O를 사용했는가? 불필요하게 느린 구현이 없는가?"),
    ("알고리즘이해도","해당 자료구조/알고리즘의 핵심 특성을 올바르게 구현했는가?"),
    ("엣지케이스",    "빈 배열, null, 경계값 등 예외 상황을 처리했는가?"),
    ("코드품질",      "변수명이 명확하고 가독성이 좋으며 불필요한 중복이 없는가?"),
]

MODEL = "gemini-2.0-flash"
MAX_FILE_BYTES = 100_000  # 100 KB — 초과 시 채점 건너뜀


def get_changed_java_files():
    base = os.environ.get("BASE_SHA", "")
    head = os.environ.get("HEAD_SHA", "HEAD")

    if not base or base == "0000000000000000000000000000000000000000":
        result = subprocess.run(
            ["git", "diff", "--name-only", "HEAD~1", "HEAD"],
            capture_output=True, text=True
        )
    else:
        result = subprocess.run(
            ["git", "diff", "--name-only", base, head],
            capture_output=True, text=True
        )

    files = [
        f for f in result.stdout.strip().split("\n")
        if f.endswith(".java") and "/src/main/" in f
    ]
    return [f for f in files if os.path.exists(f)]


def find_test_files(solution_path: str) -> list[str]:
    """src/main/Foo.java → src/test/study/ 내 모든 DayXTest.java 반환."""
    p = Path(solution_path)
    test_dir = p.parents[1] / "test" / "study"
    if test_dir.exists():
        return sorted(str(f) for f in test_dir.glob("*.java"))
    return []


def sanitize_reason(text: str) -> str:
    """마크다운 테이블 셀 안에서 파이프와 줄바꿈이 깨지지 않도록 이스케이프."""
    return str(text).replace("|", "\\|").replace("\n", " ").strip()


def validate_grade(data: dict) -> dict:
    """Gemini 응답이 기대 스키마를 따르는지 검증하고, 누락 필드를 채워 반환."""
    expected_criteria = {name for name, _ in CRITERIA}
    results = data.get("results")

    if not isinstance(results, list) or len(results) != len(CRITERIA):
        got = len(results) if isinstance(results, list) else type(results)
        raise ValueError(f"results 항목 수 불일치: {got} != {len(CRITERIA)}")

    for r in results:
        if not isinstance(r, dict):
            raise ValueError(f"result 항목이 dict가 아닙니다: {r}")
        if r.get("criterion") not in expected_criteria:
            raise ValueError(f"알 수 없는 criterion: {r.get('criterion')!r}")
        if r.get("status") not in ("PASS", "FAIL"):
            raise ValueError(f"status는 PASS/FAIL이어야 합니다: {r.get('status')!r}")
        r.setdefault("reason", "")

    if data.get("overall") not in ("PASS", "FAIL"):
        raise ValueError(f"overall은 PASS/FAIL이어야 합니다: {data.get('overall')!r}")
    data.setdefault("summary", "")
    return data


def grade_with_gemini(solution_code: str, test_code: str, filename: str) -> dict:
    client = genai.Client(api_key=os.environ["GEMINI_API_KEY"])

    criteria_text = "\n".join(
        f"{i+1}. {name}: {desc}"
        for i, (name, desc) in enumerate(CRITERIA)
    )

    prompt = f"""당신은 알고리즘 스터디의 코드 채점 에이전트입니다.
아래 Java 코드를 5가지 기준으로 채점하세요.

## 솔루션 코드 ({filename})
```java
{solution_code}
```

## 테스트 파일 (참고용)
```java
{test_code}
```

## 채점 기준 (중요도 순)
{criteria_text}

## 응답 형식
반드시 아래 JSON 형식만 반환하세요. 다른 텍스트는 포함하지 마세요.
{{
  "results": [
    {{"criterion": "정확성",        "status": "PASS", "reason": "이유를 한국어 1-2문장으로"}},
    {{"criterion": "시간복잡도",    "status": "PASS", "reason": "..."}},
    {{"criterion": "알고리즘이해도","status": "PASS", "reason": "..."}},
    {{"criterion": "엣지케이스",    "status": "FAIL", "reason": "..."}},
    {{"criterion": "코드품질",      "status": "PASS", "reason": "..."}}
  ],
  "overall": "PASS",
  "summary": "전체 피드백 2-3문장"
}}"""

    response = client.models.generate_content(
        model=MODEL,
        contents=prompt,
        config={"response_mime_type": "application/json"},
    )

    raw = json.loads(response.text)
    return validate_grade(raw)


def grade_with_retry(solution_code: str, test_code: str, filepath: str, max_retries: int = 3) -> dict | None:
    """Gemini API 호출 실패 시 지수 백오프로 최대 max_retries회 재시도."""
    for attempt in range(max_retries):
        try:
            return grade_with_gemini(solution_code, test_code, filepath)
        except Exception as e:
            if attempt == max_retries - 1:
                print(f"  Gemini API 오류 ({max_retries}회 시도 모두 실패): {e}")
                return None
            wait = 2 ** attempt  # 1초, 2초
            print(f"  Gemini API 오류 ({e}), {wait}초 후 재시도 ({attempt + 1}/{max_retries})...")
            time.sleep(wait)
    return None


def build_kakao_message(filename: str, grade: dict, actor: str) -> str:
    icon = {"PASS": "✅", "FAIL": "❌"}
    overall = grade.get("overall", "FAIL")
    criteria_line = " ".join(
        f"{icon.get(r['status'], '❓')}{r['criterion']}"
        for r in grade.get("results", [])
    )
    return (
        f"🎯 채점 완료 ({actor})\n"
        f"📄 {Path(filename).name}\n\n"
        f"{criteria_line}\n\n"
        f"판정: {icon.get(overall, '❓')} {overall}"
    )


def build_report(filename: str, grade: dict, actor: str) -> str:
    status_icon = {"PASS": "✅", "FAIL": "❌"}
    overall = grade.get("overall", "FAIL")

    lines = [
        f"## {status_icon.get(overall, '❓')} 알고리즘 채점 결과 — `{Path(filename).name}`",
        f"> 제출자: **{actor}**\n",
        "| # | 기준 | 결과 | 피드백 |",
        "|---|------|------|--------|",
    ]

    for i, r in enumerate(grade.get("results", []), 1):
        icon = status_icon.get(r.get("status", "FAIL"), "❓")
        lines.append(
            f"| {i} | {r['criterion']} | {icon} {r['status']} | {sanitize_reason(r.get('reason', ''))} |"
        )

    lines += [
        "",
        f"**종합 판정: {status_icon.get(overall, '❓')} {overall}**",
        "",
        f"> {sanitize_reason(grade.get('summary', ''))}",
        "",
        f"<sub>Powered by Gemini ({MODEL}) · Google AI Studio free tier</sub>",
    ]

    return "\n".join(lines)


def main():
    if not os.environ.get("GEMINI_API_KEY"):
        print("GEMINI_API_KEY secret이 설정되지 않았습니다.")
        sys.exit(0)

    changed = get_changed_java_files()
    if not changed:
        print("채점할 Java 파일이 없습니다.")
        sys.exit(0)

    actor = os.environ.get("GITHUB_ACTOR", "unknown")
    all_reports = []
    kakao_messages = []
    any_fail = False

    for filepath in changed:
        print(f"\n채점 중: {filepath}")

        # 대용량 파일 가드
        file_size = os.path.getsize(filepath)
        if file_size > MAX_FILE_BYTES:
            print(f"  파일이 너무 큽니다 ({file_size:,} bytes > {MAX_FILE_BYTES:,} bytes). 채점 건너뜁니다.")
            continue

        with open(filepath, encoding="utf-8") as f:
            solution_code = f.read()

        # 테스트 파일 전체 로드 (여러 개일 경우 모두 포함)
        test_files = find_test_files(filepath)
        test_code = ""
        if test_files:
            parts = []
            for tf in test_files:
                with open(tf, encoding="utf-8") as f:
                    parts.append(f"// {Path(tf).name}\n{f.read()}")
            test_code = "\n\n".join(parts)
            names = ", ".join(Path(tf).name for tf in test_files)
            print(f"  테스트 파일: {names}")
        else:
            print("  테스트 파일 없음 (솔루션 코드만으로 채점)")

        grade = grade_with_retry(solution_code, test_code, filepath)
        if grade is None:
            continue

        report = build_report(filepath, grade, actor)
        all_reports.append(report)
        kakao_messages.append(build_kakao_message(filepath, grade, actor))

        overall = grade.get("overall", "FAIL")
        print(f"  결과: {overall}")
        for r in grade.get("results", []):
            icon = "✅" if r["status"] == "PASS" else "❌"
            print(f"    {icon} {r['criterion']}: {r['reason']}")

        if overall == "FAIL":
            any_fail = True

    if all_reports:
        full_report = "\n\n---\n\n".join(all_reports)
        with open("grade_report.md", "w", encoding="utf-8") as f:
            f.write(full_report)
        print("\ngrade_report.md 저장 완료")

    if kakao_messages:
        with open("grade_kakao_msg.txt", "w", encoding="utf-8") as f:
            f.write("\n\n".join(kakao_messages))
        print("grade_kakao_msg.txt 저장 완료")

    # FAIL이 있어도 워크플로우는 통과 (채점 결과는 PR 코멘트로 확인)
    # 워크플로우 자체를 실패시키려면 아래 주석을 해제하세요:
    # if any_fail:
    #     sys.exit(1)


if __name__ == "__main__":
    main()
