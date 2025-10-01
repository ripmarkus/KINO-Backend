package com.example.kinoxp.service.booking;

import com.example.kinoxp.model.booking.Reservation;
import com.example.kinoxp.model.booking.Ticket;
import com.example.kinoxp.model.movie.Movie;
import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.theatre.Theatre;
import com.example.kinoxp.repository.Booking.ReservationRepo;
import com.example.kinoxp.repository.Booking.TicketRepo;
import com.example.kinoxp.repository.Theatre.ScreeningRepo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.itextpdf.text.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.itextpdf.text.pdf.PdfWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;


@Service
public class TicketPdfService {


    private final ReservationRepo reservationRepo;
    private final TicketRepo ticketRepo;
    private final ScreeningRepo screeningRepo;

    public TicketPdfService(ReservationRepo reservationRepo, TicketRepo ticketRepo, ScreeningRepo screeningRepo) {
        this.reservationRepo = reservationRepo;
        this.ticketRepo = ticketRepo;
        this.screeningRepo = screeningRepo;
    }

    public byte[] generateReservationPdf(Integer reservationId) {
        Reservation reservation = reservationRepo.findByReservationId(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found!"));

        List<Ticket> tickets = ticketRepo.findByReservationIdWithSeats(reservationId);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document doc = new Document(PageSize.A6, 20, 20, 20, 20);
            PdfWriter writer = PdfWriter.getInstance(doc, baos);
            doc.open();

            Font headerFont = new Font();
            Font normalFont = new Font();


            doc.add(new Paragraph("Cinema Ticket", headerFont));
            doc.add(new Paragraph("ReservationID: " + reservation.getReservationId(), normalFont));

            Screening screening = reservation.getScreening();
            Movie movie = screening.getMovie();
            Theatre theatre = screening.getTheatre();
            String showTime = screening.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));


            doc.add(new Paragraph("Film: " + movie.getTitle(), normalFont));
            doc.add(new Paragraph("Dato/Tid: " + showTime, normalFont));
            doc.add(new Paragraph("Sal: " + theatre.getName(), normalFont));

            doc.add(new Paragraph("Kunde: " + reservation.getCustomer().getName(), normalFont));
            doc.add(new Paragraph("Email: " + reservation.getCustomer().getEmail(), normalFont));


            StringBuilder sb = new StringBuilder();
            tickets.forEach(t -> sb.append("R").append(t.getSeat().getRowNumber())
                    .append(" S").append(t.getSeat().getSeatNumber()).append(", "));
            if (sb.length() > 2) sb.setLength(sb.length() - 2);
            doc.add(new Paragraph("Sæder: " + sb, normalFont));

            doc.add(new Paragraph("Totalpris: " + reservation.getTotalPrice()
                    + (reservation.getPaid() ? " (Betalt)" : " (Ikke betalt)"), normalFont));


            String qrText = "http://localhost:8080/reservations/" + reservation.getReservationId() + ".pdf";

            Image qrImg = createQrImage(qrText, 150, 150);
            doc.add(new Paragraph("\n"));
            doc.add(qrImg);

            doc.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error while creating PDF", e);
        }
    }

    private Image createQrImage(String content, int width, int height) throws Exception {
        var hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.MARGIN, 1);

        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix matrix = qrWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);

        return Image.getInstance(baos.toByteArray()); // iText Image
    }


}
