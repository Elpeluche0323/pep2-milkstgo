import axios from "axios";

const API_URL = "http://localhost:8080/acopio_leche/";

class FileUploadAcopioService{
    
    CargarArchivo(file){
        return axios.post(API_URL, file);
    }
}

export default new FileUploadAcopioService()