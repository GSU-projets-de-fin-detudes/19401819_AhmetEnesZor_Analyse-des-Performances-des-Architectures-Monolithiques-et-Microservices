package arch.microservice.product;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="product")
public class Product extends PanacheEntity {

    public String name;
    public int rating;
    public Long catalogID;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", catalogID=" + catalogID +
                '}';
    }
}