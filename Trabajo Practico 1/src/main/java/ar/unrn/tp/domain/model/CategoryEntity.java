package ar.unrn.tp.domain.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class CategoryEntity {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private Category name;

    public Category getName() {
        return name;
    }
}
