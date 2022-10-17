package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;
    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAllOverview(OrderSearch orderSearch) {
        List<Order> resultList = em.createQuery("select o from Order o join o.member m" +
                        " where o.status = :status" +
                        " and m.name like :name", Order.class)
                        // where절 : search 조건 설정
                        // like : 특정 문자열을 '포함하는' 값의 데이터 모두 검색
                        .setParameter("status", orderSearch.getOrderStatus())
                        .setParameter("name", orderSearch.getMemberName())
//                .setFirstResult(10) -> 검색된 데이터에서 10부터 select한다는 뜻... paging 기능 구현에 활용.
                        .setMaxResults(1000) // 최대로 select할 데이터 수
                        .getResultList();
        // Order와 member를 조인

        return resultList;

    }

    // Querydsl
//    public List<Order> findAllByString(OrderSearch orderSearch) {
//
//        String memberName = orderSearch.getMemberName();
//
//        return em.createQuery("select o from Order o where o.member = :member")
//                .setParameter("member", orderSearch.getMemberName())
//                .getResultList();
//
//    }

}
