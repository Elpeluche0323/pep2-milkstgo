import './App.module.css';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeComponent from './components/HomeComponent';
import FileUploadAcopioComponent from './components/FileUploadAcopioComponent';
import FileUploadValoresComponent from './components/FileUploadValoresComponent';
import ProveedorComponent from './components/ProveedorComponent';
import CreateProveedorComponent from './components/CreateProveedorComponent';
import ReporteComponent from './components/ReporteComponent';
import FileInformationAcopioComponent from './components/FileInformationAcopioComponent';
import FileInformationValoresComponent from './components/FileInformationValoresComponent';
// import EmployeeComponent from './components/EmployeeComponent';
// import JustificativoComponent from './components/JustificativoComponent';
// import AutorizacionComponent from './components/AutorizacionComponent';
// import SueldosComponent from './components/SueldosComponent';
function App() {
  return (
    <div>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomeComponent />} />
        <Route path= "/subir-archivo-acopio" element={<FileUploadAcopioComponent/>} />
        <Route path= "/lista-proveedores" element={<ProveedorComponent />} />
        <Route path= "/subir-archivo-valores" element={<FileUploadValoresComponent />} />
        <Route path= "/nuevo-proveedor" element={<CreateProveedorComponent />} />
        <Route path= "/reporte" element={<ReporteComponent/>} />
        <Route path= "/informacion-archivo-acopio" element={<FileInformationAcopioComponent />} />
        <Route path= "/informacion-archivo-valores" element={<FileInformationValoresComponent />}/>
      </Routes>
    </BrowserRouter>
  </div>
  );
}

export default App;

/**
 * <Route path= "/informacion-archivo-acopio" element={<FileInformationAcopioComponent />} />
        <Route path= "/informacion-archivo-valores" element={<FileInformationValoresComponent />} />
        <Route path= "/lista-empleados" element={<EmployeeComponent />} />
        <Route path= "/justificativo" element={<JustificativoComponent />} />
        <Route path= "/autorizacion" element={<AutorizacionComponent />} />
        <Route path= "/planilla-sueldos" element={<SueldosComponent />} />
 */