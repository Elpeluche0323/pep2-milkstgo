package milkstgo.reporteservice.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import milkstgo.reporteservice.entities.ReporteEntity;
import milkstgo.reporteservice.models.ProveedorModel;
import milkstgo.reporteservice.models.AcopioModel;
import milkstgo.reporteservice.models.ValoresModel;
import milkstgo.reporteservice.repositories.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public ProveedorModel obtenerPorCodigo(String codigo){
        ProveedorModel proveedor = restTemplate.getForObject("http://proveedor-service/proveedor/" + codigo, ProveedorModel.class);
        System.out.println(proveedor);
        return proveedor;
    }

    public AcopioModel obtenerAcopioPorCodigo(String proveedor){
        AcopioModel acopio = restTemplate.getForObject("http://acopio_leche-service/proveedor/" + proveedor, AcopioModel.class);
        System.out.println(acopio);
        return acopio;
    }

    public ValoresModel obtenerValoresPorCodigo(String proveedor){
        ValoresModel valores = restTemplate.getForObject("http://valores_gs-service/proveedor/" + proveedor, ValoresModel.class);
        System.out.println(valores);
        return valores;
    }

    public AcopioModel obtenerFechaPorProveedor(String proveedor, String fecha){
        AcopioModel acopio = restTemplate.getForObject("http://acopio_leche-service/acopio/" + proveedor +"/"+ fecha, AcopioModel.class);
        System.out.println(acopio);
        return acopio;
    }

    public List<String> obtenerCodigoDeAcopio(){
        List<String> codigosAcopio = restTemplate.getForObject("http://acopio_leche-service/proveedor", List.class);
        System.out.println(codigosAcopio);
        return codigosAcopio;
    }

    public List<String> obtenerGrasas(){
        List<String> grasasValores = restTemplate.getForObject("http://valores_gs-service/proveedor", List.class);
        System.out.println(grasasValores);
        return grasasValores;
    }

    public List<String> obtenerSolidos(){
        List<String> solidosValores = restTemplate.getForObject("http://valores_gs-service/proveedor", List.class);
        System.out.println(solidosValores);
        return solidosValores;
    }

    public void reportePlanilla() throws ParseException {
        reporteRepository.deleteAll();
        List<String> listaCodigos = obtenerCodigoDeAcopio();
        for (String listaCodigo : listaCodigos) {
            calculoPlanilla(listaCodigo);
        }
    }

    public void calculoPlanilla(String codigo) throws ParseException {
        ProveedorModel proveedorActual = obtenerPorCodigo(codigo);
        AcopioModel acopioActual = obtenerAcopioPorCodigo(codigo);
        ValoresModel valoresActual = obtenerValoresPorCodigo(codigo);
        ReporteEntity reporte_proveedor = new ReporteEntity();
        reporte_proveedor.setQuincena(calcularQuincena(acopioActual.getFecha()));
        reporte_proveedor.setCodigo_proveedor(proveedorActual.getCodigo());
        reporte_proveedor.setNombre_proveedor(proveedorActual.getNombre());
        reporte_proveedor.setTotal_kls_leche(acopioActual.getKls_leche());
        //reporte_proveedor.setNro_dias_envio_leche();
        //reporte_proveedor.setPromedio_kls_leche();
        //reporte_proveedor.setVariacion_leche();
        //reporte_proveedor.setGrasa();
        //reporte_proveedor.setDescuento_variacion_grasa();
        //reporte_proveedor.setSolidos_totales();
        //reporte_proveedor.setVariacion_solidos_totales();
        //reporte_proveedor.setPago_leche();
        //reporte_proveedor.setPago_grasa();
        //reporte_proveedor.setPago_solidos_totales();
        //reporte_proveedor.setBonificacion_frecuencia();
        //reporte_proveedor.setDescuento_variacion_leche();
        //reporte_proveedor.setDescuento_variacion_grasa();
        //reporte_proveedor.setDescuento_variacion_solidos();
        //reporte_proveedor.setPago_total();
        //reporte_proveedor.setMonto_retencion();
        //reporte_proveedor.setMonto_final();
        reporteRepository.save(reporte_proveedor);
    }

    public String calcularQuincena(String fecha){
        String quincena = null, año,mes;
        int dia;
        String quincena1 = "1" , quincena2 = "2";
        String[] partes = fecha.split(";");
        año = partes[0];
        mes = partes[1];
        dia = Integer.parseInt(partes[3]);
        if (1 <= dia && dia <= 15) {
            quincena = año + "/" + mes + "/" + quincena1;
        }
        if (16 <= dia && dia <= 31) {
            quincena = año + "/" + mes + "/" + quincena2;
        }

        return quincena;
    }

//    public int calcularNroDias(List<String> fecha,){
//  }
    public float extraCategoria(String categoria, Float kls_leche) {
        switch (categoria) {
            case "A":
                return (700 * kls_leche);
            case "B":
                return (550 * kls_leche);
            case "C":
                return (400 * kls_leche);
            case "D":
                return (250 * kls_leche);
            default:
                return 0;
        }
    }

    public float extraGrasa(Float grasa,Float kls_leche ){
        if( 0 <= grasa && grasa <= 20)
            return 30*kls_leche;
        if( 21 <= grasa && grasa <= 45)
            return 80*kls_leche;
        if( 46 <= grasa && grasa <= 100)
            return 120*kls_leche;
        return 0;
    }

    // Correspondiente a un turno
    public float extraSolidos(Float solido,Float kls_leche){
        if( 0 <= solido && solido <= 7)
            return -130*kls_leche;
        if( 8 <= solido && solido <= 18)
            return -90*kls_leche;
        if( 19 <= solido && solido <= 35)
            return 95*kls_leche;
        if( 36 <= solido && solido <= 100)
            return 150*kls_leche;
        return 0;
    }

    // Pago por kls de leche
    public float pagoXKlsLeche(Float pago_leche, Float pago_grasa, Float pago_solidos_totales){
        float pago_kls_leche = pago_leche + pago_grasa + pago_solidos_totales;
        return pago_kls_leche;
    }


    // bonificacion frecuencia
    public float bonificacionFrecuencia(List<String> turno,Float pagoTotalXKlsLeche){
        int contM = 0, contT = 0;
        for(String dia : turno){
            if (dia == "M")
                contM++;
            if (dia == "T")
                contT++;
        }
        if(contM != 0 && contT == 0)
            return (float) (pagoTotalXKlsLeche + pagoTotalXKlsLeche*0.12);
        if(contM == 0 && contT != 0)
            return (float) (pagoTotalXKlsLeche + pagoTotalXKlsLeche*0.08);
        return (float) (pagoTotalXKlsLeche + pagoTotalXKlsLeche*0.20);
    }

    public float pagoXAcopio(Float pago_kls_leche, Float bonificacion){
        float pago_acopio_leche = pago_kls_leche + bonificacion;
        return pago_acopio_leche;
    }

    public  ArrayList<ReporteEntity> obtenerReporte(){
        return (ArrayList<ReporteEntity>)reporteRepository.findAll();
    }
/**
    public ReporteEntity buscarReporte(String codigo_proveedor, String quincena){
        return this.reporteRepository.buscarReporte(codigo_proveedor, quincena);
    }

    public void eliminarReporte(ReporteEntity reporte){
        this.reporteRepository.delete(reporte);
    }

    public List<ReporteEntity> obtenerReporteProveedor(String codigo_proveedor){
        return reporteRepository.buscarReportePorCodigo(codigo_proveedor);
    }
**/
    public void eliminarReporte(){
        this.reporteRepository.deleteAll();
    }

}
