package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        // 컨테이너를 닫는 .close()는 일반적으로 사용하는 ApplicationContext 타입에서는 지원해주지 않음..
        // .close()를 사용하기 위해서는 ApplicationContext의 하위에 있는 ConfigurableApplicationContext 타입으로 받아야 함
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();

    }
    @Configuration
    static class LifeCycleConfig {
        // @Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            // 객체가 생성되는 단계에서는 url 정보가 주어지지 않음
            NetworkClient networkClient = new NetworkClient();
            // 객체를 생성한 후 외부에서 수정자 주입을 통해 url이 주어짐
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }

    }
}
