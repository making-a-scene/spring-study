package hello.core.scope;

import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;
import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {
    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

        ac.close();

    }


    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean{
        @Autowired
        // 스프링 컨테이너에 클라이언트 대신 요청하는 대리자 기능
        private Provider<PrototypeBean> provider;
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
        // private ObjectFactory<PrototypeBean> prototypeBeanProvider;


        // problem; prototypeBean이 사용될 때마다 생성되지 않음.
//        private final PrototypeBean prototypeBean; // 생성 시점에 자동 주입됨
//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }
        // 주석 처리 안 된 코드처럼 Provider 사용해서 해결

        public int logic() {
            // 컨테이너에서 빈 찾아주는 기능 제공
//          PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            PrototypeBean prototypeBean = provider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Provider 사옹 없이 문제 해결
    @Scope("singleton")
    static class SingletonBean2 {
        private PrototypeBean prototypeBean;
        @Autowired
        private AnnotationConfigApplicationContext ac;

        public int logic() {
            this.prototypeBean = ac.getBean(PrototypeBean.class);
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
        // logic()이 호출될 때마다 ac에서 새로운 빈 요청...
        // 그냥 컨테이너에서 빈을 요청하기만 하면 되는데 ac까지 주입받아야 하는 건 너무 비효율적임..
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }

    }

}
