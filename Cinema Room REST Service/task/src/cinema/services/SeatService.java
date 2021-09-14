package cinema.services;

import cinema.beans.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

public interface SeatService {
    SeatsDTO getAvailableSeats();
    boolean isSeatAvailable(final Seat seat);
    Optional<BookedTicket> bookTicket(final Seat seat);
    Optional<TicketDetails> getTicketByToken(final UUID ticketToken);
    Optional<CinemaStats> getStats();
}
