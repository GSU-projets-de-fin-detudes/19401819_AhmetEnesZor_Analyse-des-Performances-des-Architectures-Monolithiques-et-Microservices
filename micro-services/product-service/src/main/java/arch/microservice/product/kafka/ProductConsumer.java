package arch.microservice.product.kafka;

import arch.microservice.product.Product;
import arch.microservice.product.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.io.IOException;
import java.util.List;

public class ProductConsumer {

    @Inject
    ProductProducer productProducer;

    @Inject
    ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Incoming("catalog-to-product")
    @Blocking
    public void consumeList(String message) {
        try {
            List<Long> productIDs = objectMapper.readValue(message, new TypeReference<List<Long>>() {});
            System.out.println("Received list: " + productIDs);
            List<Product> productList = productService.findProductsByIDs(productIDs);
            productProducer.sendProductList(productList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
