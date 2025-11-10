# NW25_SocketHW1_Calculator

<h1> Socket 통신 기반 계산기 구현


<h3> 서버 요구 조건

• 서버는 동시에 여러 클라이언트를 처리할 수 있어야 함-> 이를 위해 ThreadPool과 Runnable 인터페이스 사용

<h3> 클라이언트 요구 조건

• 서버 정보(IP 주소, 포트 번호)는 클라이언트 측의 설정 텍스트 파일(예: server_info.dat)에 저장 • server_info.dat 파일에서 서버 IP와 포트번호 읽어 옴

• 설정 파일이 존재하지 않을 경우, 기본값(예: localhost, port 1234)을 지정하여 처리

<h3> 기능 요구 조건

• 덧셈, 뺄셈, 곱셈, 나눗셈의 기본 산술 연산 지원

• 응답에 따라 답변 또는 오류 메시지 전송

<h2> Protocol 상세 설명

<h3> Client -> Server 요청
• 형식 : <숫자1> <연산자> <숫자2>

• 연산자: +, -, *, /

• 종료 명령: "exit" 입력 시 연결 종료

• 공백으로 세 부분으로 나눠서 전송

1.	첫 번째 숫자 → Double로 변환

2.	연산자 → + - * / 중 하나

3.	두 번째 숫자 → Double로 변환


<h3> Server -> Client 응답
  
• 성공 시 결과 전송: RESULT <계산 결과>

• 오류 시 전송되는 메시지 :

오류 유형 메시지 설명

잘못된 인수 개수 | ERROR ARG_COUNT -> "a + b" 형식이 아님

숫자 변환 실패 | ERROR INVALID_NUMBER -> 숫자가 아닌 값 입력

0으로 나누기 | ERROR DIV_BY_ZERO -> b가 0일 때 / 연산

잘못된 연산자 | ERROR UNKNOWN_OPERATOR -> + - * / 외 연산자

<h3> 종료 프로토콜
  
• 클라이언트 종료: "exit" 입력

• 서버는 요청 수신 시 문자열이 "exit"인지 확인 → 루프 종료 → 스레드 종료

• 클라이언트와 서버 모두 연결 종료
