package com.prg2022.proyectoQR.addons.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ReadExcel {
   
    public void leer(){
        try {
            Resource resource = new ClassPathResource("productos.xlsx");
            FileInputStream f = new FileInputStream(resource.getFile());
            XSSFWorkbook libro = new XSSFWorkbook(f);
            XSSFSheet hoja = libro.getSheetAt(0);
            Iterator<Row> filas = hoja.iterator();
            Row fila;
            while (filas.hasNext()){
                fila = filas.next();
                String Nombre = fila.getCell(0).getStringCellValue();
                String Apellidos = fila.getCell(1).getStringCellValue();
                String DNI;
                switch(fila.getCell(2).getCellType()){
                    case NUMERIC:
                        DNI = Double.toString(fila.getCell(2).getNumericCellValue());
                        break;
                    case STRING:
                        DNI = fila.getCell(2).getStringCellValue();
                        break;
                    default:
                        DNI ="";
                        break;
                }
                System.out.println(Nombre+"-"+Apellidos+"-"+DNI);

            }
        libro.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
