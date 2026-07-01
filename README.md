# java-algorithm

모든 파일은 MD 파일로 작성합니다.
과제 할당은 Discord로 관리합니다. 실습 검토는 Claude가 합니다.(알고리즘 채점)

기간: `2026-06-02` ~ `점검으로 미룹니다.`

IDE: neovim

LSP: jdtls, nvim-jdtls(nvim 사용시 필수임), lemminx(Maven/Gradle 설정파일 용도)

Java version: 21 Temurin

## Notice

현직 개발자 분들 또는 비전공자 분들, 학부생이지만 java를 공부하려니 막막한 분들, 취준생 분들 모두 놓쳤던 개념들을 채워넣는 용도로 쓰셨으면 합니다.
AI를 활용하기 이전에 Java를 공부할 때에는 직접 작성해보는 것을 추천 드립니다.
Git, GitHub에 대한 온보딩은 인프런의 훌륭한 코딩 강사분들의 강의를 참고해주세요.
저작권은 Wiki와 Oracle의 원본을 따릅니다. 문제가 되는 사항이 있다면 디스코드 devJzen으로 연락주세요.

## MD 문법
MD(MarkDown)문법이 익숙하지 않은 분들을 위해 간단히 설명합니다.

`파일.확장자` : 파일 1부를 설명할 때 또는 특정 파일명, 단위 및 코드를 설명할 때 사용합니다.

**글자의 굵기 강조** : 강조할 언어를 `**텍스트**`로 감쌉니다.

_글자의 기울임_ : 강조할 언어를 `_텍스트_`로 감쌉니다.

### 제목 3 : 해시의 개수가 작을수록 글자가 크게 강조됩니다.

> 인용구 : 강조하고 싶을 때 사용합니다.

---

`---`: 글을 구분하고 싶을 때 사용합니다.

``` java
코드라인을 강조하고 싶을 때 사용합니다.
```
"```" : 열고 닫기로 코드라인을 강조할 때 사용하세요.

모르는 개념을 사용할 때에는 AI를 활용합니다.
**사용한 AI 모델과 출처를 기입합니다.**

# Java 컴파일 과정

1. `.java` 소스 코드 작성
2. 자바 컴파일러가 번역, JDK에 포함된 javac가 소스 코드를 읽는다.
3. Bytecode로 변환
4. 바이트코드 생성, 성공시 `.class` 확장자 파일이 생성됨, `.class`의 바이트코드는 운영체제(Operating System)에 종속되지 않는 독립적인 코드다. 가상 머신이 이해할 수 있는 중간 단계의 언어다. 기계어는 Execution에서 실행됨.
5. 자바 가상 머신(Java Virtual Machine) 작동, Class Loader(실행에 필요한 클래스 파일을 메모리에 올림) - Execution Engine(바이트코드를 Binary Code로 번역하며 실행) - JIT(Just-In-Time)컴파일 방식이 특징이다.

