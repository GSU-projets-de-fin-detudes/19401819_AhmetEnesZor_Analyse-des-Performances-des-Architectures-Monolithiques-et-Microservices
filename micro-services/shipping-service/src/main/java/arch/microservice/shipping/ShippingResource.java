package arch.microservice.shipping;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/api/shipping")
public class ShippingResource {

    Logger logger;
    ShippingService shippingService;

    public ShippingResource(Logger logger, ShippingService shippingService) {
        this.shippingService = shippingService;
        this.logger = logger;
    }

    @GET
    public RestResponse<List<Shipping>> getAllShipping() {
        List<Shipping> shipping = shippingService.findAllShipping();
        logger.info("Total number of shipping: " + shipping.size());
        return RestResponse.ok(shipping);
    }

    @GET
    @Path("/{id}")
    public RestResponse<Shipping> getShipping(@RestPath Long id) {
        Shipping shipping = shippingService.findShippingById(id);
        if (shipping != null) {
            logger.info("Found shipping " + shipping);
            return RestResponse.ok(shipping);
        } else {
            logger.info("No shipping found with id " + id);
            return RestResponse.noContent();
        }
    }

    @POST
    public RestResponse<Void> createShipping(@Valid Shipping shipping, @Context UriInfo uriInfo) {
        shipping = shippingService.persistShipping(shipping);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(shipping.id));
        logger.info("New shipping created with URI " + builder.build().toString());
        return RestResponse.created(builder.build());
    }

    @PUT
    public RestResponse<Shipping> updateShipping(@Valid Shipping shipping) {
        shipping = shippingService.updateShipping(shipping);
        logger.info("Shipping updated with new valued " + shipping);
        return RestResponse.ok(shipping);
    }

    @DELETE
    @Path("/{id}")
    public RestResponse<Void> deleteShipping(@RestPath Long id) {
        shippingService.deleteShipping(id);
        logger.info("Shipping deleted with " + id);
        return RestResponse.noContent();
    }

}
