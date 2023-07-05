package milkstgo.reporteservice.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import milkstgo.reporteservice.entities.ReporteEntity;
import milkstgo.reporteservice.models.ProveedorModel;
import milkstgo.reporteservice.models.AcopioModel;
import milkstgo.reporteservice.models.ValoresModel;
import milkstgo.reporteservice.repositories.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<String> codigosAcopio = restTemplate.getForObject("http://acopio_leche-service/acopio/proveedor", List.class);
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
        List<AcopioModel> fechasAcopio = (List<AcopioModel>) obtenerAcopioPorCodigo(codigo);
        List<ValoresModel> grasaSolido = (List<ValoresModel>) obtenerValoresPorCodigo(codigo);
        ReporteEntity reporte_proveedor = new ReporteEntity();

        reporte_proveedor.setQuincena(calcularQuincena(acopioActual.getFecha()));
        reporte_proveedor.setCodigo_proveedor(proveedorActual.getCodigo());
        reporte_proveedor.setNombre_proveedor(proveedorActual.getNombre());
        reporte_proveedor.setTotal_kls_leche(acopioActual.getKls_leche());
        reporte_proveedor.setNro_dias_envio_leche(calcularNroDias(fechasAcopio));
        reporte_proveedor.setPromedio_kls_leche(acopioActual.getKls_leche());
        reporte_proveedor.setVariacion_leche(variacionKlsDeLeche(fechasAcopio));
        reporte_proveedor.setGrasa(valoresActual.getGrasa());
        reporte_proveedor.setVariacion_grasa(variacionGrasa(grasaSolido));
        reporte_proveedor.setSolidos_totales(valoresActual.getSolido());
        reporte_proveedor.setVariacion_solidos_totales(variacionSolido(grasaSolido));
        reporte_proveedor.setPago_leche(extraCategoria(proveedorActual.getCategoria(), acopioActual.getKls_leche()));
        reporte_proveedor.setPago_grasa(extraGrasa(valoresActual.getGrasa(),acopioActual.getKls_leche()));
        reporte_proveedor.setPago_solidos_totales(extraSolidos(valoresActual.getSolido(), acopioActual.getKls_leche()));
        reporte_proveedor.setBonificacion_frecuencia(bonificacionFrecuencia(fechasAcopio,(pagoXKlsLeche(reporte_proveedor.getPago_leche(),reporte_proveedor.getPago_grasa(),reporte_proveedor.getPago_solidos_totales()))));
        reporte_proveedor.setDescuento_variacion_leche(descuentoVarLeche(reporte_proveedor.getPago_leche(),reporte_proveedor.getVariacion_leche()));
        reporte_proveedor.setDescuento_variacion_grasa(descuentoVarGrasa(reporte_proveedor.getPago_grasa(),reporte_proveedor.getVariacion_grasa()));
        reporte_proveedor.setDescuento_variacion_solidos(descuentoVarSolidos(reporte_proveedor.getPago_solidos_totales(),reporte_proveedor.getDescuento_variacion_solidos()));
        reporte_proveedor.setPago_total(pagoTotal(
                pagoXAcopio(
                        (pagoXKlsLeche(reporte_proveedor.getPago_leche(),reporte_proveedor.getPago_grasa(),reporte_proveedor.getPago_solidos_totales()))
                        ,reporte_proveedor.getBonificacion_frecuencia()
                )
                ,descuentosXAcopio(
                        reporte_proveedor.getDescuento_variacion_leche(),
                        reporte_proveedor.getDescuento_variacion_grasa(),
                        reporte_proveedor.getDescuento_variacion_solidos())
        ));
        reporte_proveedor.setMonto_retencion(montoRetencion(reporte_proveedor.getPago_total()));
        reporte_proveedor.setMonto_final(montoFinal(reporte_proveedor.getPago_total(),reporte_proveedor.getMonto_retencion()));
        reporteRepository.save(reporte_proveedor);
    }

    Float montoRetencion(Float pago_total){
        float retencion = 0.0F;
        if(pago_total > 950000){
            retencion = pago_total*0.13F;
        }
        return retencion;
    }

    Float montoFinal(Float pago_total, Float retencion){
        return pago_total - retencion;
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

    public  Float descuentoVarLeche(Float pagoLeche, Float variacionLeche){
        if (0 <= variacionLeche && variacionLeche <= 8)
            return (pagoLeche*0);
        if (9 <= variacionLeche && variacionLeche <= 25)
            return (pagoLeche*0.07F);
        if (26 <= variacionLeche && variacionLeche <= 45)
            return (pagoLeche*0.15F);
        if (46 <= variacionLeche)
            return (pagoLeche*0.30F);
        return 0.0F;
    }
    public  Float descuentoVarGrasa(Float pagoGrasa, Float variacionGrasa){
        if (0 <= variacionGrasa && variacionGrasa <= 15)
            return (pagoGrasa*0);
        if (16 <= variacionGrasa && variacionGrasa <= 25)
            return (pagoGrasa*0.12F);
        if (26 <= variacionGrasa && variacionGrasa <= 40)
            return (pagoGrasa*0.20F);
        if (41 <= variacionGrasa)
            return (pagoGrasa*0.30F);
        return 0.0F;
    }
    public  Float descuentoVarSolidos(Float pagoSolidos, Float variacionSolido){
        if (0 <= variacionSolido && variacionSolido <= 6)
            return (pagoSolidos*0);
        if (7 <= variacionSolido && variacionSolido <= 12)
            return (pagoSolidos*0.18F);
        if (13 <= variacionSolido && variacionSolido <= 35)
            return (pagoSolidos*0.27F);
        if (36 <= variacionSolido)
            return (pagoSolidos*0.45F);
        return 0.0F;
    }

    public Float variacionKlsDeLeche(List<AcopioModel> kls_leche){
        int elementos = kls_leche.size();
        Float variacion = 0.0F;
        if (elementos > 1) {
            variacion = Math.abs(((kls_leche.get(1).getKls_leche()*100)/kls_leche.get(0).getKls_leche()) - 100);
            return variacion;
        }
        return variacion;
    }

    public Float variacionGrasa(List<ValoresModel> grasa){
        int elementos = grasa.size();
        Float variacion = 0.0F;
        if (elementos > 1){
            variacion = Math.abs(((grasa.get(1).getGrasa()*100)/grasa.get(0).getGrasa())-100);
            return variacion;
        }
        return variacion;
    }
    public Float variacionSolido(List<ValoresModel> solido){
        int elementos = solido.size();
        Float variacion = 0.0F;
        if (elementos > 1){
            variacion = Math.abs((((solido.get(1).getSolido()*100)/solido.get(0).getSolido())-100));
            return variacion;
        }
        return variacion;
    }

    public int calcularNroDias(List<AcopioModel> fecha){
       int cantDias = fecha.size();
       return cantDias;
    }

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
    public float bonificacionFrecuencia(List<AcopioModel> turno, Float pagoTotalXKlsLeche){
        int contM = 0, contT = 0, i = 0;
        int cantTurnos = turno.size();
        while (i < cantTurnos){
            if (turno.get(i).getTurno() == "M")
                contM++;
            if (turno.get(i).getTurno() == "T")
                contT++;
            i++;
        }
        if((contM + contT) >= 10) {
            if (contM != 0 && contT == 0)
                return (float) (pagoTotalXKlsLeche + pagoTotalXKlsLeche * 0.12);
            if (contM == 0 && contT != 0)
                return (float) (pagoTotalXKlsLeche + pagoTotalXKlsLeche * 0.08);
            return (float) (pagoTotalXKlsLeche + pagoTotalXKlsLeche * 0.20);
        }
        return 0;
    }

    public float pagoXAcopio(Float pago_kls_leche, Float bonificacion){
        float pago_acopio_leche = pago_kls_leche + bonificacion;
        return pago_acopio_leche;
    }
    public float descuentosXAcopio(Float descuentoLeche, Float descuentoGrasa, Float descuentoSolido){
        float descuentos_acopio_leche = descuentoLeche + descuentoGrasa + descuentoSolido;
        return descuentos_acopio_leche;
    }

    public float pagoTotal(Float pago_acopio_leche, Float descuento_acopio_leche){
        Float pago_total = pago_acopio_leche - descuento_acopio_leche;
        return pago_total;
    }

    public  ArrayList<ReporteEntity> obtenerReporte(){
        return (ArrayList<ReporteEntity>)reporteRepository.findAll();
    }

    public void eliminarReporte(){
        this.reporteRepository.deleteAll();
    }

}
