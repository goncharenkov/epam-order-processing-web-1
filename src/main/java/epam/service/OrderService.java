package epam.service;

import epam.dao.jpa.OrderRepository;
import epam.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderService() {
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrder(int id) {
        return orderRepository.findOne(id);
    }

    public void deleteOrder(int id) {
        orderRepository.delete(id);
    }

    public ArrayList<Order> getAllOrders() {
        ArrayList pageOfOrders = orderRepository.findAll();
        return pageOfOrders;
    }
}
