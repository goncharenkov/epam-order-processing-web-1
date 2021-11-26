package epam.service;

import org.springframework.context.ApplicationEvent;

public class ProductServiceEvent extends ApplicationEvent {

    public ProductServiceEvent(Object source) {
        super(source);
    }

    public String toString() {
        return "ProductService Event";
    }
}