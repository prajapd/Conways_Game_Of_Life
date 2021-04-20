package ca.uwaterloo.cs349;

public class Cell {
    private boolean state;
    private Coordinates coordinates;

    public Cell(double x, double y) {
        state = false;
        coordinates = new Coordinates(x, y);
    }
    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
