package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
// 위 두 어노테이션을 추가하면 스프링 부트를 사용해 테스트가 이루어짐
@Transactional // 테스트 코드에 추가한 경우 테스트 종료시 디폴트로 롤백을 수행함.
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    @Test
    public void 회원가입() {

        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        assertThat(member).isEqualTo(memberRepository.findOne(savedId));
        // 동일한 트랜잭션 내부에서(하나의 영속성 컨텍스트 내부에서) id 값이 같은 엔티티인 경우 하나의 같은 데이터로 관리됨.

    }

    @Test(expected = IllegalStateException.class)
    // 이 메소드에서 throws한 예외가 expected의 값과 같을 때 테스트 성공.
    public void 중복_회원_예외() {

        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 하는 지점. 이 밑 부분은 실행되지 않아야 함.

        //then
        fail("예외가 발생해야 한다.");
        // fail() : 이 메소드가 호출된 경우 테스트 실패.
    }

}