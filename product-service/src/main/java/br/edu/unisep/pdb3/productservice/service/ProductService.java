package br.edu.unisep.pdb3.productservice.service;

import br.edu.unisep.pdb3.productservice.repository.ProductRepository;
import br.edu.unisep.pdb3.productservice.dto.ProductRequest;
import br.edu.unisep.pdb3.productservice.dto.ProductResponse;
import br.edu.unisep.pdb3.productservice.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    private ProductResponse mapToProductResponse(
            Product product){
        return ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();

        return products.stream().
                map(this::mapToProductResponse).toList();
    }

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved!", product.getId());
    }
}
