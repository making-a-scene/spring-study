package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY) // Item은 여러 OrderItem 엔티티에 추가되어 있을 수 있음
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY) // Order에는 여러 OrderItem이 존재할 수 있음
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 상품 가격
    private int count; // 주문 수량

    // >>>> 생성 메소드 <<<< //
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    // >>>> 비즈니스 로직 <<<< //
    public void cancel() {
        getItem().addStock(count);
    }

    // >>>> 조회 로직 <<<< //
    // 주문 상품의 전체 가격 조회
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
