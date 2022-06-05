package com.prg2022.proyectoQR.addons;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.FileSystemUtils;


public class borrarArchivosAntiguos {
    public void borrar(){
        try {
            List<Path> rutaTemporal = 
                Files.list(FileSystems.getDefault().getPath("archivos_subidos")).collect(Collectors.toList());
            for(Path posicion:rutaTemporal) {
                //ver si tiene mas de 1 hora
                FileTime este = (FileTime) Files.getAttribute(posicion, "creationTime");
                if (este.toInstant().isBefore(Instant.now().minusSeconds(60*60*1))){
                    FileSystemUtils.deleteRecursively(posicion);
                }
            }
        } catch (IOException ex) { /**/ }
    }
}
