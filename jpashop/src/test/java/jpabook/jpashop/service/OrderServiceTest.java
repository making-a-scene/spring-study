package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static jpabook.jpashop.domain.OrderStatus.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() {

        // given
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);

        Item book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        // when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertThat(getOrder.getStatus()).isEqualTo(ORDER); // order의 주문 상태
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1); // 주문 상품 종류 수
        assertThat(getOrder.getTotalPrice()).isEqualTo(10000 * orderCount); // 총 주문 금액
        assertThat(book.getStockQuantity()).isEqualTo(8); // 주문 후 남은 재고량

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() { // 재고 수량보다 많은 수량이 주문될시 예외가 발생하는지?

        // given
        Member member1 = new Member();
        member1.setName("hello");
        member1.setAddress(new Address("asd", "assg", "gdf"));
        em.persist(member1);

        Item newItem = new Book();
        newItem.setPrice(5000);
        newItem.setName("jpa");
        newItem.setStockQuantity(100);
        em.persist(newItem);

        // when
        // OrderItem.createOrder() 호출할 때 item.removeStock()에서 예외 발생
        // 재고 수량은 100개인데 110개를 주문했기 때문
         orderService.order(member1.getId(), newItem.getId(), 110);

        // then
        fail("NotEnoughStockException이 발생해야 합니다.");

        // item.removeStock() 메소드 자체의 정상 작동을 검증하는 단위 테스트도 필요함!
    }

    @Test
    public void 주문취소() {

        // given
        Member member1 = new Member();
        member1.setName("hello");
        member1.setAddress(new Address("asd", "assg", "gdf"));
        em.persist(member1);

        Item newItem = new Book();
        newItem.setPrice(5000);
        newItem.setName("jpa");
        newItem.setStockQuantity(100);
        em.persist(newItem);

        // when
        Long orderId = orderService.order(member1.getId(), newItem.getId(), 70);
        orderService.cancelOrder(orderId);

        // then
        Order newOrder = orderRepository.findOne(orderId);
        assertThat(newOrder.getStatus()).isEqualTo(CANCEL);
        assertThat(newItem.getStockQuantity()).isEqualTo(100);

    }

}