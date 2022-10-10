package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.*;

@Embeddable
@Getter // 값 타입은 Setter가 제공되면 안 됨... 값이 바뀌면 안 되기 때문
@NoArgsConstructor(access = PROTECTED)
// jpa 기능상 기본 생성자가 필요할 때가 있어서 만들어 두는 것..
// protected로 설정해서 기본 생성자 호출하는 일은 최대한 방지함
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // 생성하는 시점에만 값을 setting -> 이후에는 변경 불가능
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
