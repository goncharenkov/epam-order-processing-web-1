package epam.service;

import org.springframework.context.ApplicationEvent;

public class OrderServiceEvent extends ApplicationEvent {

    public OrderServiceEvent(Object source) {
        super(source);
    }

    public String toString() {
        return "OrderService Event";
    }
}