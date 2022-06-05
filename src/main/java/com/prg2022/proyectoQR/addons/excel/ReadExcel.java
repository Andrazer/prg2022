package com.prg2022.proyectoQR.addons.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import com.prg2022.proyectoQR.payload.response.UsuarioExcelResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadExcel {
   
    public List<UsuarioExcelResponse> leer(String pathArchivoExcel){
        try {
            /*System.out.println("Leyendo: "+pathArchivoExcel);*/
            //Resource resource = new ClassPathResource(pathArchivoExcel);

            List<UsuarioExcelResponse> usuarios = new ArrayList<UsuarioExcelResponse>();

            FileInputStream f = new FileInputStream(pathArchivoExcel);
            XSSFWorkbook libro = new XSSFWorkbook(f);
            XSSFSheet hoja = libro.getSheetAt(0);
            Iterator<Row> filas = hoja.iterator();
            Row fila;
            while (filas.hasNext()){
                fila = filas.next();
                String DNI;
                int rancho=0;
                int numero=0;
                switch(fila.getCell(3).getCellType()){
                    case NUMERIC:
                        DNI = Integer.toString((int) fila.getCell(3).getNumericCellValue());
                        break;
                    case STRING:
                        DNI = fila.getCell(3).getStringCellValue();
                        break;
                    default:
                        DNI ="";
                        break;
                }
                
                if (fila.getCell(4) != null) {
                    switch(fila.getCell(4).getCellType()){
                        case NUMERIC:
                            rancho = (int) fila.getCell(4).getNumericCellValue();
                        default:
                            break;
                    }
                }
                if (fila.getCell(5) != null) {
                    switch(fila.getCell(5).getCellType()){
                        case NUMERIC:
                            numero = (int) fila.getCell(5).getNumericCellValue();
                        default:
                            break;
                    }              
                }
                UsuarioExcelResponse leido = new UsuarioExcelResponse(
                    fila.getCell(0).getStringCellValue(), 
                    fila.getCell(1).getStringCellValue(), 
                    fila.getCell(2).getStringCellValue(), 
                    DNI,
                    rancho,
                    numero);               
                usuarios.add(leido);

            }
        libro.close();
        f.close();
        return usuarios;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

}
