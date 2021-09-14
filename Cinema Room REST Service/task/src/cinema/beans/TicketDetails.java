package cinema.beans;

public class TicketDetails {
    public Seat getReturned_ticket() {
        return returned_ticket;
    }

    public void setReturned_ticket(Seat returned_ticket) {
        this.returned_ticket = returned_ticket;
    }

    private Seat returned_ticket;
}
