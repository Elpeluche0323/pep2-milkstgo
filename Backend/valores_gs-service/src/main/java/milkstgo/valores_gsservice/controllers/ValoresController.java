package milkstgo.valores_gsservice.controllers;

import milkstgo.valores_gsservice.entities.ValoresEntity;
import milkstgo.valores_gsservice.services.ValoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/valores_gs")
public class ValoresController {
    @Autowired
    private ValoresService valoresService;

    @GetMapping
    public ResponseEntity<ArrayList<ValoresEntity>> obtenerData() {
        ArrayList<ValoresEntity> data = valoresService.obtenerData();
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/proveedor")
    public ResponseEntity<List<String>> obtenerProveedorDeData(){
        List<String> proveedor = valoresService.obtenerProveedor();
        if(proveedor.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proveedor);
    }


    @PostMapping
    public void guardarData(@RequestParam("file") MultipartFile file, RedirectAttributes ms) throws FileNotFoundException, ParseException {
        valoresService.guardar(file);
        valoresService.leerCsv("ValoresGS.csv");
    }
}
