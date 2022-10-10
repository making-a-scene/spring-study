package jpabook.jpashop.service;


import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // >>>> 주문 <<<< //
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 예제 단순화를 위해 한 번의 주문에 하나의 상품만을 주문 가능.

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);
        // Cascade를 ALL로 설정한 필드의 경우
        // order 엔티티에 대해서만 persist()를 호출해도 order 내부 필드의 엔티티들도 자동으로 persist()됨.
        // 자기 자신 외에는 전혀 참조하는 곳이 없는 private한 엔티티인 경우에만 해당 클래스에서 Cascade를 ALL로 설정하기.
        // 반면 여기 저기에서 많이 쓰이는 엔티티라면 cascade를 ALL로 설정하기보단 repository를 따로 생성해 persist() 직접 하는 게 더 나음..
        // 지금 당장 이해 안 되면 일단 안 쓰다가 나중에 이해될 때 리팩토링하는 게 더 낫다.

        return order.getId();
    }

    // >>>> 주문 취소 <<<< //
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
        // jpa를 사용하면 db에 저장된 엔티티에 변경 사항이 있을 때 이 변경 사항을 db에 반영하기 위한 update 쿼리를 보낼 필요가 없음.
        // jpa는 엔티티에 변경 사항이 있으면 자동으로 감지해서 업데이트 해주는 기능을 제공함
        // 여기서는 cancel()을 호출함으로써 order의 status가 CANCEL로 바뀌었는데, 이때 jpa에 의해 자동으로 이 변경사항이 db에 반영됨
    }

    // >>>> 주문 검색 <<<< //
    // to-do
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }
}
