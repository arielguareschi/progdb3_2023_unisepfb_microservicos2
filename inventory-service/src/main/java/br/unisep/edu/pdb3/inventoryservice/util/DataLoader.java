package br.unisep.edu.pdb3.inventoryservice.util;


import br.unisep.edu.pdb3.inventoryservice.model.Inventory;
import br.unisep.edu.pdb3.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) throws Exception {
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
    }
}
