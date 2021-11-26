package epam.dao.jpa;

import epam.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    ArrayList findAll();
}
