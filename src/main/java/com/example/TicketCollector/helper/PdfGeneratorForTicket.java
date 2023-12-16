package com.example.TicketCollector.helper;

import com.example.TicketCollector.entity.BookTicketEntity;
import com.example.TicketCollector.entity.TravellingModeEntity;
import com.example.TicketCollector.repository.TravellingModeRepository;
import com.example.TicketCollector.service.CloudinaryService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfGeneratorForTicket {
    @Autowired
    TravellingModeRepository travellingModeRepository;

    @Autowired
    CloudinaryService cloudinaryService;

    public String generate(BookTicketEntity bookTicketEntity, TravellingModeEntity travellingModeEntity) throws DocumentException, IOException, MessagingException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTiltle.setSize(20);

        Paragraph paragraph1 = new Paragraph("Ticket", fontTiltle);

        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph1);

        PdfPTable table = new PdfPTable(8);

        table.setWidthPercentage(100f);
        table.setWidths(new int[]{3, 3, 3, 3, 3, 3, 3, 3});
        table.setSpacingBefore(5);

        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(CMYKColor.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(CMYKColor.WHITE);

        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Class", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Source", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Destination", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Departure time", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Arrival time", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Total Fair", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Vehicle number", font));
        table.addCell(cell);


        table.addCell(bookTicketEntity.getUserName());
        table.addCell(bookTicketEntity.getTicketClass());
        table.addCell(bookTicketEntity.getSource());
        table.addCell(bookTicketEntity.getDestination());
        table.addCell(String.valueOf(travellingModeEntity.getDepartureTime()));
        table.addCell(String.valueOf(travellingModeEntity.getArrivalTime()));
        table.addCell(String.valueOf(bookTicketEntity.getFair()));
        table.addCell(travellingModeEntity.getTravelModeNumber());


        document.add(table);
        document.close();
        outputStream.close();
        /*String filename = "C:\\Users\\MS\\Desktop\\pdfs\\" + "ticket1.pdf";
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        fileOutputStream.write(outputStream.toByteArray());
        fileOutputStream.close();*/


        return cloudinaryService.uploadFileOnCloudinary(outputStream.toByteArray());
    }
}