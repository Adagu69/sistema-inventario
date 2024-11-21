package com.clinica.sistema.inventario.util.reportes;

import com.clinica.sistema.inventario.model.Usuario;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UsuarioExporterPDF {

    private List<Usuario> listaUsuarios;

    public UsuarioExporterPDF(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(Color.BLUE);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("ID", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Nombre", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Apellido", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Email", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Roles", fuente));
        tabla.addCell(celda);
    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        for (Usuario usuario : listaUsuarios) {
            tabla.addCell(String.valueOf(usuario.getIdUsuario()));
            tabla.addCell(usuario.getNombre());
            tabla.addCell(usuario.getApellido());
            tabla.addCell(usuario.getEmail());

            // Escribimos los roles del usuario (es posible que tengas que ajustar según la estructura de tus roles)
            String roles = usuario.getRoles().stream()
                    .map(rol -> rol.getNombre())
                    .reduce((r1, r2) -> r1 + ", " + r2)
                    .orElse("Sin roles");
            tabla.addCell(roles);
        }
    }

    public void exportar(HttpServletResponse response) throws DocumentException, IOException {
        // Configurar el documento PDF
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        // Abrir el documento
        documento.open();

        // Configuración de la fuente para el título
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLUE);
        fuente.setSize(18);

        // Título del documento
        Paragraph titulo = new Paragraph("Reporte de Usuarios", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        // Crear la tabla con 5 columnas (ID, Nombre, Apellido, Email, Roles)
        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[] { 1f, 2.3f, 2.3f, 6f, 3f });  // Ancho relativo de las columnas

        // Escribir cabecera y los datos de la tabla
        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        // Añadir la tabla al documento PDF
        documento.add(tabla);

        // Cerrar el documento
        documento.close();
    }
}
