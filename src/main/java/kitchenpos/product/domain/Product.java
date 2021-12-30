package kitchenpos.product.domain;

import javax.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = new Price(price);
    }

    public static Product of(String name, int price) {
        return new Product(name, price);
    }

    public Price multiplyPrice(long value) {
        return this.price.multiply(value);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public int getPriceValue() {
        return price.getPrice();
    }
}
