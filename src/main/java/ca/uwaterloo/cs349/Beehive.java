package ca.uwaterloo.cs349;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Beehive implements Shapes{
    private boolean placed;
    private String shape_name;

    public Beehive() {
        placed = false;
        shape_name = "beehive";
    }

    public String getShape_name() {
        return shape_name;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public boolean getPlaced() {
        return this.placed;
    }

    public void draw_shape(New_Board board, Point2D point) {
        for(int i = 0; i < board.cols; i++) {
            for(int j = 0; j < board.rows; j++) {
                Rectangle rect = new Rectangle(12, 12);
                rect.setX((board.board)[i][j].getCoordinates().getX());
                rect.setY((board.board)[i][j].getCoordinates().getY());
                if(rect.contains(point)) {
                 if(i+1 < board.cols) {
                     (board.board)[i+1][j].setState(true);
                 }
                 if(i+2 < board.cols) {
                     (board.board)[i+2][j].setState(true);
                 }
                 if(j+1 < board.rows) {
                     (board.board)[i][j+1].setState(true);
                     if(i+3 < board.cols) {
                         (board.board)[i+3][j+1].setState(true);
                     }
                 }
                 if(j+2 < board.rows) {
                     if(i+1 < board.cols) {
                         (board.board)[i+1][j+2].setState(true);
                     }
                     if(i+2 < board.cols) {
                         (board.board)[i+2][j+2].setState(true);
                     }
                 }
                }
            }
        }
    }

}
