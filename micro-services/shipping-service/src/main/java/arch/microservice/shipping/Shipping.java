package arch.microservice.shipping;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="shipping")
public class Shipping extends PanacheEntity {

    public String companyName;
    public Long price;

    @Override
    public String toString() {
        return "Shipping{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", price=" + price +
                '}';
    }
}
