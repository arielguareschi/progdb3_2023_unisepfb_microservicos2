package br.unisep.edu.pdb3.inventoryservice;

import br.unisep.edu.pdb3.inventoryservice.model.Inventory;
import br.unisep.edu.pdb3.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository){
        return args -> {
            Inventory inventory = new Inventory();
            inventory.setSkuCode("iphone-16");
            inventory.setQuantity(100);
            Inventory inventory1 = new Inventory();
            inventory1.setSkuCode("iphone-16-pro");
            inventory1.setQuantity(150);
            Inventory inventory2 = new Inventory();
            inventory2.setSkuCode("iphone-16-max-pro");
            inventory2.setQuantity(200);

            inventoryRepository.save(inventory);
            inventoryRepository.save(inventory1);
            inventoryRepository.save(inventory2);
        };
    }
}
