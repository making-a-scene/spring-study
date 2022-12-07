package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member memberA = createMember("userA", "서울", "1", "1111");

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            Book book2 = createBook("JPA2 BOOK", 20000, 100);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery1 = createDelivery(memberA);
            Order order = Order.createOrder(memberA, delivery1, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member memberB = createMember("userB","하남", "815", "2222");

            Book book3 = createBook("SPRING1 BOOK", 20000, 200);
            Book book4 = createBook("SPRING2 BOOK", 40000, 300);

            OrderItem orderItem3 = OrderItem.createOrderItem(book3, 20000, 3);
            OrderItem orderItem4 = OrderItem.createOrderItem(book4, 40000, 4);

            Delivery delivery2 = createDelivery(memberB);
            Order order = Order.createOrder(memberB, delivery2, orderItem3, orderItem4);
            em.persist(order);
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            em.persist(member);
            return member;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            em.persist(book);
            return book;
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }


}
