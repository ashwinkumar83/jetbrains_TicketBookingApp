package cinema.validator;

import cinema.beans.Seat;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SeatDataValidator {
    public boolean isDataValid(final Seat seat){
        if(Objects.nonNull(seat.getColumn()) && (seat.getColumn()>0 && seat.getColumn()<=9)
         && Objects.nonNull(seat.getRow()) && (seat.getRow()>0 && seat.getRow()<=9)){
            return true;
        }
        return false;
    }
}
