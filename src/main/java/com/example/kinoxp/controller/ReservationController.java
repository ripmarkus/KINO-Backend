package com.example.kinoxp.controller;

import com.example.kinoxp.DTO.booking.ReservationRequest;
import com.example.kinoxp.DTO.theatre.SeatMapResponse;
import com.example.kinoxp.model.booking.Reservation;
import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.theatre.Seat;
import com.example.kinoxp.service.booking.ReservationService;
import com.example.kinoxp.service.booking.TicketService;
import com.example.kinoxp.service.theatre.SeatService;
import com.example.kinoxp.service.theatre.ScreeningService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;
    private final TicketService ticketService;
    private final SeatService seatService;
    private final ScreeningService screeningService;

    public ReservationController(ReservationService reservationService, TicketService ticketService,
                                 SeatService seatService, ScreeningService screeningService) {
        this.reservationService = reservationService;
        this.ticketService = ticketService;
        this.seatService = seatService;
        this.screeningService = screeningService;
    }

    //TODO skal bruge screeningID istedet
    @GetMapping("/{orderId}/seats")
    public SeatMapResponse getSeatReservations(@PathVariable Integer orderId) {
        Reservation reservation = reservationService.getRequiredReservation(orderId);
        Screening screening = reservation.getScreening();

        return new SeatMapResponse(
                screening.getAvailableSeats(),
                screening.getReservedSeats()
        );
    }

    @PostMapping("/reserve")
    public Reservation createReservation(@RequestBody ReservationRequest request) {
        Screening screening = screeningService.checkIfScreeningExists(request.screeningId);
        Set<Seat> seats = request.seatIds.stream().map(seatService::getRequiredSeat).collect(Collectors.toSet());
        
        screening.reserveSeats(seats);
        screeningService.save(screening);
        
        Reservation reservation = reservationService.createReservation(request.screeningId, request.customerId);
        ticketService.createTicketsForReservation(reservation, request.seatIds);
        
        return reservation;
    }

    @GetMapping("/reservations/{id}")
    public Reservation getReservation(@PathVariable Integer id) {
        return reservationService.getRequiredReservation(id);
    }

    @PutMapping("/reservations/{id}")
    public Reservation updateReservation(@PathVariable Integer id, @RequestBody Reservation reservation) {
        reservation.setReservationId(id);
        return reservationService.save(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Integer id) {
        reservationService.deleteById(id);
    }
}