package milkstgo.reporteservice.entities;

import lombok.NonNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "planilla")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReporteEntity {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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
