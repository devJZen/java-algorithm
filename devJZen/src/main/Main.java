/*
 * Date: 2026-06-02
 * @Author: devJZen
 * Note: 작성자명 바꿔주세요.
 * Assistant: 패키지 및 라이브러리 설정 Gemini, ChatGPT
 * */

// 1일차 Java setting
//
// 1. ubuntu zip, unzip 라이브러리
// sudo apt-get install zip unzip
// sudo apt update && sudo apt install -y unzip zip
// 
// 2. ubuntu java sdkman 자바 sdk 라이브러리
// curl -s "https://get.sdkman.io" | bash
// 
// 3. 환경변수 setting
// source "$HOME/.sdkman/bin/sdkman-init.sh"
// 
// 4. java21은 Temurin으로 설치한다. 무료 표준.
// sdk install java 21.0.6-tem
// 
// 5. version 확인
// java -version

//Java 는 public class가 파일의 entry point라고 생각한다. 파일명과 public class의 이름을 일치 시킨다.
//Wiki: Java_syntax의 예제를 참고했다.
public class Main {
	public static void main(String[] args) {
		System.out.println("Hello, World!");
	}
}

