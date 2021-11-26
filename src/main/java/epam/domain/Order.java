package epam.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Set;

@Entity
@Table(name = "orders")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_Id")
    private int orderId;

    @Column(nullable = false)
    private String name;

    @Column()
    private String description;

    @OneToMany(mappedBy = "order")
    private Set<Product> products;

    public Order() {
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Order { " +
                "orderId=" + orderId +
                ", name='" + name + '\'' +
                ", description='" + description +
                ", products='" + products + '\'' + '}';
    }
}
