package epam.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "products")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private int productId;

    @Column(nullable = false)
    private String name;

    @Column()
    private String description;

    @ManyToOne()
    @JoinColumn(name = "order_Id")
    private Order order;

    public Product() {
    }

    public int getId() {
        return this.productId;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public int getOrderId(Order order) {
        return order.getOrderId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product { " +
                "Order=" + order +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' + '}';
    }
}
