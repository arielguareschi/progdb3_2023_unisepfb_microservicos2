package br.edu.unisep.pdb3.productservice.util;

import br.edu.unisep.pdb3.productservice.model.Product;
import br.edu.unisep.pdb3.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() < 1) {
            Product product = new Product();
            product.setName("iPhone 17");
            product.setDescription("iPhone 17");
            product.setPrice(BigDecimal.valueOf(11999));

            productRepository.save(product);
        }
    }
}
