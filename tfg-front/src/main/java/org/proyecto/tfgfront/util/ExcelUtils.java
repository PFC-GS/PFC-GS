package org.proyecto.tfgfront.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.proyecto.tfgfront.model.Pregunta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

public class ExcelUtils {

    private static Workbook workbook;
    private static Sheet sheet;

    private static final String[] columns = {"categoria", "dificultad", "enunciado", "respuestaA", "respuestaB", "respuestaC", "respuesta usuario"};

    public static void generateExcel(List<Pregunta> preguntas, String fileName) {


        createWorkbookAndSheet();
        createHeaderRow();
        fillDataRows(preguntas);
        adjustColumnSizes();
        writeToFile(fileName);
    }


    private static void createWorkbookAndSheet() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Preguntas");
    }

    private static void createHeaderRow() {
        Row headerRow = sheet.createRow(0);

        // Create a cell style for the headers with bold font and larger size
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private static void fillDataRows(List<Pregunta> preguntas) {
        int rowNum = 1;
        for (Pregunta pregunta : preguntas) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(getCategoriaAsString(pregunta.getCategoria()));
            row.createCell(1).setCellValue(getDificultadAsString(pregunta.getDificultad()));
            row.createCell(2).setCellValue(pregunta.getEnunciado());
            row.createCell(3).setCellValue(pregunta.getRespuestaA());
            row.createCell(4).setCellValue(pregunta.getRespuestaB());
            row.createCell(5).setCellValue(pregunta.getRespuestaC());
            String respuestaUsuario = "d".equalsIgnoreCase(pregunta.getSolucion()) ? "No ha contestado" : pregunta.getSolucion();
            row.createCell(6).setCellValue(respuestaUsuario);

        }
    }

    private static void adjustColumnSizes() {
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static void writeToFile(String fileName) {
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
           // workbook.close();  omitimos el cierre para poder descargar el archivo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCategoriaAsString(int categoria) {
        switch (categoria) {
            case 1:
                return "Acceso a Datos";
            case 2:
                return "SGE";
            case 3:
                return "Futbol";
            default:
                return "";
        }
    }

    private static String getDificultadAsString(int dificultad) {
        switch (dificultad) {
            case 1:
                return "Facil";
            case 2:
                return "Normal";
            case 3:
                return "Dificil";
            default:
                return "";
        }
    }
    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Archivo borrado exitosamente.");
            } else {
                System.out.println("No se pudo borrar el archivo.");
            }
        } else {
            System.out.println("El archivo no existe.");
        }
    }
    public static void saveExcelToDownloads(String fileName) {
        String userHome = System.getProperty("user.home");
        Path rutaDescarga = FileSystems.getDefault().getPath(userHome, "Downloads");
        Path rutaObjetivo = rutaDescarga.resolve(fileName);

        try (OutputStream outputStream = new FileOutputStream(rutaObjetivo.toFile())) {
            workbook.write(outputStream);
            System.out.println("Excel guardado exitosamente en la carpeta de Descargas.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar el Excel en la carpeta de Descargas.");
        }
    }


}
