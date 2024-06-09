package arch.microservice.product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
public class ProductService {

    @Transactional
    public List<Product> findAllProducts() {
        return Product.listAll();
    }

    @Transactional
    public Product findProductById(Long id){
        return Product.findById(id);
    }

    public List<Product> findProductsByIDs(List<Long> productIDs) {
        List<Product> productList = new ArrayList<>();
        for (Long productID : productIDs) {
            productList.add(findProductById(productID));
        }
        return productList;
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
