package main.java.fr.batis.impression.devis;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.IOException;
import java.net.MalformedURLException;

public class HeaderFooterPageEvent extends PdfPageEventHelper {

    private PdfTemplate t;
    private Image total;

    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(10, 20);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addHeader(writer);
        addFooter(writer);
    }

    private void addHeader(PdfWriter writer){
        PdfPTable header = new PdfPTable(1);
        try {
            // set defaults
            header.setWidths(new int[]{20});
            header.setTotalWidth(550);
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(40);
            header.getDefaultCell().setBorder(Rectangle.BOTTOM);
         
            Image logo = Image.getInstance(HeaderFooterPageEvent.class.getResource("/images/logo.png"));
            header.addCell(logo);

            // add text
            PdfPCell text = new PdfPCell();
            text.setPaddingBottom(15);
            text.setPaddingLeft(10);
            text.setBorder(Rectangle.BOTTOM);
          
            header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }

    private void addFooter(PdfWriter writer){
        PdfPTable footer = new PdfPTable(4);
        try {
            // set defaults
            footer.setWidths(new int[]{20, 10,3,1});
            footer.setTotalWidth(527);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(20);
            footer.getDefaultCell().setBorder(Rectangle.TOP);
           // footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

            // add copyright
            Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 8);
            PdfPCell copyright = new PdfPCell();
            copyright.setBorder(Rectangle.TOP);
            copyright.setHorizontalAlignment(Element.ALIGN_RIGHT);
            copyright.addElement(new Phrase("\u00A9 BATIS INC. Tél : +257 578 785 5478 . Email : batis@batis.com",footerFont ));
            footer.addCell(copyright);
            
            PdfPCell lien = new PdfPCell();
            lien.setBorder(Rectangle.TOP);
            lien.addElement(new Phrase("http://batis.com", footerFont));
            footer.addCell(lien);
            
           
            PdfPCell pageCount = new PdfPCell();
            pageCount.setBorder(Rectangle.TOP);
            pageCount.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pageCount.addElement(new Phrase(String.format("Page %d de", writer.getPageNumber()), footerFont));
            footer.addCell(pageCount);
            
            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            totalPageCount.setBorder(Rectangle.TOP);
           // totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
            footer.addCell(totalPageCount);

            // write page
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            footer.writeSelectedRows(0, -1, 34, 50, canvas);
            canvas.endMarkedContentSequence();
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
        ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
                new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.TIMES_ROMAN, 8)),
                totalWidth, 6, 0);
    }
}