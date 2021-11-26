package epam.service;

import epam.dao.jpa.ProductRepository;
import epam.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductService() {
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.delete(id);
    }

    public Product getProduct(int id) {
        return productRepository.findOne(id);
    }
}
