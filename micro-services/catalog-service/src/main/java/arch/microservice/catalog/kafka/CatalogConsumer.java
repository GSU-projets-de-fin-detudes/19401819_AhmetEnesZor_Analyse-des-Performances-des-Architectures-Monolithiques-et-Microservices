package arch.microservice.catalog.kafka;

import arch.microservice.catalog.Catalog;
import arch.microservice.catalog.CatalogService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.io.IOException;
import java.util.List;

@ApplicationScoped
public class CatalogConsumer {

    @Inject
    CatalogProducer catalogProducer;

    @Inject
    CatalogService catalogService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Incoming("store-to-catalog")
    @Blocking
    public void consumeList(String message) {
        try {
            List<Long> catalogIDs = objectMapper.readValue(message, new TypeReference<List<Long>>() {});
            System.out.println("Received list: " + catalogIDs);
            List<Catalog> catalogList = catalogService.findCatalogsByIDs(catalogIDs);
            catalogProducer.sendCatalogList(catalogList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
