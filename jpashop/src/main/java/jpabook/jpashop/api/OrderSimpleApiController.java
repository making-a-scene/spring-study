package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
* XXToOne 관계에서의 성능 최적화
* Order
* Order -> Member(ManyToOne)
* Order -> Delivery(OneToOne)
* */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        // 비어 있는 OrderSearch 객체를 넘겨 주면 jpql의 파라미터에 값이 안 넘어오기 때문에
        // db 내의 Order 엔티티를 다 select함.
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }



}
