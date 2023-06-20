package milkstgo.acopio_lecheservice.services;


import milkstgo.acopio_lecheservice.entities.AcopioEntity;
import milkstgo.acopio_lecheservice.repositories.AcopioRepository;
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
public class AcopioService {

    @Autowired
    private AcopioRepository acopioRepository;

    private final Logger logg = LoggerFactory.getLogger(AcopioService.class);

    public ArrayList<AcopioEntity> obtenerData() {
        return (ArrayList<AcopioEntity>) acopioRepository.findAll();
    }

    public String guardar(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            if ((!file.isEmpty()) && (filename.toUpperCase().equals("Acopio.csv"))) {
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

    public AcopioEntity obtenerAcopio(String proveedor, String fecha){
        return acopioRepository.buscarData(proveedor, fecha);
    }

    public void leerCsv(String direccion) {
        String texto = "";
        BufferedReader bf = null;
        acopioRepository.deleteAll();
        try {
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            while ((bfRead = bf.readLine()) != null) {
                String fecha = bfRead.split(";")[0];
                String newFecha = fecha.replaceAll("/","-");
                guardarDataDB(newFecha, bfRead.split(";")[1], bfRead.split(";")[2], bfRead.split(";")[3]);
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

    public void guardarData(AcopioEntity data){
        acopioRepository.save(data);
    }
    public void guardarDataDB(String fecha, String turno, String proveedor, String kls_leche){
        AcopioEntity newData = new AcopioEntity();
        newData.setFecha(fecha);
        newData.setTurno(turno);
        newData.setProveedor(proveedor);
        newData.setKls_leche(Float.valueOf(kls_leche));
        guardarData(newData);
    }

    public  String obtenerFechaProveedor(String proveedor){
        return acopioRepository.buscarFechaProveedor(proveedor);
    }

    public List<String> obtenerProveedor(){
        return acopioRepository.findDistinctProveedor();
    }

    public void eliminarData(ArrayList<AcopioEntity> datas){
        acopioRepository.deleteAll(datas);
    }


    public static float sueldoLecheCategoria(String categoria, float kls_leche){
        switch(categoria){
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
}
