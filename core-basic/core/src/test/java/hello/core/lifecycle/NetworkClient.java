package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    // 서비스 시작시 호출
    public void setUrl(String url) {
        this.url = url;
    }

    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + "message : " + message);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("close : " + url);
    }

    // 의존관계 주입이 끝난 후 호출되는 메소드
    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
       connect();
       call("초기화 호출 메시지");
    }

    // 빈이 종료되기 직전에 호출되는 메소드
    // 스프링 컨테이너를 안전하게 종료하기 위함
    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
