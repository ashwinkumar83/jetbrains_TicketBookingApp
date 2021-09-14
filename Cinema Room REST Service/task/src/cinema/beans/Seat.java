package cinema.beans;

public class Seat{
    private int row;
    private int column;
    private int price;

    public Seat(){}
    public Seat(final int row,final int column, final int price){
        this.row = row;
        this.column = column;
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat that = (Seat) o;
        return this.getRow() == that.getRow() &&
                this.getColumn()== that.getColumn();
    }

    @Override
    public int hashCode() {
        return row * 24 + column * 24;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
