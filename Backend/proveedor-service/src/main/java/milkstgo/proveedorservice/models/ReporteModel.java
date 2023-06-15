package milkstgo.proveedorservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteModel {
    private String quincena;
    private String codigo_proveedor;
    private String nombre_proveedor;
    private float total_kls_leche;
    private int nro_dias_envio_leche;
    private float promedio_kls_leche;
    private float variacion_leche;
    private float grasa;
    private float variacion_grasa;
    private float solidos_totales;
    private float variacion_solidos_totales;
    private float pago_leche;
    private float pago_grasa;
    private float pago_solidos_totales;
    private float bonificacion_frecuencia;
    private float descuento_variacion_leche;
    private float descuento_variacion_grasa;
    private float descuento_variacion_solidos;
    private float pago_total;
    private float monto_retencion;
    private float monto_final;
}
