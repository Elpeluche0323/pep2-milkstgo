package milkstgo.valores_gsservice.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "datos_gs")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ValoresEntity {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String proveedor;
    private int grasa;
    private int solido;
}
