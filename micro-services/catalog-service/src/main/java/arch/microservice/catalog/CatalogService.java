package arch.microservice.catalog;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
public class CatalogService {

    @Transactional
    public List<Catalog> findAllCatalogs() {
        return Catalog.listAll();
    }

    @Transactional
    public Catalog findCatalogById(Long id){
        return Catalog.findById(id);
    }

    public List<Catalog> findCatalogsByIDs(List<Long> catalogIDs) {
        List<Catalog> catalogList = new ArrayList<>();
        for (Long catalogID : catalogIDs) {
            catalogList.add(findCatalogById(catalogID));
        }
        return catalogList;
    }

    public Catalog persistCatalog(@Valid Catalog catalog) {
        catalog.persist();
        return catalog;
    }

    public Catalog updateCatalog(@Valid Catalog catalog) {
        Catalog entity = Catalog.findById(catalog.id);
        entity.name = catalog.name;
        entity.productIDs = catalog.productIDs;
        return entity;
    }

    public void deleteCatalog(Long id) {
        Catalog catalog = Catalog.findById(id);
        catalog.delete();
    }
}