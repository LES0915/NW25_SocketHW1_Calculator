package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    public static void main(String[] args) {
        int port = 1111; // 서버가 열 포트 번호를 지정, 클라이언트가 이 포트로 접속
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        // 최대 10개의 클라이언트를 동시에 처리할 수 있는 ThreadPool 생성
        // 클라이언트 접속 요청이 들어오면 스레드풀에서 Thread를 할당

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // ServerSocket 생성, 지정한 포트에서 클라이언트 연결 대기
            System.out.println("[SERVER] Started on port " + port);
            // 서버 시작 메시지 출력

            while (true) {
                Socket clientSocket = serverSocket.accept();
                // 클라이언트 접속 대기
                // accept()는 블록 호출 → 클라이언트가 연결될 때까지 대기
                System.out.println("[SERVER] Client connected: " + clientSocket.getRemoteSocketAddress());
                // 클라이언트가 연결되면 IP와 포트 정보 출력
                threadPool.execute(new ClientHandler(clientSocket));
                // ThreadPool에 ClientHandler 작업 제출
                // ClientHandler에서 실제 클라이언트 요청 처리
            }

        } catch (IOException e) {
            e.printStackTrace();
            // 서버 소켓 생성이나 accept() 중 IOException 발생 시 출력
        }
    }
}
