package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form, @PathVariable Long itemId) {
//         Book book = new Book();
//         book.setId(form.getId());
//         book.setName(form.getName());
//         book.setPrice(form.getPrice());
//         book.setStockQuantity(form.getStockQuantity());
//         book.setAuthor(form.getAuthor());
//         book.setIsbn(form.getIsbn());
//         // 새로 생성한 book 엔티티는 식별자인 id 값을 이미 가지고는 있지만(== jpa에서 관리한 적이 있던 엔티티임), EntityManager를 통해 영속성 컨텍스트에 등록한 적은 없기 때문에
//        // 더 이상 jpa가 관리하고 있지 않음... -> 준영속 엔티티

//        itemService.saveItem(book);

        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
        // Book 엔티티 자체를 넘겨주는 게 아니라, 수정이 필요한 변수 값만 form에서 get하여 넘겨주는 게 훨씬 깔끔함...!
        // 수정이 필요한 변수가 많아서 파라미터가 너무 많아지는 경우엔 dto를 따로 만들어 dto 객체 하나만 넘겨주면 됨

        return "redirect:/items";
    }
}









