package arch.microservice.product.kafka;

import arch.microservice.product.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.List;

public class ProductProducer {

    @Inject
    @Channel("product-to-catalog")
    Emitter<String> emitter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendProductList(List<Product> productList) {
        try {
            String json = objectMapper.writeValueAsString(productList);
            emitter.send(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
