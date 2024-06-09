package arch.microservice.catalog.kafka;

import arch.microservice.catalog.Catalog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.List;

public class CatalogProducer {

    @Inject
    @Channel("catalog-to-store")
    Emitter<String> emitter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendCatalogList(List<Catalog> catalogList) {
        try {
            String json = objectMapper.writeValueAsString(catalogList);
            emitter.send(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
