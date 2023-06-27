import axios from 'axios';

const REPORTE_API_URL = "http://localhost:8085/reporte";

class ReporteService {

    getProveedores(){
        return axios.get(REPORTE_API_URL);
    }

    createProveedor(proveedor){
        return axios.post(REPORTE_API_URL, proveedor);
    }
}

export default new ReporteService()