package server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    // 각 클라이언트와 연결된 소켓을 저장할 필드

    public ClientHandler(Socket socket) {
        this.socket = socket;
        // 생성자에서 소켓을 받아 필드에 저장
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
                // 클라이언트와 통신할 입출력 스트림 생성
                // BufferedReader: 클라이언트로부터 문자열 읽기
                // PrintWriter: 클라이언트로 문자열 전송 (auto-flush true)
        ) {
            while (true) {
                String request = in.readLine();
                // 클라이언트가 보낸 한 줄 읽기
                if (request == null || request.equalsIgnoreCase("exit")) break;
                // 클라이언트 연결 종료 또는 "exit" 입력 시 루프 탈출

                String[] parts = request.split(" ");
                // 공백 기준으로 문자열 분리
                if (parts.length != 3) {
                    out.println("ERROR ARG_COUNT");
                    continue;
                    // 입력 형식(a op b)이 아니면 오류 메시지 전송 후 다음 루프
                }

                double a, b;
                String op = parts[1];
                // parts[0]: 첫 번째 숫자, parts[1]: 연산자, parts[2]: 두 번째 숫자

                try {
                    a = Double.parseDouble(parts[0]);
                    b = Double.parseDouble(parts[2]);
                    // 첫 번째 숫자와 두 번째 숫자를 실수로 변환, 변환 실패 시 예외 발생
                } catch (NumberFormatException e) {
                    out.println("ERROR INVALID_NUMBER");
                    continue;
                    // 숫자가 아닌 경우 오류 메시지 전송
                }

                double result;
                switch (op) {
                    case "+": result = a + b; break;
                    case "-": result = a - b; break;
                    case "*": result = a * b; break;
                    case "/":
                        if (b == 0) { out.println("ERROR DIV_BY_ZERO"); continue; }
                        result = a / b; break;
                    default:
                        out.println("ERROR UNKNOWN_OPERATOR");
                        continue;
                        // 지원하지 않는 연산자 입력 시 오류 메시지
                }

                out.println("RESULT " + result);
                // 계산 결과를 클라이언트로 전송
            }
            System.out.println("[SERVER] Client disconnected: " + socket.getRemoteSocketAddress());
            // 클라이언트 종료 시 로그 출력

        } catch (IOException e) {
            e.printStackTrace();
            // 입출력 오류 발생 시 콘솔에 에러 출력
        }
    }
}
