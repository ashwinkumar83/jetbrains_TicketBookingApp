package cinema.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class BookedTicket {
    private UUID token;
    private Seat ticket;
    public BookedTicket(final UUID token,final Seat ticket){
        this.token = token;
        this.ticket = ticket;
    }
    public Seat getTicket() {
        return ticket;
    }

    public void setTicket(Seat ticket) {
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
