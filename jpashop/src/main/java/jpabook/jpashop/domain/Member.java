package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id") // pk의 column명 설정
    private Long id;

    // 근데 이 NotEmpty 속성이 모든 api에 적용되는 건 아닐 수도 있는데 엔티티에 떡하니 이걸 붙여서 검증하는 게 맞을까?
    @NotEmpty
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // 연관관계의 주인이 아닌 테이블에는 mappedBy 옵션을 줘야 함
    // 연관관계 주인인 테이블의 "member"라는 필드에 의해 매핑된다는 뜻
    // 외래 키가 저장되어 있는 위치
    // Order 엔티티 중 member 필드의 id 값이 자기 자신의 id 값과 같으면 연관관계에 있다.
    private List<Order> orders = new ArrayList<>();
    // 컬렉션은 필드 선언시에 바로 초기화하는 것이 안전하다.(NPE 예방)
    /*
    * 하이버네이트는 엔티티가 영속 context로 등록되면 이를 관리해야 한다.
    * 따라서 하이버네이트는 엔티티를 영속화할 때, 컬렉션을 감싸서 하이버네이트가 관리할 수 있는 내장 컬렉션 타입으로 변경한다.
    * 그런데 만약 이렇게 하이버네이트가 변경한 컬렉션을 다른 컬렉션으로 바꾸려고 하면 해당 컬렉션은 더이상 하이버네이트의 매커니즘대로 동작하지 않을 것이다.
    * -> 컬렉션은 생성, 초기화한 이후에는 변경하면 안 됨..
    * */

}
