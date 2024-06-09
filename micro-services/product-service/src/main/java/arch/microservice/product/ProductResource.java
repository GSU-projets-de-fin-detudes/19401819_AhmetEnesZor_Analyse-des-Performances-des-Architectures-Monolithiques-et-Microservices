package arch.microservice.product;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/api/products")
public class ProductResource {

    Logger logger;
    ProductService productService;

    public ProductResource(Logger logger, ProductService productService) {
        this.productService = productService;
        this.logger = logger;
    }

    @GET
    public RestResponse<List<Product>> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        logger.info("Total number of products: " + products.size());
        return RestResponse.ok(products);
    }

    @GET
    @Path("/{id}")
    public RestResponse<Product> getProduct(@RestPath Long id) {
        Product product = productService.findProductById(id);
        if (product != null) {
            logger.info("Found product " + product);
            return RestResponse.ok(product);
        } else {
            logger.info("No product found with id " + id);
            return RestResponse.noContent();
        }
    }

    @POST
    public RestResponse<Void> createProduct(@Valid Product product, @Context UriInfo uriInfo) {
        product = productService.persistProduct(product);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(product.id));
        logger.info("New product created with URI " + builder.build().toString());
        return RestResponse.created(builder.build());
    }

    @PUT
    public RestResponse<Product> updateProduct(@Valid Product product) {
        product = productService.updateProduct(product);
        logger.info("Product updated with new valued " + product);
        return RestResponse.ok(product);
    }

    @DELETE
    @Path("/{id}")
    public RestResponse<Void> deleteProduct(@RestPath Long id) {
        productService.deleteProduct(id);
        logger.info("Product deleted with " + id);
        return RestResponse.noContent();
    }

}
