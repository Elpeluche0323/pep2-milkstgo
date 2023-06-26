import axios from 'axios';

const PROVEEDOR_API_URL = "http://localhost:8082/proveedor";

class ProveedorService {

    getProveedores(){
        return axios.get(PROVEEDOR_API_URL);
    }

    createProveedor(proveedor){
        return axios.post(PROVEEDOR_API_URL, proveedor);
    }

    getProveedorByCodigo(proveedorCodigo){
        return axios.get(PROVEEDOR_API_URL + '/' + proveedorCodigo);
    }
}

export default new ProveedorService()