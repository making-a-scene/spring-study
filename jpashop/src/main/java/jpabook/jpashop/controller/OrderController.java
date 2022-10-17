package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member>  members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        // Service 클래스 밖에서 엔티티를 찾아서 넘겨주는 것보다 엔티티의 식별자만 넘겨주고 엔티티를 찾는 작업부터 service 계층이 하도록 설계하는 걸 권장.
        // 트랜잭션 내부에서 db에 접근해야 영속성 컨텍스트 내부에서 데이터를 변경할 수 있기 때문....!
        orderService.order(memberId, itemId, count);

        return "redirect:/orders";

    }

    @GetMapping("/orders")
    // @ModelAttribute가 추가된 파라미터의 경우 model.addAttribute()에 넘겨주지 않아도 model 객체에 자동으로 담겨 view로 보내지며,
    // view에서 해당 파라미터 객체에 값을 담아 다시 서버로 넘겨 준다.
    // model.addAttribute("orderSearch", orderSearch);가 생략되어 있는 거라고 생각하면 됨.
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {

        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";

    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
