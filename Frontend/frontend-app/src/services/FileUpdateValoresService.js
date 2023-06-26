import axios from "axios";

const API_URL = "http://localhost:8084/valores_gs/";

class FileUploadValoresService{
    
    CargarArchivo(file){
        return axios.post(API_URL, file);
    }
    getValores(){
        return axios.get(API_URL);
    }
}

export default new FileUploadValoresService()