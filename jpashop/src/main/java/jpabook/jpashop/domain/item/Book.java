package jpabook.jpashop.domain.item;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
@Setter
public class Book extends Item {

    private Long id;

    private String author;
    private String isbn;

    private String name;
    private int price;
    private int stockQuantity;

}
