package epam.dao.jpa;

import epam.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    ArrayList findAll();
}
