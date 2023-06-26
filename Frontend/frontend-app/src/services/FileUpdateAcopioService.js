import axios from "axios";

const API_URL = "http://localhost:8083/acopio_leche/";

class FileUploadAcopioService{
    
    CargarArchivo(file){
        return axios.post(API_URL, file);
    }
    getAcopio(){
        return axios.get(API_URL);
    }
}

export default new FileUploadAcopioService()