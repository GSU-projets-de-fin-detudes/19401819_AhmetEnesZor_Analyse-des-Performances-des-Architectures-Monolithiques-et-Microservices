package arch.microservice.store.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.List;

@ApplicationScoped
public class StoreProducer {

    @Inject
    @Channel("store-to-catalog")
    Emitter<String> emitter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendList(List<Long> list) {
        try {
            String json = objectMapper.writeValueAsString(list);
            emitter.send(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
