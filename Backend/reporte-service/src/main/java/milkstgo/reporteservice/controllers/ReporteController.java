package milkstgo.reporteservice.controllers;

import milkstgo.reporteservice.entities.ReporteEntity;
import milkstgo.reporteservice.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/reporte")
public class ReporteController {
    @Autowired
    ReporteService reporteService;

    @GetMapping
    public ResponseEntity<ArrayList<ReporteEntity>> obtenerReporte(){
        ArrayList<ReporteEntity> reporte = reporteService.obtenerReporte();
        if(reporte.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/porproveedor/{codigo}/{quincena}")
    public  ResponseEntity<ReporteEntity> obtenerReportePorCodigo(@PathVariable("codigo_proveedor") String codigo_proveedor, @PathVariable("quincena") String quincena){
        ReporteEntity reporte = reporteService.buscarReporte(codigo_proveedor, quincena);
        return ResponseEntity.ok(reporte);
    }
    @PostMapping
    public ResponseEntity<ReporteEntity> guardarReporte(@RequestBody ReporteEntity reporte){
        String codigo_proveedor = reporte.getCodigo_proveedor();
        String quincena = reporte.getQuincena();
        /**
        String nombre_proveedor = reporte.getNombre_proveedor();
        float total_kls_leche = reporte.getTotal_kls_leche();
        int nro_dias_envio_leche = reporte.getNro_dias_envio_leche();
        float promedio_kls_leche = reporte.getPromedio_kls_leche();
        float variacion_leche = reporte.getVariacion_leche();
        float grasa = reporte.getGrasa();
        float variacion_grasa = reporte.getVariacion_grasa();
        float solidos_totales = reporte.getSolidos_totales();
        float variacion_solidos_totales = reporte.getVariacion_solidos_totales();
        float pago_leche = reporte.getPago_leche();
        float pago_grasa = reporte.getPago_grasa();
        float pago_solidos_totales = reporte.getPago_solidos_totales();
        float bonificacion_frecuencia = reporte.getBonificacion_frecuencia();
        float descuento_variacion_leche = reporte.getDescuento_variacion_leche();
        float descuento_variacion_grasa = reporte.getDescuento_variacion_grasa();
        float descuento_variacion_solidos = reporte.getDescuento_variacion_solidos();
        float pago_total = reporte.getPago_total();
        float monto_retencion = reporte.getMonto_retencion();
        float monto_final = reporte.getMonto_final();

        reporteService.guardarReporte(quincena,codigo_proveedor,nombre_proveedor,total_kls_leche,nro_dias_envio_leche,promedio_kls_leche,
                variacion_leche,grasa,variacion_grasa,solidos_totales,variacion_solidos_totales,pago_leche,pago_grasa,
                pago_solidos_totales,bonificacion_frecuencia,descuento_variacion_leche,descuento_variacion_grasa,descuento_variacion_solidos,
                pago_total,monto_retencion,monto_final);
         **/
        reporteService.guardarReporte(quincena,codigo_proveedor);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/eliminar")
    public void eliminarReporte(){
        reporteService.eliminarReporte();
    }


}
