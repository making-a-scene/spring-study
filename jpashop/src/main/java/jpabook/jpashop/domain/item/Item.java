package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 싱글 테이블 전략으로 설정
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany
    @JoinTable(name = "category_item",
               joinColumns = @JoinColumn(name = "category_id"),
               inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Category> categories = new ArrayList<>();

    // >>>> 비즈니스 로직(재고 관리) <<< //
    // 비즈니스 로직은 해당 로직이 관리하는 필드를 갖고 있는 엔티티 내부에 설계하는 게 가장 좋음...!
    // 여기서 재고 관리 로직은 stockQuantity를 사용하니까 이 필드를 갖고 있는 이 Item 클래스에 구현한 것.
    // 실무에서는 setter를 추가하지 말고 이런 비즈니스 로직을 통해서 값을 수정하도록 해야 함.

    // 재고 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    // 재고 감소
    public void removeStock(int quantity) {
        int restStock = this.getStockQuantity() - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}


