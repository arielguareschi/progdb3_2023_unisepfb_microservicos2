package br.edu.unisep.pdb3.productservice.repository;

import br.edu.unisep.pdb3.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository
        extends MongoRepository<Product, String> {

}
