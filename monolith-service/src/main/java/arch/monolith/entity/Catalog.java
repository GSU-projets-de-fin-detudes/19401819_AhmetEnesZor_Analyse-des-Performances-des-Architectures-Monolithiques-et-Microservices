package arch.monolith.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name="catalog")
public class Catalog extends PanacheEntity {

    public String name;
    public List<Long> productIDs;

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productIDs=" + productIDs +
                '}';
    }
}

