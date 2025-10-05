package com.example.kinoxp.RestController;

import com.example.kinoxp.service.booking.TicketPdfServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationTicketRestController {


    private final TicketPdfServiceImpl pdfService;


    public ReservationTicketRestController(TicketPdfServiceImpl pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping(value = "/{id}/ticket.pdf", produces = "application/pdf")
    public ResponseEntity<byte[]> generateTicketPdf(@PathVariable int id) {
        byte[] pdf = pdfService.generateReservationPdf(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=ticket_" + id + ".pdf")
                .body(pdf);
    }
}
