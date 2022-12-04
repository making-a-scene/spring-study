package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기 전용이라는 뜻. 데이터 수정이 거의 없을 때 설정하면 성능이 최적화됨.
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    ////* 회원 가입 *////
    @Transactional
    // 클래스 단위에 @Transactional이 설정되어 있을 때 내부 메소드에도 따로 @Transactional을 추가해 주면 내부 어노테이션이 우선권을 가짐.
    // join()은 읽기 전용 메소드가 아니므로 여기에는 readOnly = false로 따로 설정해 줘야 함
    // @Transactionaldml readyOnly 옵션은 디폴트 값이 false라 따로 값 지정해 주지 않은 것..
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }

    // 가입하려는 회원이 중복 회원이라면 예외를 발생시키는 메소드
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    ////* 전체 회원 조회 *////
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    ////* 한 명의 회원만 조회 *////
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
