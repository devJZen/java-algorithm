import os
import sys
import smtplib
import yaml
from datetime import datetime, timedelta, timezone
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from pathlib import Path

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from kakao_notify import notify_all as kakao_notify_all

KST = timezone(timedelta(hours=9))
DEADLINES_FILE = Path(__file__).parents[1] / "deadlines.yml"


def load_deadlines() -> dict:
    with open(DEADLINES_FILE, encoding="utf-8") as f:
        return yaml.safe_load(f)


def check_upcoming(config: dict) -> list[dict]:
    now = datetime.now(KST)
    upcoming = []

    for assignment in config.get("assignments", []):
        due = datetime.fromisoformat(assignment["due"]).replace(tzinfo=KST)
        hours_left = (due - now).total_seconds() / 3600

        notify_hours = config.get("notify_before_hours", [24, 3])
        if isinstance(notify_hours, int):
            notify_hours = [notify_hours]

        for threshold in notify_hours:
            if 0 < hours_left <= threshold:
                upcoming.append({**assignment, "hours_left": round(hours_left, 1), "due_dt": due})
                break

    return upcoming


# ── 이메일 ────────────────────────────────────────────────────────────────────

def build_email_html(assignment: dict) -> str:
    hours = assignment["hours_left"]
    due_str = assignment["due_dt"].strftime("%Y년 %m월 %d일 %H:%M KST")
    urgency = "🚨" if hours <= 3 else "⏰"

    return f"""
<html><body style="font-family: sans-serif; max-width: 600px; margin: 0 auto; padding: 20px;">
  <h2>{urgency} 알고리즘 스터디 과제 마감 알림</h2>
  <hr>
  <table style="width: 100%; border-collapse: collapse;">
    <tr>
      <td style="padding: 8px; font-weight: bold; width: 120px;">과제</td>
      <td style="padding: 8px;">{assignment['title']}</td>
    </tr>
    <tr style="background: #f5f5f5;">
      <td style="padding: 8px; font-weight: bold;">마감일시</td>
      <td style="padding: 8px; color: #d32f2f;"><strong>{due_str}</strong></td>
    </tr>
    <tr>
      <td style="padding: 8px; font-weight: bold;">남은 시간</td>
      <td style="padding: 8px; color: #e65100;"><strong>{hours}시간</strong></td>
    </tr>
  </table>
  <br>
  <p style="background: #fff3e0; padding: 12px; border-radius: 4px; border-left: 4px solid #ff9800;">
    아직 제출하지 않았다면 지금 바로 Push 해주세요!<br>
    제출 후 GitHub Actions에서 자동 채점 결과를 확인할 수 있습니다.
  </p>
  <hr>
  <small style="color: #999;">java-algorithm study · GitHub Actions</small>
</body></html>
"""


def send_email(to: str, subject: str, html: str, gmail_user: str, app_password: str):
    msg = MIMEMultipart("alternative")
    msg["From"] = gmail_user
    msg["To"] = to
    msg["Subject"] = subject
    msg.attach(MIMEText(html, "html", "utf-8"))

    with smtplib.SMTP_SSL("smtp.gmail.com", 465) as server:
        server.login(gmail_user, app_password)
        server.sendmail(gmail_user, to, msg.as_string())


# ── 카카오 ────────────────────────────────────────────────────────────────────

def build_kakao_message(assignment: dict) -> str:
    hours = assignment["hours_left"]
    due_str = assignment["due_dt"].strftime("%-m월 %-d일 %H:%M")
    urgency = "🚨" if hours <= 3 else "⏰"
    title = assignment["title"][:30] + ("…" if len(assignment["title"]) > 30 else "")

    return (
        f"{urgency} 마감 {hours}시간 전 | 알고리즘 스터디\n\n"
        f"📌 {title}\n"
        f"🕛 {due_str} KST\n\n"
        f"아직 제출 안 하셨나요? 지금 바로 Push!"
    )


# ── 메인 ─────────────────────────────────────────────────────────────────────

def main():
    if not DEADLINES_FILE.exists():
        print(f"{DEADLINES_FILE} 파일이 없습니다.")
        sys.exit(0)

    config = load_deadlines()
    notifications = config.get("notifications", {})
    email_enabled = notifications.get("email", {}).get("enabled", True)
    kakao_enabled = notifications.get("kakao", {}).get("enabled", False)

    upcoming = check_upcoming(config)
    if not upcoming:
        print("발송할 알림 없음 (마감 임박 과제 없음)")
        sys.exit(0)

    repo_url = f"https://github.com/{os.environ.get('GITHUB_REPOSITORY', '')}"

    for assignment in upcoming:
        hours = assignment["hours_left"]
        print(f"\n[{assignment['title']}] 마감 {hours}시간 전")

        # 이메일
        if email_enabled:
            gmail_user = os.environ.get("GMAIL_USER", "")
            gmail_password = os.environ.get("GMAIL_APP_PASSWORD", "")
            if gmail_user and gmail_password:
                subject = f"[알고리즘 스터디] ⏰ '{assignment['title']}' 마감 {hours}시간 전"
                html = build_email_html(assignment)
                for recipient in config.get("recipients", []):
                    try:
                        send_email(recipient, subject, html, gmail_user, gmail_password)
                        print(f"  이메일 발송 완료: {recipient}")
                    except Exception as e:
                        print(f"  이메일 발송 실패 ({recipient}): {e}")
            else:
                print("  이메일: GMAIL_USER/GMAIL_APP_PASSWORD 미설정, 건너뜀")

        # 카카오
        if kakao_enabled:
            msg = build_kakao_message(assignment)
            kakao_notify_all(msg, repo_url)
        else:
            print("  카카오: deadlines.yml notifications.kakao.enabled: false, 건너뜀")


if __name__ == "__main__":
    main()
