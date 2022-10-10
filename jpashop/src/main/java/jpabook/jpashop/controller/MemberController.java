package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        // 컨트롤러에서 Model 타입 객체의 .addAttribute(String attributeName, Data) 호출하면 attributeName에 Data를 매핑해서 view에 넘김.

        model.addAttribute("memberForm", new MemberForm());
        // return 페이지인  members/createMemberForm의 화면에서 이 memberForm 객체에 접근할 수 있게 됨.

        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    /* @Valid : 매개변수로 받는 객체의 필드 값에 대한 검증을 수행함.
       MemberForm 클래스에서 name에 @NotNull을 추가했기 때문에, 여기서 name 값이 null이 아닌지 검증하는 것.
       @NotNull 외에도 다양한 validation 어노테이션이 존재한다.. 필요할 때 찾아보고 사용하면 됨. */
    /* @Valid 객체 다음에 BindingResult 객체가 있으면,
       post 방식으로 받아온 @Valid 객체의 검증 과정에서 오류가 발생했을 때 그 오류를 BindingResult 객체에 저장한 후 메소드를 실행함.
       이때 값이 입력된 @Valid 객체도 함께 받아오기 때문에 이전 화면으로 돌아가더라도 에러가 발생하지 않은 다른 form 데이터는 필드에 그대로 유지됨...!  */
    public String create(@Valid MemberForm form, BindingResult result) {

        if(result.hasErrors()) {
            // 에러에 대한 정보가 저장되어 있는 result를 form으로 돌아가서 사용할 수 있도록 해줌.
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        // 회원 정보가 저장된 이후 페이지가 재로딩되어 중복 저장되는 상황을 막기 위해 리다이렉트를 함.
        return "redirect:/";

    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
