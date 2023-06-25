import axios from "axios";

const API_URL = "http://localhost:8080/valores_gs/";

class FileUploadValoresService{
    
    CargarArchivo(file){
        return axios.post(API_URL, file);
    }
}

export default new FileUploadValoresService()