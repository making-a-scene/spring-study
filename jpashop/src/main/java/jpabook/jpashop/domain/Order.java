package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "orders") // 테이블명 설정
@Getter @Setter
@NoArgsConstructor(access = PROTECTED) // JPA는 기본 생성자의 access level을 protected까지 허용함.
// jpa에서 protected로 설정된 메소드는 사용하지 말라는 뜻으로 이해하면 됨...
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id") // 외래 키 이름 설정
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    // CascadeType을 ALL로 설정하면 orderItems 내부의 모든 OrderItem 엔티티, Order 엔티티를 각각 persist() 호출할 필요 없이
    // persist(order)만 해도 위 과정이 자동으로 됨
    // 해당 필드를 가지고 있는 엔티티만 persist()해도 내부 필드인 이 orderItem도 함께 persist 되는 것
    // (persist는 생성되는 모든 엔티티에 대해 호출해 주어야 하는 것이 원칙이다.)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 일대일 연관관계에서는 FK를 어느 쪽에 둬도 상관 없음
    // 엑세스가 많이 이뤄지는 쪽에 두는 걸 권장...
    // OneToOne 관계에 대해 CascadeType을 ALL로 설정하면, persist(delivery) 할 필요 없이 persist(order)만 해도 자동으로 delivery도 컨텍스트에 추가됨
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태[ORDER, CANCEL]

    // >>> 연관 관계 편의 메소드 <<< //
    // 연관관계 편의 메소드는 양방향 연관 관계에 있는 두 엔티티 중에서 보다 데이터의 추가가 많이 일어나는 쪽에 두는 게 좋음.
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // >>>> 생성 메소드 <<<< //
    // https://java.ihoney.pe.kr/155 -> ... parameter
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        // static 메소드 호출을 통해 Order 엔티티가 생성되도록...
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order; // 하나의 메소드에서 모든 세팅을 완료함

    }

    // >>>> 비즈니스 로직 <<<< //
    // 주문 취소
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // >>>> 조회 로직 <<<< //
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }


}
