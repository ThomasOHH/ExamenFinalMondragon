/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.semana05.Controller;

import com.example.semana05.model.Mascota;
import com.example.semana05.service.MascotaService;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.properties.TextAlignment;
import jakarta.persistence.Table;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.wp.usermodel.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/mascotas")
public class MascotaController {
     @Autowired
    private MascotaService service;

    @GetMapping
    public String listarMascotas(Model model) {
        model.addAttribute("mascotas", service.listarTodas());
        return "mascotas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("mascota", new Mascota());
        return "mascotas/formulario";
    }

    @PostMapping
    public String guardarMascota(@ModelAttribute Mascota mascota) {
        service.guardar(mascota);
        return "redirect:/mascotas";
    }

    @GetMapping("/editar/{id}")
    public String editarMascota(@PathVariable Long id, Model model) {
        Mascota mascota = service.obtenerPorId(id);
        model.addAttribute("mascota", mascota);
        return "mascotas/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMascota(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/mascotas";
    }
     @GetMapping("/mascotas/reporte/excel")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        // Configuración de tipo de contenido y encabezado de la respuesta
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=Mascotas.xlsx");

        // Creación del libro y la hoja de cálculo
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Mascotas");

        // Encabezados de la tabla
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Nombre");
        header.createCell(2).setCellValue("Tipo");
        header.createCell(3).setCellValue("Raza");
        header.createCell(4).setCellValue("Edad");
        header.createCell(5).setCellValue("Sexo");
        header.createCell(6).setCellValue("Nombre Dueño");
        header.createCell(7).setCellValue("Teléfono Dueño");

        // Obtención de los datos y llenado de la tabla
        List<Mascota> mascotas = service.listarTodas();
        int rowIdx = 1;
        for (Mascota mascota : mascotas) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(mascota.getId());
            row.createCell(1).setCellValue(mascota.getNombre());
            row.createCell(2).setCellValue(mascota.getTipo());
            row.createCell(3).setCellValue(mascota.getRaza());
            row.createCell(4).setCellValue(mascota.getEdad());
            row.createCell(5).setCellValue(mascota.getSexo());
            row.createCell(6).setCellValue(mascota.getNombreDueno());
            row.createCell(7).setCellValue(mascota.getTelefonoDueno());
        }

        // Escritura del archivo en la respuesta
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    /**
     * Exportar datos de mascotas a un archivo PDF.
     */
    @GetMapping("/mascotas/reporte/pdf")
    public void exportarPDF(HttpServletResponse response) throws IOException {
        // Configuración de tipo de contenido y encabezado de la respuesta
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Mascotas.pdf");

        // Creación del archivo PDF
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Título del documento
        document.add(new Paragraph("Reporte de Mascotas").setBold().setFontSize(14).setTextAlignment(TextAlignment.CENTER));

        // Creación de una tabla con columnas
        Table table = new Table(new float[]{1, 2, 2, 2, 1, 1, 2, 2}) {};
        table.setWidthPercent(100);

        // Encabezados de la tabla
        table.addHeaderCell(new Cell().add(new Paragraph("ID").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Nombre").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Tipo").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Raza").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Edad").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Sexo").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Nombre Dueño").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Teléfono Dueño").setBold()));

        // Obtención de los datos y llenado de la tabla
        List<Mascota> mascotas = service.listarTodas();
        for (Mascota mascota : mascotas) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(mascota.getId()))));
            table.addCell(new Cell().add(new Paragraph(mascota.getNombre())));
            table.addCell(new Cell().add(new Paragraph(mascota.getTipo())));
            table.addCell(new Cell().add(new Paragraph(mascota.getRaza())));
            table.addCell(new Cell().add(new Paragraph(mascota.getEdad())));
            table.addCell(new Cell().add(new Paragraph(mascota.getSexo())));
            table.addCell(new Cell().add(new Paragraph(mascota.getNombreDueno())));
            table.addCell(new Cell().add(new Paragraph(mascota.getTelefonoDueno())));
        }

        // Añadir la tabla al documento
        document.add(table);
        document.close();
    }
}
