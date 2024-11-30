package com.clinica.sistema.inventario.util.reportes;

import com.clinica.sistema.inventario.model.Estado;
import com.clinica.sistema.inventario.model.Usuario;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class UsuariosInactivosExporterPDF {

    public void exportar(List<Usuario> usuarios, HttpServletResponse response) throws IOException {
        // Configurar la respuesta HTTP para exportar como archivo PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios_inactivos_report.pdf");

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Título del documento
            document.add(new Paragraph("Usuarios Inactivos"));
            document.add(new Paragraph("\n"));

            // Crear la tabla
            PdfPTable table = new PdfPTable(5); // Número de columnas según tu tabla en UsuarioListar.html
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 2, 3, 3, 3}); // Anchos de las columnas (ajustar según tus necesidades)

            // Agregar los encabezados de la tabla
            table.addCell("ID");
            table.addCell("Nombre");
            table.addCell("Apellido");
            table.addCell("Email");
            table.addCell("Fecha de Creación");

            // Agregar los datos de los usuarios inactivos
            for (Usuario usuario : usuarios) {
                if (usuario.getEstado() == Estado.INACTIVO) {
                    table.addCell(String.valueOf(usuario.getIdUsuario()));
                    table.addCell(usuario.getNombre());
                    table.addCell(usuario.getApellido());
                    table.addCell(usuario.getEmail());

                    // Acceder a la fecha desde el campo embebido UsuarioFecha
                    if (usuario.getUsuarioFecha() != null && usuario.getUsuarioFecha().getFecha() != null) {
                        table.addCell(usuario.getUsuarioFecha().getFecha().toString());
                    } else {
                        table.addCell("N/A"); // Si no hay fecha disponible
                    }
                }
            }

            document.add(table);
        } catch (Exception e) {
            throw new IOException("Error al generar el archivo PDF", e);
        } finally {
            document.close();
        }
    }
}
