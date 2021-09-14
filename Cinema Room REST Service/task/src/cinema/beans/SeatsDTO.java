package cinema.beans;

import java.util.List;
import java.util.Set;

public class SeatsDTO {
   public int getTotal_rows() {
      return total_rows;
   }

   public void setTotal_rows(int total_rows) {
      this.total_rows = total_rows;
   }

   public int getTotal_columns() {
      return total_columns;
   }

   public void setTotal_columns(int total_columns) {
      this.total_columns = total_columns;
   }

   public Set<Seat> getAvailable_seats() {
      return available_seats;
   }

   public void setAvailable_seats(Set<Seat> available_seats) {
      this.available_seats = available_seats;
   }

   private int total_rows;
   private int total_columns;
   private Set<Seat> available_seats;
}
