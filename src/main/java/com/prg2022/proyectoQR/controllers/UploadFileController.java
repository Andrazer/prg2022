package com.prg2022.proyectoQR.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.prg2022.proyectoQR.Repository.BrigadaRepository;
import com.prg2022.proyectoQR.Repository.UsuarioRepository;
import com.prg2022.proyectoQR.addons.borrarArchivosAntiguos;
import com.prg2022.proyectoQR.addons.excel.ReadExcel;
import com.prg2022.proyectoQR.addons.numeroBrigada.generador;
import com.prg2022.proyectoQR.modelos.Brigada;
import com.prg2022.proyectoQR.modelos.Usuario;
import com.prg2022.proyectoQR.payload.request.UploadFileRequest;
import com.prg2022.proyectoQR.payload.response.UsuarioExcelResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;



@Controller


public class UploadFileController {
    @Autowired
    private UsuarioRepository urepository;    
    @Autowired
    private BrigadaRepository brepository;    



    @RequestMapping(value = "/subeFotos/{id}", method = RequestMethod.POST)
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> subeFotos (
                                    @PathVariable Long id,
                                    HttpServletRequest request,
                                    UploadFileRequest subeFoto){
        
        MultipartFile[] fileDatas = subeFoto.getFileDatas();
        for (MultipartFile subidos : fileDatas) {
            /* COMPRUEBA CADA ARCHIVO SUBIDO */
            String extension = FilenameUtils.getExtension(subidos.getOriginalFilename()).toLowerCase();
            String[] permitidos = {"jpg","jpeg","png","bmp"};
            /* segun la extension podemos hacer una cosa u otra */
            if (extension.equalsIgnoreCase("zip")){
                try {
                    ZipEntry entry = null;
                    ZipInputStream zis = new ZipInputStream(subidos.getInputStream());
                    while (( entry = zis.getNextEntry()) != null) {
                        String zipPath = entry.getName();
                        String extUnziped = FilenameUtils.getExtension(zipPath).toLowerCase();

                        if(Arrays.stream(permitidos).anyMatch(extUnziped::equals)){
                            String quienEs = zipPath.replaceFirst("[.][^.]+$", "");
                            Optional<Usuario> pepe = urepository.findBynumeroDeBrigadaIs(quienEs);
                            // si encontramos al usuario y tenemos el archivo, seteamos la foto
                            if (!pepe.isEmpty()){
                                Usuario encontrado = pepe.get();
                                if (encontrado.getBrigadaId()==id){
                                    //el usuario pertenece a la brigada que estamos editando
                                    String rutaCorta = encontrado.getBrigadaId()+"/";
                                    String rutaLarga = "src/main/resources/static/fotos/"+rutaCorta;
                                    File file = new File(rutaLarga+""+encontrado.getId()+"."+extension);
                                    if (!file.exists()) {
                                        File pathDir = file.getParentFile();
                                        pathDir.mkdirs();
                                        file.createNewFile();
                                     }
                                    FileOutputStream fos = new FileOutputStream(file);
                                    int bread;
                                    while ((bread = zis.read()) != -1) { 
                                        fos.write(bread);
                                    }
                                    fos.close();
                                    encontrado.setFoto(rutaCorta+""+encontrado.getId()+"."+extension);
                                    urepository.save(encontrado);
                                }
                            }
                        } else {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IMAGEN NO PROCESADA");
                        }
                    }                    
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IMAGEN NO PROCESADA");
                }

            } else if(Arrays.stream(permitidos).anyMatch(extension::equals)) {

                String quienEs = subidos.getOriginalFilename().replaceFirst("[.][^.]+$", "");
                Optional<Usuario> pepe = urepository.findBynumeroDeBrigadaIs(quienEs);
                if (!pepe.isEmpty()){
                    Usuario encontrado = pepe.get();
                    String rutaCorta = encontrado.getBrigadaId()+"/";
                    String rutaLarga = "src/main/resources/static/fotos/"+rutaCorta;
                    File carpeta = new File(rutaLarga);

                    if (!carpeta.exists()) { carpeta.mkdirs(); }
                    File archivoServidor = new File(rutaLarga+""+encontrado.getId()+"."+extension);
                    try {
                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(archivoServidor));
                        stream.write(fileDatas[0].getBytes());
                        stream.close();
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IMAGEN NO PROCESADA");
                    }

                    encontrado.setFoto(rutaCorta+""+encontrado.getId()+"."+extension);
                    urepository.save(encontrado);
               }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IMAGEN NO PROCESADA");
            }  
            
        }

