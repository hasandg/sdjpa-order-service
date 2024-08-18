package guru.springframework.orderservice.domain;

import jakarta.persistence.*;

@Entity
public class Foo extends BaseEntity {

    private Integer amount;


    @ManyToOne
    private OrderHeader orderHeader;

    public OrderHeader getOrderHeader() {
        return orderHeader;
    }

    public void setOrderHeader(OrderHeader orderHeader) {
        this.orderHeader = orderHeader;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
