package milkstgo.valores_gsservice.services;

import milkstgo.valores_gsservice.entities.ValoresEntity;
import milkstgo.valores_gsservice.repositories.ValoresRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ValoresService {

    @Autowired
    private ValoresRepository valoresRepository;

    private final Logger logg = LoggerFactory.getLogger(ValoresService.class);

    public ArrayList<ValoresEntity> obtenerData() {
        return (ArrayList<ValoresEntity>) valoresRepository.findAll();
    }

    public String guardar(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            if ((!file.isEmpty()) && (filename.toUpperCase().equals("ValoresGS.csv"))) {
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                } catch (IOException e) {
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        } else {
            return "No se pudo guardar el archivo";
        }
    }

    public ValoresEntity obtenerValores(String proveedor){
        return valoresRepository.buscarData(proveedor);
    }

    public void leerCsv(String direccion) {
        String texto = "";
        BufferedReader bf = null;
        valoresRepository.deleteAll();
        try {
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            while ((bfRead = bf.readLine()) != null) {
                guardarDataDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2]);
                temp = temp + "\n" + bfRead;
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        } catch (Exception e) {
            System.err.println("No se encontro el archivo");
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    logg.error("ERROR", e);
                }
            }
        }
    }

    public void guardarData(ValoresEntity data){
        valoresRepository.save(data);
    }
    public void guardarDataDB(String proveedor, String grasa, String solidos){
        ValoresEntity newData = new ValoresEntity();
        newData.setProveedor(proveedor);
        newData.setGrasa(Float.valueOf(grasa));
        newData.setSolido(Float.valueOf(solidos));
        guardarData(newData);
    }

    public List<String> obtenerProveedor(){
        return valoresRepository.findDistinctProveedor();
    }

    public void eliminarData(ArrayList<ValoresEntity> datas){
        valoresRepository.deleteAll(datas);
    }
}