        return ResponseEntity.ok("");
    }



    @RequestMapping(value = "/subeFoto/{id}", method = RequestMethod.POST)
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String>  subeFoto(
                                        @PathVariable Long id,
                                        HttpServletRequest request,
                                        UploadFileRequest subeFoto) {
        if (setFoto(id, subeFoto.getFileDatas())) {
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IMAGEN NO PROCESADA");
        }
    }



    @RequestMapping(        
        value = "/usuarios/ProcesaExcel/{id}/{rancho}", 
        method = RequestMethod.POST, 
        produces= MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseBody
    public String Procesado(@CookieValue(name = "operacion") String operacion,
        @PathVariable Long id,
        @PathVariable int rancho
        ){
        File carpeta = new File("archivos_subidos/"+operacion);
        String contents[] = carpeta.list();
        ReadExcel leer = new ReadExcel();
        Brigada brigadaUp = brepository.getById(id);
        /*TAL COMO ADVIERTE, LIMPIAMOS DE USUARIOS LA BRIGADA */
        List<Usuario> borrados = urepository.findByBrigada(brigadaUp);
        for (Usuario borra : borrados){
            urepository.deleteById(borra.getId());
        }
        

        generador NumBrigada =  new generador(brigadaUp.getGrupo(), brigadaUp.getLetra(),rancho);
        List<UsuarioExcelResponse> leidos = leer.leer("archivos_subidos/"+operacion+"/"+contents[0]);
        for (UsuarioExcelResponse leido : leidos) {


            Object[] usuarioNumero = NumBrigada.getRancho();
            int meteRancho = (int)usuarioNumero[0];
            int meteNumero = (int)usuarioNumero[1];
            int meteGrupo = (int)usuarioNumero[2];
            String meteLetra = (String)usuarioNumero[3];   
            
            if ((leido.getRancho()>0) && (leido.getNumero()>0)){
                meteRancho = leido.getRancho();
                meteNumero = leido.getNumero();                
            }                    
            
            urepository.save(new Usuario(
                leido.getNombre(), 
                leido.getApellido1(), 
                leido.getApellido2(), 
                leido.getDni(), 
                brigadaUp, 
                meteRancho,
                meteNumero,
                meteGrupo,
                meteLetra
                ));            
        }
        return operacion;
    }
    
    @RequestMapping(        
        value = "/usuarios/NuevoExcel", 
        method = RequestMethod.POST, 
        produces= MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<?> SubeExcelPOST(
        HttpServletRequest request,UploadFileRequest archivos,Model model) throws JsonProcessingException { 

            borrarArchivosAntiguos borrado = new borrarArchivosAntiguos();
            borrado.borrar();
            
            String identificador = UUID.randomUUID().toString();
            String tmpUploadFolder = "archivos_subidos/"+identificador;
            File carpeta = new File(tmpUploadFolder);
            MultipartFile[] fileDatas = archivos.getFileDatas();
            List<String> archivoSubido = new ArrayList<String>();
            List<UsuarioExcelResponse> leidos = new ArrayList<UsuarioExcelResponse>();
            String ruta = "";
            if (!carpeta.exists()) { carpeta.mkdirs(); }
            String nombre = fileDatas[0].getOriginalFilename();
            String extension = FilenameUtils.getExtension(nombre).toLowerCase();
            String[] permitidos = {"XLSX","xlsx","XLS","xls"};
            if(!Arrays.stream(permitidos).anyMatch(extension::equals)) { 
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo debe ser Excel"); 
            }      

            if (nombre != null && nombre.length() > 0) {
                ruta = carpeta.getAbsolutePath() + File.separator + nombre;
                try {
                    File archivoServidor = new File(ruta);
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(archivoServidor));
                    stream.write(fileDatas[0].getBytes());
                    stream.close();
                    archivoSubido.add(nombre);
                    ReadExcel leer = new ReadExcel();
                    leidos = leer.leer(ruta);
                } catch (Exception e) {
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo debe ser Excel"); 
                }
            }
            ResponseCookie cookie = ResponseCookie.from("operacion", identificador)
                .path("/")
                .maxAge(10*60)
                .httpOnly(true).build();   
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(leidos);
        }

/* 
    @RequestMapping(value = "/usuarios/subirExcel", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<?>>  uploadOneFileHandlerPOST(
            HttpServletRequest request, 
            @ModelAttribute("cargando") UploadFileRequest archivos) {
        return ResponseEntity.ok(this.subir(request, archivos));
    }
    private List<?> subir(HttpServletRequest request, UploadFileRequest archivos) {
        //Carlos: al subir hace limpieza, si los archivos o carpetas dentro de archivos_subidos
        // llevan mas de 24h, borrar
        String tmpUploadFolder = "archivos_subidos/"+UUID.randomUUID().toString();
        File carpeta = new File(tmpUploadFolder);

        
        MultipartFile[] fileDatas = archivos.getFileDatas();
        //List<File> archivoSubido = new ArrayList<File>();
        List<String> archivoSubido = new ArrayList<String>();
        List<String> failedFiles = new ArrayList<String>();

        if (!carpeta.exists()) { carpeta.mkdirs(); }

        for (MultipartFile fileData : fileDatas) {
            String nombre = fileData.getOriginalFilename();
            if (nombre != null && nombre.length() > 0) {
                try {
                    String ruta = carpeta.getAbsolutePath() + File.separator + nombre;
                    File archivoServidor = new File(ruta);
                    //String miruta=carpeta.getAbsolutePath();
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(archivoServidor));
                    stream.write(fileData.getBytes());
                    stream.close();
                    //archivoSubido.add(archivoServidor);
                    archivoSubido.add(nombre);
                    //suponemos que es un excel
                    ReadExcel leer = new ReadExcel();
                    List<UsuarioExcelResponse> leidos = leer.leer(ruta);
                    for (UsuarioExcelResponse leido : leidos) {
                        System.out.println(leido.datos());
                    }
                    return leidos;
                } catch (Exception e) {
                    failedFiles.add(nombre);
                }
            }
        }
        //aqui si tenemos xls o csv... alta de usuarios, si tenemos fotos... a fotos de usuario
        //si falla, devolvemos error con motivo del fallo
        //modelo.addAttribute("archivoSubido", archivoSubido);
        //modelo.addAttribute("failedFiles", failedFiles);
        //JSONObject crunchifyJSON1 = new JSONObject();
        return archivoSubido;
    }
*/
    private boolean setFoto(Long id,  MultipartFile[] fileDatas){
        Optional<Usuario> buscado = urepository.findById(id);
        if (buscado.isEmpty()){return false;}
        Usuario usuario = buscado.get();
        String extension = FilenameUtils.getExtension(fileDatas[0].getOriginalFilename()).toLowerCase();
        String[] permitidos = {"jpg","jpeg","png","bmp"};
        if(!Arrays.stream(permitidos).anyMatch(extension::equals)) { return false; }        
        String rutaCorta = usuario.getBrigadaId()+"/";
        String rutaLarga = "src/main/resources/static/fotos/"+rutaCorta;
        File carpeta = new File(rutaLarga);
        if (!carpeta.exists()) { carpeta.mkdirs(); }
        File archivoServidor = new File(rutaLarga+""+usuario.getId()+"."+extension);
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(archivoServidor));
            stream.write(fileDatas[0].getBytes());
            stream.close();
        } catch (Exception e) {
            return false;
        }
        usuario.setFoto(rutaCorta+""+usuario.getId()+"."+extension);
        urepository.save(usuario);
        return true;
    }


}
