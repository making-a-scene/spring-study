package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class OrderServiceImplTest {
    @Test
    void createOrder() {
        MemberRepository memberRepository = new MemoryMemberRepository();
        DiscountPolicy discountPolicy = new FixDiscountPolicy();
        OrderService orderService = new OrderServiceImpl(memberRepository, discountPolicy);
        memberRepository.save(new Member(1L, "name", Grade.VIP));
        Order order = orderService.createOrder(1L, "itemA", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);


//        OrderServiceImpl orderService = new OrderServiceImpl();
//        orderService.createOrder(1L, "itemA", 10000); // 컴파일 오류
        // 생성자 주입을 사용하면 테스트하고자 하는 객체가 어떤 다른 객체에 의존하고 있는지 알기가 어려움...
        // 의존 관계가 누락되어 NPE가 발생할 가능성이 매우 높음
        // 반면 생성자 주입의 경우 의존 관계가 누락되었을 때 컴파일 오류를 발생시키므로 NPE 발생 가능성이 적음
    }
}
