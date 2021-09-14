package cinema.services.impl;

import cinema.beans.*;
import cinema.services.SeatService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class SeatServiceImpl implements SeatService {
    private final int TOTAL_CAPACITY_ROWS = 9;
    private final int TOTAL_CAPACITY_COLOMNS = 9;
    private final int DEFAULT_SEAT_PRICE = 10;

    private Set<Seat> AVAILABLE_SEATS = new HashSet<Seat>();
    private Map<UUID,BookedTicket> BOOKED_SEATS = new HashMap<>();

    public SeatServiceImpl(){
        populateSeats();
    }

    public Optional<CinemaStats> getStats(){
        int totalIncome = BOOKED_SEATS.values().stream().mapToInt(b -> b.getTicket().getPrice()).sum();
        int totalAvailableSeats = AVAILABLE_SEATS.size();
        int totalBookedSeats = BOOKED_SEATS.size();

        CinemaStats stat = new CinemaStats();
        stat.setCurrent_income(totalIncome);
        stat.setNumber_of_available_seats(totalAvailableSeats);
        stat.setNumber_of_purchased_tickets(totalBookedSeats);

        return Optional.of(stat);
    }

    @Override
    public Optional<TicketDetails> getTicketByToken(final UUID ticketToken){
        BookedTicket bookedTicket  = BOOKED_SEATS.get(ticketToken);
        if(bookedTicket==null) {
            return Optional.empty();
        }
        TicketDetails ticketDetails = new TicketDetails();
        ticketDetails.setReturned_ticket(bookedTicket.getTicket());
        BOOKED_SEATS.remove(ticketToken);
        AVAILABLE_SEATS.add(bookedTicket.getTicket());
        return Optional.of(ticketDetails);
    }

    @Override
    public Optional<BookedTicket> bookTicket(@RequestBody Seat seat){
        Optional<Seat> optSeat = Optional.of(AVAILABLE_SEATS.stream().filter(as -> as.getRow()==seat.getRow() && as.getColumn()==seat.getColumn()).findAny())
                        .orElse(null);

        if(optSeat.isPresent()) {
            Seat purchasedSeat = optSeat.get();
            UUID generatedTicketToken = UUID.randomUUID();
            BookedTicket bookedTicket = new BookedTicket(generatedTicketToken,purchasedSeat);
            BOOKED_SEATS.put(generatedTicketToken,bookedTicket);
            AVAILABLE_SEATS.remove(purchasedSeat);
            return Optional.of(bookedTicket);
        }
        return Optional.empty();
    }

    @Override
    public boolean isSeatAvailable(final Seat seat){
        return AVAILABLE_SEATS.contains(seat);
    }

    private SeatsDTO getSeatsDTO() {
        SeatsDTO seatsDTO = new SeatsDTO();
        seatsDTO.setTotal_columns(TOTAL_CAPACITY_COLOMNS);
        seatsDTO.setTotal_rows(TOTAL_CAPACITY_ROWS);
        seatsDTO.setAvailable_seats(AVAILABLE_SEATS);
        return seatsDTO;
    }

    @Override
    public SeatsDTO getAvailableSeats(){
        SeatsDTO seatsDTO = getSeatsDTO();
        seatsDTO.setAvailable_seats(AVAILABLE_SEATS);
        return seatsDTO;
    }

    private void populateSeats() {
        int r = TOTAL_CAPACITY_ROWS;
        while (r != 0) {
            int c = TOTAL_CAPACITY_COLOMNS;
            while (c != 0) {
                int price = r > 4 ? 8 : DEFAULT_SEAT_PRICE;
                AVAILABLE_SEATS.add(new Seat(r, c, price));
                c--;
            }
            r--;
        }
    }
}
