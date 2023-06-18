package milkstgo.reporteservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValoresModel {
    private String proveedor;
    private float grasa;
    private float solido;
}
