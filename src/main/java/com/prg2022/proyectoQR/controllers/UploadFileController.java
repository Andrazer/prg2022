package com.prg2022.proyectoQR.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.prg2022.proyectoQR.payload.request.UploadFileRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller

public class UploadFileController {
    String tmpUploadFolder = "archivos_subidos";
    File carpeta = new File(tmpUploadFolder);

    @RequestMapping(value = "/usuarios/subidaUnArchivo", method = RequestMethod.POST)
    public String uploadOneFileHandlerPOST(HttpServletRequest request, Model modelo, @ModelAttribute("miformulario") UploadFileRequest archivos) {
        return this.subir(request, modelo, archivos);
    }


    private String subir(HttpServletRequest request, Model modelo, UploadFileRequest archivos) {
        MultipartFile[] fileDatas = archivos.getFileDatas();
        List<File> archivoSubido = new ArrayList<File>();
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
                    archivoSubido.add(archivoServidor);
                } catch (Exception e) {
                    failedFiles.add(nombre);
                }
            }
        }
        //aqui si tenemos xls o csv... alta de usuarios, si tenemos fotos... a fotos de usuario
        //si falla, devolvemos error con motivo del fallo
        modelo.addAttribute("archivoSubido", archivoSubido);
        modelo.addAttribute("failedFiles", failedFiles);
        return "tests/resultadosubida";
    }
}