**학습 도움:** [Google Gemini](https://gemini.google.com/) - Java 컴파일 과정 및 JVM 구조 학습

# Java 기본 문법

[자바 (프로그래밍 언어), Wiki](https://ko.wikipedia.org/wiki/%EC%9E%90%EB%B0%94_(%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D_%EC%96%B8%EC%96%B4)),
[Java Syntax](https://www.w3schools.com/java/java_syntax.asp),
[자바 문법,Wiki](https://en.wikipedia.org/wiki/Java_syntax)

# Java의 기본 학습

[오라클 공식문서](https://docs.oracle.com/javase/tutorial/)
[JLS](https://docs.oracle.com/javase/specs/)
[이펙티브 자바](https://product.kyobobook.co.kr/detail/S000001033066)

책은 웬만하면 구매하여 공부하고, 돈이 없는 학생인 경우에는 pdf로 읽고 나중에 책 원본을 구매하도록 하세요. 중고 서점을 이용하는 것도 방법입니다.
오라클의 JLS는 무료로 배포된 자바 학습서로 훌륭합니다.
이펙티브 자바는 9까지 대응됩니다. java 21을 학습하는데에 있어서 다양한 자료를 찾아보는 것을 추천드립니다.

# 자료구조와 기본 알고리즘

only for study
reference credit:

- [백준](https://www.acmicpc.net/)
- [leet code](http://leetcode.com/)
- [english wikipedia sorting and search example](https://en.wikipedia.org/wiki/Bubble_sort)

## 예제1: 자료구조와 알고리즘이란?

Questions
- 자료구조, 알고리즘의 어원과 정의를 간단히 설명하시오.
- 알고리즘의 역사를 아는만큼 서술하시오.
- 알고리즘의 표현을 아는만큼 서술하시오.
- 알고리즘의 분석, 복잡성이란 무엇인가?

예제인만큼 정답은 스스로 확인하고 채점해보세요.

# 학습 진행도

> 구현 제출은 각 참여자 폴더의 `src/main/` 하위에 작성하세요. (예: `devJZen/src/main/`, `IT/src/main/`)

## 1회차 (2026-06-02) — [Summary](devJZen/docs/2026-06-02-Summary.md)

분류 | 명명 | devJZen | IT
--- | --- | --- | ---
알고리즘 | 버블 정렬 | [링크]() | [링크]()
자료구조 | 자료형: 배열 | [링크]() | [링크]()
자료구조 | 자료형: 튜플(레코드) | [링크]() | [링크]()
자료구조 | 자료형: 연결 리스트 | [링크]() | [링크]()
자료구조 | 자료형: 원형 연결 리스트 | [링크]() | [링크]()
자료구조 | 자료형: 이중 연결 리스트 | [링크]() | [링크]()
자료구조 | 자료형: 해시 테이블 | [링크]() | [링크]()

## 2회차 (2026-06-03) — [Summary](devJZen/docs/2026-06-03-Summary.md)

분류 | 명명 | devJZen | IT
--- | --- | --- | ---
알고리즘 | 삽입 정렬 | [링크]() | [링크]()
알고리즘 | 선택 정렬 | [링크]() | [링크]()
알고리즘 | 퀵 정렬 | [링크]() | [링크]()
자료구조 | 비선형구조: 그래프 | [링크]() | [링크]()
자료구조 | 비선형구조: 트리 | [링크]() | [링크]()
자료구조 | 비선형구조: 이진 트리 | [링크]() | [링크]()
자료구조 | 비선형구조: 힙 | [링크]() | [링크]()

## 3회차 (2026-06-04) — [Summary](devJZen/docs/2026-06-04-Summary.md)

분류 | 명명 | devJZen | IT
--- | --- | --- | ---
알고리즘 | 병합 정렬 | [링크]() | [링크]()
알고리즘 | 카운팅 정렬 | [링크]() | [링크]()
알고리즘 | 힙 정렬 | [링크]() | [링크]()
자료구조 | 선형구조: 스택 | [링크]() | [링크]()
자료구조 | 선형구조: 큐 | [링크]() | [링크]()
자료구조 | 선형구조: 덱 | [링크]() | [링크]()

## 4회차 (2026-06-05) — [Summary](devJZen/docs/2026-06-05-Summary.md)

분류 | 명명 | devJZen | IT
--- | --- | --- | ---
알고리즘 | 셸 정렬 | [링크]() | [링크]()
알고리즘 | 기수 정렬 | [링크]() | [링크]()
알고리즘 | Tim 정렬 | [링크]() | [링크]()

# 과제 확인

날짜/회차 | 내용 | 확인 | 비고
--- | --- | --- | ---
2026-06-02/1회차 | 프로젝트 세팅, 버블정렬 | IT | 자료구조:자료형 부터 2회차 시작
2026-06-02/1회차 | 1-2회차 학습준비 및 검토(누락으로 git 설정 방법부터 자료공유) | devJZen | 3-4회차 학습 준비, neovim 세팅이 생각보다 오래걸림. java 컴파일 속도 높이는 방법 찾아보기
2026-06-04/2회차 | - | IT | 프로젝트 정비로 일정을 하루 늦췄습니다.
2026-06-04/2회차 | - | devJZen | 프로젝트 정비로 일정을 하루 늦췄습니다.
2026-06-05/3회차 |  | IT | 
2026-06-06/3회차 |  | devJZen | 
2026-06-06/4회차 |  | IT | 
2026-06-06/4회차 |  | devJZen | 

# 과제 회고

날짜/회차 | 회고 및 내용 | 작성자 | 비고
--- | --- | --- | ---
2026-06-02/1회차 | 프로젝트 준비를 완벽하지 못 했던게 아쉽고 온보딩을 위해서 봇을 준비하는 것도 나쁘지 않겠다고 느꼈다. 알고리즘 공부에 백준과 리트코드가 참 좋다는 것이고 이제껏 다른 개발자 분들의 풍요로움으로 lua 세팅에 어려움이 없었으나 java cfg을 직접 해보니 어려웠다. | devJZen | 2회차-3회차 학습 준비
2026-06-0/1회차 | 내용 쓰시면 됩니다. | IT | git 학습 중이어서 대신 작성합니다.
2026-06-0/2회차 | - | IT | 프로젝트 정비로 일정을 하루 늦췄습니다.
2026-06-0/2회차 | - | devJZen | 프로젝트 정비로 일정을 하루 늦췄습니다.
2026-06-0/3회차 |  | IT | 
2026-06-0/3회차 |  | devJZen | 
2026-06-0/4회차 |  | IT | 
2026-06-0/4회차 |  | devJZen | 

---

# GitHub Actions CI/CD

## 프로젝트 구조

```
java-algorithm/
├── .github/
│   ├── workflows/
│   │   ├── grade-algorithm.yml        # Java 파일 Push/PR 시 자동 채점
│   │   └── deadline-reminder.yml      # 매일 09:00 KST 마감 알림
│   ├── scripts/
│   │   ├── grade_code.py              # Gemini AI 채점 에이전트
│   │   ├── kakao_notify.py            # 카카오톡 알림 유틸리티
│   │   └── send_deadline_reminder.py  # 마감 알림 발송
│   └── deadlines.yml                  # 과제 마감 및 알림 채널 설정
├── IT/
│   ├── src/main/                      # 구현 제출 (Java)
│   └── src/test/study/                # Day별 테스트 파일
└── devJZen/
    ├── src/main/                      # 구현 제출 (Java)
    └── src/test/study/                # Day별 테스트 파일
```

## Gemini 채점 에이전트

`src/main/*.java` 파일이 Push / PR되면 GitHub Actions가 자동으로:
1. `google-genai` Python SDK로 Gemini 2.0 Flash API를 단일 호출
2. 솔루션 코드 + 테스트 파일을 프롬프트에 포함해 5가지 기준으로 PASS/FAIL 채점
3. 결과를 카카오톡으로 발송하고 PR 코멘트로 첨부

**채점 기준** (중요도 순):

\# | 기준 | 설명
---|------|------
1 | 정확성 | 알고리즘 로직이 테스트 케이스를 올바르게 통과하는가?
2 | 시간복잡도 | 문제에 적합한 Big-O를 사용했는가?
3 | 알고리즘이해도 | 핵심 자료구조/알고리즘을 올바르게 구현했는가?
4 | 엣지케이스 | 빈 배열, null, 경계값 등 예외 상황을 처리했는가?
5 | 코드품질 | 변수명이 명확하고 가독성이 좋으며 중복이 없는가?

**API 통합 방식 (`.github/scripts/grade_code.py`)**:

```python
from google import genai

client = genai.Client(api_key=os.environ["GEMINI_API_KEY"])
response = client.models.generate_content(
    model="gemini-2.0-flash",
    contents=prompt,                                    # 솔루션 코드 + 채점 기준 포함
    config={"response_mime_type": "application/json"},  # 구조화된 JSON 응답 강제
)
grade = json.loads(response.text)
# → {"results": [{"criterion": "정확성", "status": "PASS", "reason": "..."},...],
#    "overall": "PASS", "summary": "..."}
```

## 마감 알림 에이전트

`.github/deadlines.yml`에 과제 마감일을 등록하면 매일 09:00 KST(UTC 00:00)에 24시간·3시간 전 알림 자동 발송.

**카카오톡 OAuth2 흐름 (`.github/scripts/kakao_notify.py`)**:
1. `KAKAO_REFRESH_TOKENS`로 Kakao 토큰 엔드포인트에 POST → 단기 Access Token 발급
2. 카카오톡 "나에게 보내기" API로 메시지 발송 (200자 제한 자동 적용)

## 필요한 GitHub Secrets

Secret | 용도
-------|------
`GEMINI_API_KEY` | Google AI Studio API 키 (채점)
`KAKAO_CLIENT_ID` | 카카오 앱 Client ID
`KAKAO_REFRESH_TOKENS` | 콤마로 구분된 Refresh Token 목록
`GMAIL_USER` | Gmail 계정 (이메일 알림, 현재 비활성)
`GMAIL_APP_PASSWORD` | Gmail 앱 비밀번호 (현재 비활성)

---

## 보안 유의사항

### Refresh Token 유출 위험
`KAKAO_REFRESH_TOKENS`는 **장기 토큰**이다. 유출 시 카카오 계정으로 메시지 발송이 가능하다.
GitHub Secrets는 로그에서 자동 마스킹되지만, 스크립트 내에서 토큰 값을 `print()`하면 Actions 로그에 그대로 노출된다. `kakao_notify.py`를 수정할 때 토큰 변수를 출력하지 않도록 주의한다.

### 소스 코드 외부 전송
`src/main/*.java` 전체 내용이 Google(Gemini API)로 전송된다. 이 프로젝트는 학습용 코드이므로 문제없으나, 업무 코드나 개인정보가 포함된 내용을 올리면 안 된다.

### Prompt Injection (PR 코멘트)
Gemini 응답이 검증 없이 `grade_report.md`로 저장되고 PR 코멘트에 그대로 게시된다. Gemini가 악의적인 마크다운을 반환할 경우 코멘트가 오염될 수 있다. 현재는 `response_mime_type: "application/json"`으로 JSON만 강제하고 있어 위험도가 낮지만, 이유 필드(`reason`)의 내용은 필터링 없이 출력되므로 인지가 필요하다.

### 비활성 시크릿 노출
`email.enabled: false` 상태에서도 `GMAIL_USER`·`GMAIL_APP_PASSWORD`가 워크플로우 env에 주입된다. 사용하지 않는 시크릿은 워크플로우 step에서 제외하는 것이 원칙이다.

---

## 알려진 결점 및 개선 방향

결점 | 대응 | 위치
-----|------|------
**Kakao Refresh Token 만료** | `invalid_grant` 오류 감지 시 재발급 안내 메시지 출력 (토큰 자동 갱신은 카카오 정책상 불가) | `kakao_notify.py` `get_access_token()`
**Gemini 응답 스키마 미검증** | `validate_grade()`로 criterion·status·overall 타입 및 값 검증. 누락 필드 자동 보완 | `grade_code.py` `validate_grade()`
**테스트 파일 매핑 불확실** | `find_test_files()`로 `study/` 내 모든 DayNTest.java를 수집해 Gemini 프롬프트에 전부 포함 | `grade_code.py` `find_test_files()`
**재시도 로직 없음** | `grade_with_retry()`에서 최대 3회 지수 백오프 재시도 (1초 → 2초 대기) | `grade_code.py` `grade_with_retry()`
**대용량 파일 미처리** | `MAX_FILE_BYTES = 100_000` (100 KB) 초과 시 채점 건너뜀 + 경고 출력 | `grade_code.py` `main()`

---

# 레퍼런스

## 공식 문서
- [Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/)
- [Java Language Specification (JLS)](https://docs.oracle.com/javase/specs/)
- [Java SE 21 API Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/index.html)

## 알고리즘 & 자료구조
- [백준 온라인 저지](https://www.acmicpc.net/)
- [LeetCode](https://leetcode.com/)
- [Bubble Sort, Wikipedia (English)](https://en.wikipedia.org/wiki/Bubble_sort)

## 자바 학습 자료
- [자바 (프로그래밍 언어), 위키백과](https://ko.wikipedia.org/wiki/%EC%9E%90%EB%B0%94_(%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D_%EC%96%B8%EC%96%B4))
- [Java Syntax, W3Schools](https://www.w3schools.com/java/java_syntax.asp)
- [Java Syntax, Wikipedia](https://en.wikipedia.org/wiki/Java_syntax)
- [이펙티브 자바 (교보문고)](https://product.kyobobook.co.kr/detail/S000001033066)

---

# 개정 이력

날짜 | 내용 | 작성자
--- | --- | ---
2026-06-03 | devJZen/docs 저작권 정비 (Oracle 링크 출처 추가, 문제 설명 직접 서술로 교체), 06-03 Summary 날짜·참고자료 추가, 06-04 Summary 신규 생성 (스택/큐/덱, 병합·카운팅·힙 정렬), README 레퍼런스 정리 | devJZen
2026-06-03 | 06-05 Summary 신규 생성 (셸·기수·Tim 정렬, 전체 정렬 비교표), study/Example.java 업데이트, README 학습 진행도 1~4회차 구조화 (Summary 링크 + 구현 제출 칸), 과제 확인·회고 2~4회차 행 추가 | devJZen
2026-06-15 | 자료구조 및 알고리즘 학습방법 재정비(백준, leetcode 관련 문제 제거), claude 또는 gemini로 개선 방안 또는 채점 방법에 대해서 생각해보기 | devJZen
2026-06-22 | 채점방법 추가 | devJZen

