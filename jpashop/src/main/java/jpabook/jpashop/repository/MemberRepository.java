package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext
//    private EntityManager em;
    // 원래는 위에 주석 처리한 코드와 같이 작성해야 하는데, 스프링 부트는 EntityManager 선언부에 @Autowired만 추가해 줘도
    // 생성자를 통해 EntityManager를 자동 주입해줌.. 그래서 아래와 같이 일반적인 생성자 주입받는 필드처럼 작성해도 됨.
    private final EntityManager em;


    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); // 단건 조회
    }

    public List<Member> findAll() {
        // em.createQuery() : JPQL 쿼리문을 생성하는 메소드. 파라미터로 JPQL, 반환 값 타입을 넘겨줌
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
                // Member 타입의 find 결과 값을 리스트로 만들어서 반환해주는 메소드
                // 다건 조회
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                // 이렇게 하면 위 JPQL문에서 :name 부분이 name으로 바인딩됨.
                // 즉 member.name의 값이 findByName의 매개변수인 name과 같은 엔티티들을 가져오는 것.
                .getResultList();
    }
    // JPQL은 SQL과 비슷하지만 엔티티를 대상으로 함.

}
