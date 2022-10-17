package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    // 주문 내역 검색을 위한 항목들...

    private String memberName; // 회원명
    private OrderStatus orderStatus; // 주문 상태

}
