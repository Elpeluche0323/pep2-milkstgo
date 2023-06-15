package milkstgo.acopio_lecheservice.entities;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "datos_acopio")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AcopioEntity {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fecha;
    private String turno;
    private String proveedor;
    private float kls_leche;
}
