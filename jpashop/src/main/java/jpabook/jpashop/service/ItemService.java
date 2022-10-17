package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
        System.out.println(item.getStockQuantity());
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        // view로부터 받아온 수정된 객체인 param 내부 필드의 값을 기존 영속성 엔티티인 findItem에 set하는 방식,,,!
        // 이렇게 하면 영속성 엔티티를 사용해 update를 하므로 flush될 때 db에 변경 사항이 저장됨.
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    // ItemService는 ItemRepository에 정보를 저장하거나 정보를 찾는 역할만 할 뿐임..
    // 이렇게 서비스 계층이 새롭게 하는 일이 없는 경우엔 굳이 service 계층을 따로 안 만들어도 무방함
    // 컨트롤러를 통해 ItemRepository 엔티티로 바로 매핑되도록 설정해도 됨

}
