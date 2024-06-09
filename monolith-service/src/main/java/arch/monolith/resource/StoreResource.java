package arch.monolith.resource;

import arch.monolith.entity.Catalog;
import arch.monolith.entity.Inventory;
import arch.monolith.entity.Product;
import arch.monolith.service.StoreService;
import arch.monolith.entity.Shipping;
import arch.monolith.entity.Store;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/api/stores")
public class StoreResource {

    Logger logger;
    StoreService storeService;

    public StoreResource(Logger logger, StoreService storeService) {
        this.storeService = storeService;
        this.logger = logger;
    }

    @GET
    public RestResponse<List<Store>> getAllStores() {
        List<Store> stores = storeService.findAllStores();
        logger.info("Total number of stores: " + stores.size());
        return RestResponse.ok(stores);
    }

    @GET
    @Path("/{id}")
    public RestResponse<Store> getStoreById(@RestPath Long id) {
        Store store = storeService.findStoreById(id);
        if (store != null) {
            logger.info("Found store " + store);
            return RestResponse.ok(store);
        } else {
            logger.info("No store found with id " + id);
            return RestResponse.noContent();
        }
    }

    @GET
    @Path("/catalogs/{id}")
    public RestResponse<List<Catalog>> getStoreCatalogs(@RestPath Long id) {
        List<Catalog> catalogList = storeService.findStoreCatalogsById(id);
        if (catalogList.get(0) != null) {
            logger.info("Found catalogs of store id " + id);
            return RestResponse.ok(catalogList);
        } else {
            logger.info("No catalogs found with store id " + id);
            return RestResponse.noContent();
        }
    }

    @GET
    @Path("/inventories/{id}")
    public RestResponse<List<Inventory>> getStoreInventories(@RestPath Long id) {
        List<Inventory> inventoryList = storeService.findStoreInventoriesById(id);
        if (inventoryList.get(0) != null) {
            logger.info("Found inventories of store id " + id);
            return RestResponse.ok(inventoryList);
        } else {
            logger.info("No inventories found with store id " + id);
            return RestResponse.noContent();
        }
    }

    @GET
    @Path("/products/{id}")
    public RestResponse<List<Product>> getStoreProducts(@RestPath Long id) {
        List<Product> productList = storeService.findStoreProductsById(id);
        if (productList.get(0) != null) {
            logger.info("Found products of store id " + id);
            return RestResponse.ok(productList);
        } else {
            logger.info("No products found with store id " + id);
            return RestResponse.noContent();
        }
    }

    @GET
    @Path("/shipping/{id}")
    public RestResponse<List<Shipping>> getStoreShipping(@RestPath Long id) {
        List<Shipping> shippingList = storeService.findStoreShippingById(id);
        if (shippingList.get(0) != null) {
            logger.info("Found shipping list of store id " + id);
            return RestResponse.ok(shippingList);
        } else {
            logger.info("No shipping found with store id " + id);
            return RestResponse.noContent();
        }
    }

    @POST
    public RestResponse<Void> createStore(@Valid Store store, @Context UriInfo uriInfo) {
        store = storeService.persistStore(store);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(store.id));
        logger.info("New store created with URI " + builder.build().toString());
        return RestResponse.created(builder.build());
    }

    @PUT
    public RestResponse<Store> updateStore(@Valid Store store) {
        store = storeService.updateStore(store);
        logger.info("Store updated with new valued " + store);
        return RestResponse.ok(store);
    }

    @DELETE
    @Path("/{id}")
    public RestResponse<Void> deleteStore(@RestPath Long id) {
        storeService.deleteStore(id);
        logger.info("Store deleted with " + id);
        return RestResponse.noContent();
    }

}
