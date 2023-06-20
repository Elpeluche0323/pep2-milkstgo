package milkstgo.reporteservice.services;


import milkstgo.reporteservice.entities.ReporteEntity;
import milkstgo.reporteservice.repositories.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;
@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;
    @Autowired
    private RestTemplate restTemplate;

    public void guardarReporte(String quincena, String codigo_proveedor){
        ReporteEntity reporte = new ReporteEntity();
        reporte.setQuincena(quincena);
        reporte.setCodigo_proveedor(codigo_proveedor);
        /**
        reporte.setNombre_proveedor(nombre_proveedor);
        reporte.setTotal_kls_leche(total_kls_leche);
        reporte.setNro_dias_envio_leche(nro_dias_envio_leche);
        reporte.setPromedio_kls_leche(promedio_kls_leche);
        reporte.setVariacion_leche(variacion_leche);
        reporte.setGrasa(grasa);
        reporte.setDescuento_variacion_grasa(variacion_grasa);
        reporte.setSolidos_totales(solidos_totales);
        reporte.setVariacion_solidos_totales(variacion_solidos_totales);
        reporte.setPago_leche(pago_leche);
        reporte.setPago_grasa(pago_grasa);
        reporte.setPago_solidos_totales(pago_solidos_totales);
        reporte.setBonificacion_frecuencia(bonificacion_frecuencia);
        reporte.setDescuento_variacion_leche(descuento_variacion_leche);
        reporte.setDescuento_variacion_grasa(descuento_variacion_grasa);
        reporte.setDescuento_variacion_solidos(descuento_variacion_solidos);
        reporte.setPago_total(pago_total);
        reporte.setMonto_retencion(monto_retencion);
        reporte.setMonto_final(monto_final);
        **/
        reporteRepository.save(reporte);
    }

    public  ArrayList<ReporteEntity> obtenerReporte(){
        return (ArrayList<ReporteEntity>)reporteRepository.findAll();
    }

    public ReporteEntity buscarReporte(String codigo_proveedor, String quincena){
        return this.reporteRepository.buscarReporte(codigo_proveedor, quincena);
    }

    public void eliminarReporte(ReporteEntity reporte){
        this.reporteRepository.delete(reporte);
    }

    public List<ReporteEntity> obtenerReporteProveedor(String codigo_proveedor){
        return reporteRepository.buscarReportePorCodigo(codigo_proveedor);
    }

    public void eliminarReporte(){
        this.reporteRepository.deleteAll();
    }
}
