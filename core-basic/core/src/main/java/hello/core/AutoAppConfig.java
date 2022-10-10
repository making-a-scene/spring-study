package hello.core;

//import hello.core.member.MemberRepository;
//import hello.core.member.MemoryMemberRepository;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration

// 컴포넌트 스캔; @Component 어노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 자동 등록해준다.
@ComponentScan(
        // @Configuration 어노테이션이 붙은 컴포넌트는 컴포넌트 스캔에서 제외한다는 뜻.
        // (@Configuration 소스코드 내부에도 @Component 어노테이션이 붙어 있어 컴포넌트 스캔 대상이다.)
        // 스프링 빈을 수동 등록하는 AppConfig에 @Configuration이 붙어 있으니까 이걸 제외한 것.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)

// 기존 AppConfig와는 다르게 스프링 빈으로 등록한 클래스가 하나도 없다...!
public class AutoAppConfig {
//    @Bean(name = "memoryMemberRepository")
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}
