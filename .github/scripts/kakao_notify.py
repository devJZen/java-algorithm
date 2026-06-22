"""
카카오톡 나에게 보내기 API 유틸리티

필수 환경변수:
  KAKAO_CLIENT_ID       - 카카오 개발자 콘솔 REST API 키
  KAKAO_REFRESH_TOKENS  - 쉼표 구분 Refresh Token 목록 (스터디원 각자 발급)

단독 실행:
  python kakao_notify.py <메시지 또는 파일경로> [링크URL]

임포트 사용:
  from kakao_notify import notify_all
  notify_all("메시지", "https://github.com/...")
"""
import os
import sys
import json
import requests

_TOKEN_URL = "https://kauth.kakao.com/oauth/token"
_MESSAGE_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send"


def get_access_token(client_id: str, refresh_token: str) -> str:
    resp = requests.post(_TOKEN_URL, data={
        "grant_type": "refresh_token",
        "client_id": client_id,
        "refresh_token": refresh_token,
    }, timeout=10)
    data = resp.json()
    if "access_token" not in data:
        error = data.get("error", "unknown")
        desc = data.get("error_description", "")
        if error == "invalid_grant":
            raise RuntimeError(
                "Kakao Refresh Token이 만료되었거나 무효입니다. "
                "카카오 개발자 콘솔(developers.kakao.com)에서 토큰을 재발급하고 "
                "GitHub Secrets의 KAKAO_REFRESH_TOKENS를 갱신하세요."
            )
        raise RuntimeError(f"토큰 갱신 실패: {error} — {desc}")
    return data["access_token"]


def send_message(access_token: str, text: str, link_url: str = "https://github.com") -> bool:
    resp = requests.post(
        _MESSAGE_URL,
        headers={"Authorization": f"Bearer {access_token}"},
        data={
            "template_object": json.dumps({
                "object_type": "text",
                "text": text[:200],
                "link": {"web_url": link_url, "mobile_web_url": link_url},
            })
        },
        timeout=10,
    )
    return resp.json().get("result_code") == 0


def notify_all(text: str, link_url: str = "https://github.com") -> int:
    """등록된 모든 수신자에게 발송. 성공한 수를 반환."""
    client_id = os.environ.get("KAKAO_CLIENT_ID", "").strip()
    tokens_raw = os.environ.get("KAKAO_REFRESH_TOKENS", "").strip()

    if not client_id or not tokens_raw:
        print("  카카오 설정 없음 (KAKAO_CLIENT_ID, KAKAO_REFRESH_TOKENS 확인)")
        return 0

    tokens = [t.strip() for t in tokens_raw.split(",") if t.strip()]
    success = 0

    for token in tokens:
        label = token[:8] + "..."
        try:
            access_token = get_access_token(client_id, token)
            ok = send_message(access_token, text, link_url)
            status = "완료" if ok else "실패"
            print(f"  카카오 발송 {status}: {label}")
            if ok:
                success += 1
        except Exception as e:
            print(f"  카카오 오류 ({label}): {e}")

    return success


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("사용법: python kakao_notify.py <메시지 또는 파일경로> [링크URL]")
        sys.exit(1)

    arg = sys.argv[1]
    message = open(arg, encoding="utf-8").read().strip() if os.path.exists(arg) else arg
    link = sys.argv[2] if len(sys.argv) > 2 else "https://github.com"

    count = notify_all(message, link)
    print(f"총 {count}명에게 발송 완료")
