package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 사용자 입력을 받기 위한 Scanner 생성

        System.out.println("[CLIENT] Connecting to server: " + ServerConfig.ip + ":" + ServerConfig.port);
        // 서버 IP와 포트 출력
        // ServerConfig에서 읽어온 값 사용

        try (
                Socket socket = new Socket(ServerConfig.ip, ServerConfig.port); //Socket: 서버와 연결 생성
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //BufferedReader: 서버로부터 문자열 수신
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true) //PrintWriter: 서버에 문자열 전송 (auto-flush=true)

        ) {
            while (true) {
                System.out.print("Enter calculation (ex: 5 + 3) or 'exit' to quit: ");
                String message = sc.nextLine();
                out.println(message);
                if (message.equalsIgnoreCase("exit")) break;
                // "exit" 입력 시 while 루프 종료 → 프로그램 종료

                String response = in.readLine();
                System.out.println("[SERVER RESPONSE] " + response);
                // 서버가 보낸 결과 읽어서 출력
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 서버 연결 실패나 입출력 오류 발생 시 예외 출력
        }
    }
}
