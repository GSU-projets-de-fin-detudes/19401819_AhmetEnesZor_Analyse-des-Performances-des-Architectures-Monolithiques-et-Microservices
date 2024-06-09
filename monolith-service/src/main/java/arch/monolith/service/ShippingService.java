package arch.monolith.service;

import arch.monolith.entity.Shipping;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

@ApplicationScoped
@Transactional
public class ShippingService {

    @Transactional
    public List<Shipping> findAllShipping() {
        return Shipping.listAll();
    }

    @Transactional
    public Shipping findShippingById(Long id){
        return Shipping.findById(id);
    }

    public Shipping persistShipping(@Valid Shipping shipping) {
        shipping.persist();
        return shipping;
    }

    public Shipping updateShipping(@Valid Shipping shipping) {
        Shipping entity = Shipping.findById(shipping.id);
        entity.companyName = shipping.companyName;
        entity.price = shipping.price;
        return entity;
    }

    public void deleteShipping(Long id) {
        Shipping shipping = Shipping.findById(id);
        shipping.delete();
    }
}
