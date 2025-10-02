package com.example.kinoxp.RestController;

import com.example.kinoxp.model.booking.Reservation;
import com.example.kinoxp.repository.Booking.ReservationRepo;
import com.example.kinoxp.service.booking.TicketPdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationTicketRestController {


    private final TicketPdfService pdfService;
    private final ReservationRepo reservationRepo;


    public ReservationTicketRestController(TicketPdfService pdfService, ReservationRepo reservationRepo) {
        this.pdfService = pdfService;
        this.reservationRepo = reservationRepo;
    }

    @GetMapping(value = "/{id}/ticket.pdf", produces = "application/pdf")
    public ResponseEntity<byte[]> generateTicketPdf(@PathVariable int id) {
        byte[] pdf = pdfService.generateReservationPdf(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=ticket_" + id + ".pdf")
                .body(pdf);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Reservation> getReservationAsJson(@PathVariable int id) {
        return reservationRepo.findByReservationId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
