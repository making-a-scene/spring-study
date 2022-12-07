package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    @JsonIgnore
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    // EnumType을 ORDINAL로 하면 각 값에 0부터 숫자가 매겨짐(READY가 0, COMP가 1,...)
    // 그런데 ORDINAL로 하면 나중에 다른 값이 추가됐을 때 숫자가 하나씩 밀려 버림...
    // 그러니까 ORDINAL 말고 STRING으로 설정하자
    private DeliveryStatus status; // READY, COMP
}
