package arch.monolith.service;

import arch.monolith.entity.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

@ApplicationScoped
@Transactional
public class ProductService {

    private final CatalogService catalogService;

    public CatalogService getCatalogService() {
        return catalogService;
    }

    public ProductService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Transactional
    public List<Product> findAllProducts() {
        return Product.listAll();
    }

    @Transactional
    public Product findProductById(Long id){
        return Product.findById(id);
    }

    public Product persistProduct(@Valid Product product) {
        product.persist();
        return product;
    }

    public Product updateProduct(@Valid Product product) {
        Product entity = Product.findById(product.id);
        entity.name = product.name;
        entity.rating = product.rating;
        entity.catalogID = product.catalogID;
        return entity;
    }

    public void deleteProduct(Long id) {
        Product product = Product.findById(id);
        product.delete();
    }
}
