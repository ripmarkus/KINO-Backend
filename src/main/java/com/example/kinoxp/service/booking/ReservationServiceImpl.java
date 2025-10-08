package com.example.kinoxp.service.booking;

import com.example.kinoxp.DTO.booking.CreateReservationRequest;
import com.example.kinoxp.model.booking.Reservation;
import com.example.kinoxp.model.booking.ReservationSeat;
import com.example.kinoxp.model.customer.Customer;
import com.example.kinoxp.model.employee.Employee;
import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.model.theatre.Seat;
import com.example.kinoxp.repository.Booking.ReservationRepo;
import com.example.kinoxp.repository.Theatre.SeatRepo;
import com.example.kinoxp.service.customer.CustomerService;
import com.example.kinoxp.service.employee.EmployeeService;
import com.example.kinoxp.service.theatre.ScreeningService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ReservationServiceImpl implements ReservationService {
    
    private final ReservationRepo reservationRepo;
    private final ScreeningService screeningService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final SeatRepo seatRepo;
    private final TicketService ticketService;

    public ReservationServiceImpl(ReservationRepo reservationRepo, ScreeningService screeningService,
                                  CustomerService customerService, EmployeeService employeeService,
                                  SeatRepo seatRepo, TicketService ticketService) {
        this.reservationRepo = reservationRepo;
        this.screeningService = screeningService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.seatRepo = seatRepo;
        this.ticketService = ticketService;
    }

    public Reservation getRequiredReservation(Integer id) {
        return reservationRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    @Override
    public boolean existsById(Integer id) {
        return reservationRepo.existsById(id);
    }
    
    @Override
    public Optional<Reservation> findById(Integer id) {
        return reservationRepo.findById(id);
    }
    
    @Override
    public List<Reservation> findAll() {
        return reservationRepo.findAll();
    }
    
    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepo.save(reservation);
    }
    
    @Override
    public void deleteById(Integer id) {
        reservationRepo.deleteById(id);
    }

    @Transactional
    public Reservation createReservationWithSeats(CreateReservationRequest request) {
        Customer customer = customerService.getRequiredCustomer(request.getCustomerId());
        Screening screening = screeningService.getRequiredScreening(request.getScreeningId());
        Employee salesClerk = employeeService.getRequiredEmployee(request.getSalesClerkId());
        
        Reservation reservation = new Reservation();
        reservation.setReservationDate(request.getReservationDate());
        reservation.setPaid(request.getPaid());
        reservation.setTotalPrice(request.getTotalPrice());
        reservation.setCustomer(customer);
        reservation.setScreening(screening);
        reservation.setSalesClerk(salesClerk);
        reservation.setReservationSeats(new HashSet<>());

        reservation = reservationRepo.save(reservation);

        Set<Integer> seatIds = request.getSeatIds();
        
        if (request.getSeatIds() != null && !request.getSeatIds().isEmpty()) {
            Set<ReservationSeat> reservationSeats = new HashSet<>();
            
            for (Integer seatId : request.getSeatIds()) {
                Seat seat = seatRepo.findById(seatId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat not found: " + seatId));
                
                ReservationSeat reservationSeat = new ReservationSeat();
                reservationSeat.setReservation(reservation);
                reservationSeat.setScreening(screening);
                reservationSeat.setSeat(seat);
                
                reservationSeats.add(reservationSeat);

            }
            
            reservation.setReservationSeats(reservationSeats);
            reservation = reservationRepo.save(reservation);
            ticketService.createTicketsForReservation(reservation, seatIds);
        }

        return reservation;
    }
}
