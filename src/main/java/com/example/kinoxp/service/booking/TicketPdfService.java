package com.example.kinoxp.service.booking;

import com.example.kinoxp.DTO.booking.CreateReservationRequest;
import com.itextpdf.text.Image;

import java.io.IOException;

public interface TicketPdfService {

    byte[] generateReservationPdf(Integer reservationId);
}
