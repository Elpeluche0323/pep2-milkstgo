package milkstgo.acopio_lecheservice.controllers;

import milkstgo.acopio_lecheservice.config.RestTemplate;
import milkstgo.acopio_lecheservice.entities.AcopioEntity;
import milkstgo.acopio_lecheservice.services.AcopioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/acopio_leche")
public class AcopioController {

    @Autowired
    private AcopioService acopioService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<ArrayList<AcopioEntity>> obtenerData() {
        ArrayList<AcopioEntity> data = acopioService.obtenerData();
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/proveedor")
    public ResponseEntity<List<String>> obtenerProveedorDeData(){
        List<String> proveedor = acopioService.obtenerProveedor();
        if(proveedor.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proveedor);
    }

    @GetMapping("/acopio/{proveedor}/{fecha}")
    public ResponseEntity<AcopioEntity> obtenerFechaPorProveedor(
            @PathVariable("proveedor") String proveedor, @PathVariable("fecha") String fecha)
    {
        AcopioEntity fecha_envio = acopioService.obtenerAcopio(proveedor, fecha);
        return ResponseEntity.ok(fecha_envio);
    }

    @GetMapping("/primeraasistencia/{proveedor}")
    public ResponseEntity<String> obtenerPrimeraAsistencia(@PathVariable("proveedor") String proveedor){
        String fecha = acopioService.obtenerFechaProveedor(proveedor);
        if (fecha.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fecha);
    }

    @PostMapping
    public void guardarData(@RequestParam("file") MultipartFile file, RedirectAttributes ms) throws FileNotFoundException, ParseException{
        acopioService.guardar(file);
        acopioService.leerCsv("Acopio.csv");
    }
    @GetMapping("/sueldo-categoria/{categoria}")
    public float sueldoCategoria(@PathVariable String categoria, AcopioEntity kls_leche) {
        try {
            String proveedorUrl = "http://proveedor-service/codigo/" + categoria;
            String categoriaProveedor = restTemplate.getForObject(proveedorUrl, String.class);

            if (categoriaProveedor != null && !categoriaProveedor.isEmpty()) {
                float lecheCategoria = AcopioService.sueldoLecheCategoria(categoriaProveedor, kls_leche.getKls_leche());
                return lecheCategoria;
            } else {
                throw new RuntimeException("La categoría del proveedor es nula o vacía");
            }
        } catch (HttpClientErrorException e) {
            // Manejar excepción de error HTTP
            throw new RuntimeException("Error al obtener la categoría del proveedor", e);
        } catch (Exception e) {
            // Manejar otras excepciones
            throw new RuntimeException("Ocurrió un error en el cálculo del sueldo de la leche", e);
        }
    }

}
