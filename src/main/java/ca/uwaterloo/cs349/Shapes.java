package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;

public interface Shapes {
    public void draw_shape(New_Board board, Point2D point);
    public void setPlaced(boolean placed);
    public boolean getPlaced();
    public String getShape_name();
}
