package arch.monolith.resource;

import arch.monolith.entity.Catalog;
import arch.monolith.entity.Product;
import arch.monolith.service.CatalogService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/api/catalogs")
public class CatalogResource {

    Logger logger;
    CatalogService catalogService;

    public CatalogResource(Logger logger, CatalogService catalogService) {
        this.catalogService = catalogService;
        this.logger = logger;
    }

    @GET
    public RestResponse<List<Catalog>> getAllCatalogs() {
        List<Catalog> catalogs = catalogService.findAllCatalogs();
        logger.info("Total number of catalogs: " + catalogs.size());
        return RestResponse.ok(catalogs);
    }

    @GET
    @Path("/{id}")
    public RestResponse<Catalog> getCatalogById(@RestPath Long id) {
        Catalog catalog = catalogService.findCatalogById(id);
        if (catalog != null) {
            logger.info("Found catalog " + catalog);
            return RestResponse.ok(catalog);
        } else {
            logger.info("No catalog found with id " + id);
            return RestResponse.noContent();
        }
    }

    @GET
    @Path("/products/{id}")
    public RestResponse<List<Product>> getCatalogProducts(@RestPath Long id) {
        List<Product> productList = catalogService.findCatalogProductsById(id);
        if (productList.get(0) != null) {
            logger.info("Found products of catalog id " + id);
            return RestResponse.ok(productList);
        } else {
            logger.info("No products found with catalog id " + id);
            return RestResponse.noContent();
        }
    }

    @POST
    public RestResponse<Void> createCatalog(@Valid Catalog catalog, @Context UriInfo uriInfo) {
        catalog = catalogService.persistCatalog(catalog);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(catalog.id));
        logger.info("New catalog created with URI " + builder.build().toString());
        return RestResponse.created(builder.build());
    }

    @PUT
    public RestResponse<Catalog> updateCatalog(@Valid Catalog catalog) {
        catalog = catalogService.updateCatalog(catalog);
        logger.info("Catalog updated with new valued " + catalog);
        return RestResponse.ok(catalog);
    }

    @DELETE
    @Path("/{id}")
    public RestResponse<Void> deleteCatalog(@RestPath Long id) {
        catalogService.deleteCatalog(id);
        logger.info("Catalog deleted with " + id);
        return RestResponse.noContent();
    }

}
