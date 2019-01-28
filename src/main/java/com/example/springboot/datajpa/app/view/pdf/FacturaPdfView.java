package com.example.springboot.datajpa.app.view.pdf;

import com.example.springboot.datajpa.app.models.entity.Factura;
import com.example.springboot.datajpa.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Locale;
import java.util.Map;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {

  private final MessageSource messageSource;
  private final LocaleResolver localeResolver;

  public FacturaPdfView(MessageSource messageSource, LocaleResolver localeResolver) {
    this.messageSource = messageSource;
    this.localeResolver = localeResolver;
  }

  @Override
  protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Factura factura = (Factura) map.get("factura");

    Locale locale = localeResolver.resolveLocale(request);

    MessageSourceAccessor messages = getMessageSourceAccessor();

    PdfPTable tabla1 = new PdfPTable(1);
    tabla1.setSpacingAfter(20);

    PdfPCell cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente", null, locale)));
    cell.setBackgroundColor(new Color(184, 218, 255));
    cell.setPadding(8f);

    tabla1.addCell(cell);


    tabla1.addCell(factura.getCliente().toString());
    tabla1.addCell(factura.getCliente().getEmail());

    PdfPTable tabla2 = new PdfPTable(1);
    tabla2.setSpacingAfter(20);

    cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura", null, locale)));
    cell.setBackgroundColor(new Color(195, 230, 203));
    cell.setPadding(8f);

    tabla2.addCell(cell);
    tabla2.addCell(messages.getMessage("text.cliente.factura.folio") + ": " + factura.getId());
    tabla2.addCell("Descripción: " + factura.getDescripcion());
    tabla2.addCell("Fecha: " + factura.getCreateAt());

    PdfPTable tabla3 = new PdfPTable(4);
    tabla3.setWidths(new float[]{3.5f, 1, 1, 1});
    tabla3.addCell("Producto");
    tabla3.addCell("Precio");
    tabla3.addCell("Cantidad");
    tabla3.addCell("Importe");

    for (ItemFactura item : factura.getItems()) {
      tabla3.addCell(item.getProducto().getNombre());

      cell = new PdfPCell(new Phrase(formatCurrency(item.getProducto().getPrecio())));
      cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
      tabla3.addCell(cell);

      cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
      cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
      tabla3.addCell(cell);

      cell = new PdfPCell(new Phrase(formatCurrency(item.calcularImporte())));
      cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
      tabla3.addCell(cell);
    }

    cell = new PdfPCell(new Phrase("Total: "));
    cell.setColspan(3);
    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
    tabla3.addCell(cell);

    cell = new PdfPCell(new Phrase(formatCurrency(factura.getTotal())));
    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
    tabla3.addCell(cell);

    document.addTitle("Factura del Cliente");
    document.add(tabla1);
    document.add(tabla2);
    document.add(tabla3);
  }

  private String formatCurrency(Number number) {
    return "$" + String.format("%.2f", number);
  }
}
