package arch.monolith.service;


import arch.monolith.entity.Catalog;
import arch.monolith.entity.Inventory;
import arch.monolith.entity.Product;
import arch.monolith.entity.Shipping;
import arch.monolith.entity.Store;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
public class StoreService {

    private final CatalogService catalogService;
    private final InventoryService inventoryService;
    private final ShippingService shippingService;

    public StoreService(CatalogService catalogService, InventoryService inventoryService, ShippingService shippingService) {
        this.catalogService = catalogService;
        this.inventoryService = inventoryService;
        this.shippingService = shippingService;
    }

    @Transactional
    public List<Store> findAllStores() {
        return Store.listAll();
    }

    @Transactional
    public Store findStoreById(Long id){
        return Store.findById(id);
    }

    @Transactional
    public List<Catalog> findStoreCatalogsById(Long storeId) {
        Store store = Store.findById(storeId);
        List<Long> catalogIDs = store.catalogIDs;
        List<Catalog> catalogList = new ArrayList<>();
        for (Long catalogID : catalogIDs) {
            catalogList.add(catalogService.findCatalogById(catalogID));
        }
        return catalogList;
    }

    @Transactional
    public List<Inventory> findStoreInventoriesById(Long storeId) {
        Store store = Store.findById(storeId);
        List<Long> inventoryIDs = store.inventoryIDs;
        List<Inventory> inventoryList = new ArrayList<>();
        for (Long inventoryID : inventoryIDs) {
            inventoryList.add(inventoryService.findInventoryById(inventoryID));
        }
        return inventoryList;
    }

    @Transactional
    public List<Product> findStoreProductsById(Long storeId) {
        Store store = Store.findById(storeId);
        List<Long> inventoryIDs = store.inventoryIDs;
        List<Product> productList = new ArrayList<>();
        for (Long inventoryID : inventoryIDs) {
            Long productID = inventoryService.findInventoryById(inventoryID).productID;
            productList.add(inventoryService.getProductService().findProductById(productID));
        }
        return productList;
    }

    @Transactional
    public List<Shipping> findStoreShippingById(Long storeId) {
        Store store = Store.findById(storeId);
        List<Long> shippingIDs = store.shippingIDs;
        List<Shipping> shippingList = new ArrayList<>();
        for (Long shippingID : shippingIDs) {
            shippingList.add(shippingService.findShippingById(shippingID));
        }
        return shippingList;
    }

    public Store persistStore(@Valid Store store) {
        store.persist();
        return store;
    }

    public Store updateStore(@Valid Store store) {
        Store entity = Store.findById(store.id);
        entity.name = store.name;
        entity.rating = store.rating;
        entity.catalogIDs = store.catalogIDs;
        entity.inventoryIDs = store.inventoryIDs;
        entity.shippingIDs = store.shippingIDs;
        return entity;
    }

    public void deleteStore(Long id) {
        Store store = Store.findById(id);
        store.delete();
    }
}
