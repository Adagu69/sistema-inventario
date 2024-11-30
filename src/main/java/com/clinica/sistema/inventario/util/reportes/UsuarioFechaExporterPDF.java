package com.clinica.sistema.inventario.util.reportes;

import com.clinica.sistema.inventario.model.Usuario;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UsuarioFechaExporterPDF {

    public void generarReportePorFecha(List<Usuario> usuarios, HttpServletResponse response) throws DocumentException, IOException {
        // Configuración del documento PDF
        Document document = new Document();
        try {
            // Configurar la respuesta HTTP
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=usuarios_por_fecha.pdf");

            // Crear el escritor de PDF
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Agrupar usuarios por mes y año
            Map<Integer, List<Usuario>> usuariosPorMes = usuarios.stream()
                    .collect(Collectors.groupingBy(usuario -> usuario.getUsuarioFecha().getFecha().getMonthValue()));

            // Títulos de los meses
            String[] nombresMeses = {
                    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
            };

            // Iterar y crear contenido para cada mes
            for (int i = 0; i < 12; i++) {
                if (usuariosPorMes.containsKey(i + 1)) {
                    // Título del mes
                    Paragraph tituloMes = new Paragraph(nombresMeses[i], FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
                    tituloMes.setAlignment(Element.ALIGN_CENTER);
                    document.add(tituloMes);
                    document.add(Chunk.NEWLINE);

                    // Crear tabla de usuarios del mes con todas las columnas necesarias
                    PdfPTable table = new PdfPTable(5); // Ajusta el número de columnas según la cantidad de datos
                    table.setWidthPercentage(100);

                    // Encabezados de la tabla
                    table.addCell("Nombre");
                    table.addCell("Apellido");
                    table.addCell("Email");
                    table.addCell("Fecha de Creación");
                    table.addCell("Estado");

                    // Añadir los usuarios a la tabla
                    for (Usuario usuario : usuariosPorMes.get(i + 1)) {
                        table.addCell(usuario.getNombre());
                        table.addCell(usuario.getApellido());
                        table.addCell(usuario.getEmail());
                        table.addCell(usuario.getUsuarioFecha().getFecha().toString()); // Acceso correcto a la fecha
                        table.addCell(usuario.getEstado().toString()); // Muestra el estado como texto
                    }

                    document.add(table);
                    document.add(Chunk.NEWLINE);
                }
            }

            document.close();
        } catch (DocumentException e) {
            throw new IOException("Error al generar el reporte: " + e.getMessage(), e);
        }
    }
}
