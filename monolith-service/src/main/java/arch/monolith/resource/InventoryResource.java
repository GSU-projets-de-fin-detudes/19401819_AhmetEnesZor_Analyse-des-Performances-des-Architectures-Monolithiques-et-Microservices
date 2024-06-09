package arch.monolith.resource;

import arch.monolith.entity.Inventory;
import arch.monolith.service.InventoryService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/api/inventories")
public class InventoryResource {

    Logger logger;
    InventoryService inventoryService;

    public InventoryResource(Logger logger, InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.logger = logger;
    }

    @GET
    public RestResponse<List<Inventory>> getAllInventories() {
        List<Inventory> inventories = inventoryService.findAllInventories();
        logger.info("Total number of inventories: " + inventories.size());
        return RestResponse.ok(inventories);
    }

    @GET
    @Path("/{id}")
    public RestResponse<Inventory> getInventory(@RestPath Long id) {
        Inventory inventory = inventoryService.findInventoryById(id);
        if (inventory != null) {
            logger.info("Found inventory " + inventory);
            return RestResponse.ok(inventory);
        } else {
            logger.info("No inventory found with id " + id);
            return RestResponse.noContent();
        }
    }

    @POST
    public RestResponse<Void> createInventory(@Valid Inventory inventory, @Context UriInfo uriInfo) {
        inventory = inventoryService.persistInventory(inventory);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(inventory.id));
        logger.info("New inventory created with URI " + builder.build().toString());
        return RestResponse.created(builder.build());
    }

    @PUT
    public RestResponse<Inventory> updateInventory(@Valid Inventory inventory) {
        inventory = inventoryService.updateInventory(inventory);
        logger.info("Inventory updated with new valued " + inventory);
        return RestResponse.ok(inventory);
    }

    @DELETE
    @Path("/{id}")
    public RestResponse<Void> deleteInventory(@RestPath Long id) {
        inventoryService.deleteInventory(id);
        logger.info("Inventory deleted with " + id);
        return RestResponse.noContent();
    }

}
