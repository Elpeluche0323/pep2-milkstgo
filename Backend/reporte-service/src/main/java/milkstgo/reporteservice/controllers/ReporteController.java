package milkstgo.reporteservice.controllers;

import milkstgo.reporteservice.entities.ReporteEntity;
import milkstgo.reporteservice.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.text.ParseException;
import java.util.ArrayList;

@RestController
@RequestMapping("/reporte")
public class ReporteController {
    @Autowired
    ReporteService reporteService;

    @GetMapping
    public ResponseEntity<ArrayList<ReporteEntity>> planillaDeReporte() throws ParseException {
        reporteService.reportePlanilla();
        ArrayList<ReporteEntity> reporte = reporteService.obtenerReporte();
        if(reporte.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/eliminar")
    public void eliminarReporte(){
        reporteService.eliminarReporte();
    }


}
