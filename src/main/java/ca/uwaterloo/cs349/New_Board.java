package ca.uwaterloo.cs349;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class New_Board {

        int cols = 75;
        int rows = 50;
        Cell board[][];

        //initializes the board
        public New_Board() {
            board = new Cell[cols][rows];
            for(int i = 0; i < cols; i++) {
                for(int j = 0; j < rows; j++) {
                    Cell cell = new Cell(i*14, (j*14)+50);
                    board[i][j] = cell;
                }
            }
        }

        public int sumNeighbours(int x, int y) {
            int sum = 0;
            for(int i = -1 ; i < 2; i++ ) {
                for(int j = - 1; j < 2; j++) {
                    if(x+i >=0 && x+i < cols && y+j >= 0 && y+j < rows) {
                        if(this.board[x+i][y+j].getState()) {
                            sum++;
                        }
                    }
                }
            }
            return sum;
        }
        public void cloneBoard(New_Board n_board) {
            for(int i = 0; i < cols; i++) {
                for(int j = 0; j < rows; j++) {
                    this.board[i][j].setState((n_board.board)[i][j].getState());
                }
            }
        }

        public void draw_board(Group root) {
            for(int i = 0; i < cols; i++) {
                for(int j = 0; j < rows; j++) {
                    Rectangle rectangle = new Rectangle(12, 12);
                    rectangle.setX(this.board[i][j].getCoordinates().getX());
                    rectangle.setY(this.board[i][j].getCoordinates().getY());
                    if(this.board[i][j].getState()) {
                        rectangle.setFill(Color.BLACK);
                    } else {
                        rectangle.setFill(Color.WHITE);
                    }
                    root.getChildren().add(rectangle);
                }
            }
        }

        public void clear_board() {
            for(int i = 0; i < cols; i++) {
                for(int j = 0; j < rows; j++) {
                    this.board[i][j].setState(false);
                }
            }
        }
}
