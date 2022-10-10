package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;


@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    // JoinTable : 테이블과 테이블을 조인한 별도의 새로운 테이블을 생성
    @JoinTable(
            name = "category_item", // 조인테이블명
            // 아래는 조인 테이블이 가지는 FK 값들
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    // 관계형 DB에는 컬렉션이 없어 다대다 관계를 표현할 수 없기 때문에 다대다 관계를 일대다/다대일 관계로 풀어내기 위한 중간 테이블을 만들어서 매핑해줘야 함
    // Item과 Category 엔티티를 조인한 새로운 테이블이 생성됨
    // 자율성이 떨어져 실무에서는 거의 사용되지 않음
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = LAZY) // 여러 개의 자식 카테고리가 하나의 부모 카테고리로 매핑...
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent") // 하나의 부모 카테고리에 여러 개의 자식 카테고리가 존재 가능
    private List<Category> child = new ArrayList<>(); // 동일한 부모 카테고리 하위에 존재하는 자식 카테고리의 리스트

    // >>> 연관 관계 편의 메소드 <<< //
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
