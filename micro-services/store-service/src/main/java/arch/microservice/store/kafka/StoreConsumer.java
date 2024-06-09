package arch.microservice.store.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ApplicationScoped
public class StoreConsumer {


    private final ConcurrentMap<Long, List<JsonNode>> messageStore = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Incoming("catalog-to-store")
    @Blocking
    public void consumeCatalogList(String message) {
        try {
            List<JsonNode> jsonNodeList = extractJsonNodeList(message);
            for (JsonNode jsonNode : jsonNodeList) {
                Long id = extractIdFromJsonNode(jsonNode);
                if (id != null) {
                    messageStore.put(id, jsonNodeList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<JsonNode> extractJsonNodeList(String message) throws Exception {
        return objectMapper.readValue(message, new TypeReference<List<JsonNode>>() {});
    }

    private Long extractIdFromJsonNode(JsonNode jsonNode) {
        JsonNode idNode = jsonNode.get("id");
        return (idNode != null) ? idNode.asLong() : null;
    }

    public List<JsonNode> getMessageById(Long id) {
        return messageStore.get(id);
    }
}
