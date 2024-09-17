package ar.unrn.tp.domain.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class BrandEntity {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private Brand name;

    public Brand getName() {
        return name;
    }
}
