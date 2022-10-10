package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

// form을 통해 전달받은 회원의 정보를 객체에 저장하기 위한 클래스.
@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수입니다.")
    // @NotEmpty : 이 어노테이션이 추가된 필드에 값이 할당되어 있지 않으면 오류가 발생하도록 함.
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
