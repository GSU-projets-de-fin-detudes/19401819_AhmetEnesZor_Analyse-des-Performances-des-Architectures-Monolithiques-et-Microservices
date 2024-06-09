package arch.monolith.service;

import arch.monolith.entity.Catalog;
import arch.monolith.entity.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
public class CatalogService {

    private final ProductService productService;

    public ProductService getProductService() {
        return productService;
    }

    public CatalogService(ProductService productService) {
        this.productService = productService;
    }

    @Transactional
    public List<Catalog> findAllCatalogs() {
        return Catalog.listAll();
    }

    @Transactional
    public Catalog findCatalogById(Long id){
        return Catalog.findById(id);
    }

    @Transactional
    public List<Product> findCatalogProductsById(Long catalogId){
        Catalog catalog = Catalog.findById(catalogId);
        List<Long> productIDs = catalog.productIDs;
        List<Product> productList = new ArrayList<>();
        for (Long productID : productIDs) {
            productList.add(productService.findProductById(productID));
        }
        return productList;
    }

    public Catalog persistCatalog(@Valid Catalog catalog) {
        catalog.persist();
        return catalog;
    }

    public Catalog updateCatalog(@Valid Catalog catalog) {
        Catalog entity = Catalog.findById(catalog.id);
        entity.name = catalog.name;
        entity.productIDs = catalog.productIDs;
        return entity;
    }

    public void deleteCatalog(Long id) {
        Catalog catalog = Catalog.findById(id);
        catalog.delete();
    }
}
