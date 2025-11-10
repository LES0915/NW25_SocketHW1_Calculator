package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ServerConfig {
    public static String ip = "127.0.0.1"; // 기본 서버 IP
    public static int port = 1111;         // 기본 서버 포트
    // 클라이언트가 서버 IP/포트를 따로 설정하지 않으면 기본값 사용

    static {
        try {
            File f = new File("server_info.dat");
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f));
                ip = br.readLine();
                port = Integer.parseInt(br.readLine());
                br.close();
                //파일이 존재하면 첫 줄을 ip, 두 번째 줄을 port로 읽음
                //Integer.parseInt()로 문자열을 정수로 변환
            }
        } catch (Exception e) {
            System.out.println("[CONFIG] Using default server IP and port");
            //파일이 없거나 형식이 잘못되면 예외 발생
        }
    }
}
