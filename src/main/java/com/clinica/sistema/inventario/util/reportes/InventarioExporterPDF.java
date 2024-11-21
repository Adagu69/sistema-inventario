package com.clinica.sistema.inventario.util.reportes;

import com.clinica.sistema.inventario.model.Inventario;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class InventarioExporterPDF {

    private List<Inventario> listaInventarios;

    public InventarioExporterPDF(List<Inventario> listaInventarios) {
        this.listaInventarios = listaInventarios;
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

        celda.setPhrase(new Phrase("Cantidad", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Precio", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Estado", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Fecha", fuente));
        tabla.addCell(celda);
    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        for (Inventario inventario : listaInventarios) {
            tabla.addCell(String.valueOf(inventario.getIdInventario()));
            tabla.addCell(inventario.getProducto().getNombre());
            tabla.addCell(String.valueOf(inventario.getCantidad()));
            tabla.addCell(String.valueOf(inventario.getPrecio()));
            tabla.addCell(inventario.getEstado());
            tabla.addCell(inventario.getFecha());
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
        Paragraph titulo = new Paragraph("Reporte de Inventario", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        // Crear la tabla con 5 columnas (ID, Nombre, Apellido, Email, Roles)
        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[] { 1f, 2.3f, 2.3f, 6f, 3f , 3f});  // Ancho relativo de las columnas

        // Escribir cabecera y los datos de la tabla
        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        // Añadir la tabla al documento PDF
        documento.add(tabla);

        // Cerrar el documento
        documento.close();
    }
}