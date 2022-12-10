package jpabook.jpashop.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDtos() {
        // select new : 반환받을 객체의 타입(클래스)을 지정할 수 있다. 이 클래스의 생성자에 jpql 조회 결과를 넘겨줄 수 있다.
        // member와 delivery 전체를 끌어오지 않고 필요한 필드만 dto에 정의해서 가져올 수 있음. -> 페치 조인에 비해 성능이 향상됨.
        // 주의사항: 1. 패키지명을 포함한 전체 클래스명을 입력해야 한다. 2. 순서와 타입이 일치하는 생성자가 필요하다.
        // 엔티티 자체가 아닌 dto를 반환하기 때문에 엔티티에 수정이 필요한 경우 시용할 수 없음.
        // 코드의 가독성이 떨어짐.
        // repository 클래스는 엔티티를 조회하는 역할을 수행해야 되는데, view 계층을 담당하는 api 스펙(dto)이 repository 로직에 포함되어 버림.
        // ㄴ api 스펙이 바뀌면 repository까지 고쳐야 하는 일이 생김....
        // 그래서 지금 이 클래스처럼 view에 관여하는 query repository를 따로 분리해서 생성해야 함.
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class
        ).getResultList();
    }


}
