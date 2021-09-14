package cinema.controllers;

import cinema.beans.*;
import cinema.services.SeatService;
import cinema.validator.SeatDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
public class SeatController {

    @Resource
    public SeatService seatService;

    @Resource
    SeatDataValidator seatDataValidator;

    private final String PASSWORD="super_secret";

    @PostMapping("/stats")
    public ResponseEntity getStats(@RequestParam(name="password",defaultValue = "") String password) {
        if (Objects.isNull(password) || !PASSWORD.equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "The password is wrong!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(seatService.getStats().get());
    }

    @GetMapping("/seats")
    public SeatsDTO getSeats(){
        return seatService.getAvailableSeats();
    }

    @PostMapping("/return")
    public ResponseEntity returnTicket(@RequestBody Map<String, UUID> ticketTokenData){
        UUID ticketToken = ticketTokenData.get("token");
        Optional<TicketDetails> returnTicketOpt = seatService.getTicketByToken(ticketToken);
        if(returnTicketOpt.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(returnTicketOpt.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","Wrong token!"));
    }

    @PostMapping("/purchase")
    public ResponseEntity purchaseSeat(@RequestBody Seat requestedSeat){
        if(!seatDataValidator.isDataValid(requestedSeat)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","The number of a row or a column is out of bounds!"));
        }

        Optional<BookedTicket> bookedTicket  = seatService.bookTicket(requestedSeat);
        if(bookedTicket.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(bookedTicket.get());
        }
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","The ticket has been already purchased!"));
    }
}
