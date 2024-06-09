package arch.monolith.service;


import arch.monolith.entity.Inventory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

@ApplicationScoped
@Transactional
public class InventoryService {

    private final ProductService productService;

    public ProductService getProductService() {
        return productService;
    }

    public InventoryService(ProductService productService) {
        this.productService = productService;
    }

    @Transactional
    public List<Inventory> findAllInventories() {
        return Inventory.listAll();
    }

    @Transactional
    public Inventory findInventoryById(Long id){
        return Inventory.findById(id);
    }

    public Inventory persistInventory(@Valid Inventory inventory) {
        inventory.persist();
        return inventory;
    }

    public Inventory updateInventory(@Valid Inventory inventory) {
        Inventory entity = Inventory.findById(inventory.id);
        entity.productID = inventory.productID;
        entity.price = inventory.price;
        entity.numberOfProduct = inventory.numberOfProduct;
        return entity;
    }

    public void deleteInventory(Long id) {
        Inventory inventory = Inventory.findById(id);
        inventory.delete();
    }
}
