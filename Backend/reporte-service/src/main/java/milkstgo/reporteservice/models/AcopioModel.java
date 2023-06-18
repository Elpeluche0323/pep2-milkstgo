package milkstgo.reporteservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcopioModel {
    private String fecha;
    private String turno;
    private String proveedor;
    private float kls_leche;
}
