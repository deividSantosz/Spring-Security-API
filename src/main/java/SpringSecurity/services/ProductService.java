package SpringSecurity.services;

import SpringSecurity.entities.Product;
import SpringSecurity.repositories.ProductRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }


    public Product save(Product product) {
        if (validateProduct(product)) {
            return repository.save(product);
        }
        return product;
    }
    public boolean validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo");
        }
        if (product.getId() == null) {
            throw new IllegalArgumentException("O id não pode ser nulo");

        }
        if (product.getPreco() == null) {
            throw new IllegalArgumentException("O preço do produto deve ser maior que zero");
        }

        if (StringUtils.isBlank(product.getNome())) {
            throw new IllegalArgumentException("O nome do produto não pode estar em branco");
        }
        return true;
    }
    public void delete (Product product) {
        repository.delete(product);
    }
    public Product update (Product product) {
        if (product == null) {
            throw new IllegalArgumentException("O produto a ser atualizado não pode ser nulo");
        }

        Product existingProduct = repository.findById(product.getId()).orElse(null);
        if (existingProduct == null) {
            throw new EntityNotFoundException("Produto não encontrado com ID: " + product.getId());
        }
        existingProduct.setNome(product.getNome());
        existingProduct.setPreco(product.getPreco());

        return repository.save(existingProduct);
    }
}
