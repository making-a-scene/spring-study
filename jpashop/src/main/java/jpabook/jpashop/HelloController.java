package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello") // hello 라는 url에 접속하면 해당 메소드가 호출됨
    // 컨트롤러는 Model 타입의 인스턴스에 데이터를 저장해 view에 넘겨줄 수 있음
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!");
        return "hello"; // hello.html 페이지로 이동
    }
}
