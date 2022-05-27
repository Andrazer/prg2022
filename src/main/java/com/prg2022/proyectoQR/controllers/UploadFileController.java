package com.prg2022.proyectoQR.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.prg2022.proyectoQR.payload.request.UploadFileRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller

public class UploadFileController {

    @RequestMapping(value = "/usuarios/subidaUnArchivo", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>>  uploadOneFileHandlerPOST(HttpServletRequest request, @ModelAttribute("miformulario") UploadFileRequest archivos) {
        return ResponseEntity.ok(this.subir(request, archivos));
    }


    private List<String> subir(HttpServletRequest request, UploadFileRequest archivos) {
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
                    File archivoServidor = new File(carpeta.getAbsolutePath() + File.separator + nombre);
                    //String miruta=carpeta.getAbsolutePath();
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(archivoServidor));
                    stream.write(fileData.getBytes());
                    stream.close();
                    //archivoSubido.add(archivoServidor);
                    archivoSubido.add(nombre);
                } catch (Exception e) {
                    failedFiles.add(nombre);
                }
            }
        }
        //aqui si tenemos xls o csv... alta de usuarios, si tenemos fotos... a fotos de usuario
        //si falla, devolvemos error con motivo del fallo
        //modelo.addAttribute("archivoSubido", archivoSubido);
        //modelo.addAttribute("failedFiles", failedFiles);
        return archivoSubido;
    }
}
