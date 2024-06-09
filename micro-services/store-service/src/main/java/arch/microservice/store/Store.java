package arch.microservice.store;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name="store")
public class Store extends PanacheEntity {

    public String name;
    public int rating;
    public List<Long> catalogIDs;
    public List<Long> inventoryIDs;
    public List<Long> shippingIDs;

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", catalogIDs=" + catalogIDs +
                ", inventoryIDs=" + inventoryIDs +
                ", shippingIDs=" + shippingIDs +
                '}';
    }
}
