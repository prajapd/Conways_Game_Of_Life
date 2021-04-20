package ca.uwaterloo.cs349;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Point2D;

public class ConwaysGameOfLife extends Application {

    final private int height = 770;
    final private int width = 1050;
    final private int toolbar_height = 50;
    int frame_count = 0;
    Shapes curr_shape; //will be passed around this section
    boolean paused = false;

    public void draw_toolbar(Group root, New_Board board, Text footer) {
        Rectangle toolbar = new Rectangle(width, toolbar_height);
        toolbar.setX(0);
        toolbar.setY(0);
        toolbar.setFill(Color.LIGHTGRAY);
        HBox hbox = new HBox();

        Button block = new Button("Block");
        Button beehive = new Button("Beehive");
        Button blinker = new Button("Blinker");
        Button toad = new Button("Toad");
        Button glider = new Button("Glider");
        Button clear = new Button("Clear");

        block.setPrefSize(75, 30);
        Image block_image = new Image("block.png");
        ImageView block_image_view = new ImageView(block_image);
        block_image_view.setFitHeight(20);
        block_image_view.setPreserveRatio(true);
        block.setGraphic(block_image_view);

        beehive.setPrefSize(90, 30);
        Image beehive_image = new Image("beehive.png");
        ImageView beehive_image_view = new ImageView(beehive_image);
        beehive_image_view.setFitHeight(20);
        beehive_image_view.setPreserveRatio(true);
        beehive.setGraphic(beehive_image_view);

        blinker.setPrefSize(80, 30);
        Image blinker_image = new Image("blinker.png");
        ImageView blinker_image_view = new ImageView(blinker_image);
        blinker_image_view.setFitHeight(20);
        blinker_image_view.setPreserveRatio(true);
        blinker.setGraphic(blinker_image_view);

        toad.setPrefSize(80, 30);
        Image toad_image = new Image("toad.png");
        ImageView toad_image_view = new ImageView(toad_image);
        toad_image_view.setFitHeight(15);
        toad_image_view.setPreserveRatio(true);
        toad.setGraphic(toad_image_view);

        glider.setPrefSize(80, 30);
        Image glider_image = new Image("glider.png");
        ImageView glider_image_view = new ImageView(glider_image);
        glider_image_view.setFitHeight(20);
        glider_image_view.setPreserveRatio(true);
        glider.setGraphic(glider_image_view);

        clear.setPrefSize(80, 30);
        Image clear_image = new Image("clear.png");
        ImageView clear_image_view = new ImageView(clear_image);
        clear_image_view.setFitHeight(15);
        clear_image_view.setPreserveRatio(true);
        clear.setGraphic(clear_image_view);

        HBox.setMargin(block, new Insets(12, 2, 12, 10));
        HBox.setMargin(beehive, new Insets(12, 15, 12, 3));
        HBox.setMargin(blinker, new Insets(12, 2, 12, 10));
        HBox.setMargin(toad, new Insets(12, 2, 12, 3));
        HBox.setMargin(glider, new Insets(12, 15, 12, 3));
        HBox.setMargin(clear, new Insets(12, 10, 12, 10));

//        block.setStyle("-fx-border-width: 0;");
        block.setOnAction(actionEvent ->  {
            curr_shape = new Block();
            curr_shape.setPlaced(false);
        });
        beehive.setOnAction(actionEvent ->  {
            curr_shape = new Beehive();
            curr_shape.setPlaced(false);
        });
        blinker.setOnAction(actionEvent ->  {
            curr_shape = new Blinker();
            curr_shape.setPlaced(false);
        });
        toad.setOnAction(actionEvent ->  {
            curr_shape = new Toad();
            curr_shape.setPlaced(false);
        });
        glider.setOnAction(actionEvent ->  {
            curr_shape = new Glider();
            curr_shape.setPlaced(false);
        });

        clear.setOnAction(actionEvent ->  {
            board.clear_board();
            curr_shape.setPlaced(false);
            footer.setText("Cleared");
        });


        hbox.getChildren().add(block);
        hbox.getChildren().add(beehive);
        hbox.getChildren().add(blinker);
        hbox.getChildren().add(toad);
        hbox.getChildren().add(glider);
        hbox.getChildren().add(clear);
        root.getChildren().add(hbox);
    }

    public void draw_grid(Group root, int cols, int rows) {
        for(int i = 0; i < cols; i++) {
            Line col = new Line((i*14)-1, toolbar_height, (i*14)-1, 750);
            root.getChildren().add(col);
        }
        for(int j = 0; j <= rows; j++) {
            Line row = new Line(0, (j*14)+50, width, (j*14)+50);
            root.getChildren().add(row);
        }
    }

    public void create_next_board(New_Board board, New_Board n_board) {
        for(int p = 0; p < board.cols; p++) {
            for(int q = 0; q < board.rows; q++) {
                if((board.board)[p][q].getState()) { //alive
                    int alive = board.sumNeighbours(p, q)-1;
                    if(alive == 2 || alive == 3) {
                        (n_board.board)[p][q].setState(true);
                    } else {
                        (n_board.board)[p][q].setState(false);
                    }
                } else {
                    int alive = board.sumNeighbours(p, q);
                    if(alive == 3) {
                        (n_board.board)[p][q].setState(true);
                    } else {
                        (n_board.board)[p][q].setState(false);
                    }
                }
            }
        }
    }

    @Override
    public void start(Stage stage) {
        Group top = new Group();
        Group body = new Group();

        HBox bottom = new HBox();
        HBox bottom_right = new HBox();
        HBox bottom_left = new HBox();

        New_Board c_board = new New_Board();
        New_Board n_board = new New_Board();

        Text footer_right = new Text("Frame " + String.valueOf(frame_count));
        bottom_right.setAlignment(Pos.BOTTOM_RIGHT);
        bottom_right.getChildren().add(footer_right);
        HBox.setMargin(footer_right, new Insets(0, 10, 3, 10));

        Text footer_left = new Text("Cleared");
        bottom_left.setAlignment(Pos.BOTTOM_LEFT);
        bottom_left.getChildren().add(footer_left);
        HBox.setMargin(footer_left, new Insets(0, 10, 3, 5));

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        BorderPane borderPane = new BorderPane();

        draw_toolbar(top, c_board, footer_left);
        draw_grid(body, c_board.cols, c_board.rows);


        bottom.getChildren().add(bottom_left);
        bottom.getChildren().add(region1);
        bottom.getChildren().add(bottom_right);

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if(now - lastUpdate >= 1_000_000_000) {
                    draw_board(body, c_board, n_board);
                    footer_right.setText("Frame " + String.valueOf(frame_count));
                    lastUpdate = now;
                }
            }
        };
        timer.start();

        body.setOnMouseClicked(mouseEvent -> {
            if(curr_shape != null && !curr_shape.getPlaced()) {
                Point2D point = new Point2D(mouseEvent.getX(), mouseEvent.getY());
                curr_shape.draw_shape(c_board, point);
                curr_shape.setPlaced(true);
                footer_left.setText("Created " + curr_shape.getShape_name() + " at: " +
                        (int)Math.floor(mouseEvent.getX()/14) + ", " + (int)Math.floor((mouseEvent.getY()-50)/14));
            }
            if(paused) {
                c_board.draw_board(body);
            }
        });

        borderPane.setTop(top);
        borderPane.setCenter(body);
        borderPane.setBottom(bottom);
        Scene scene = new Scene(borderPane, width, height);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.M) {
                if(!paused) {
                    timer.stop();
                    paused = true;
                } else {
                    timer.start();
                    paused = false;
                }
            }
        });

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event->{
            if (event.getCode() == KeyCode.SPACE && paused) {
                create_next_board(c_board, n_board); //we come here after all the other ones have been added
                c_board.cloneBoard(n_board);
                c_board.draw_board(body);
                frame_count++;
                footer_right.setText("Frame " + String.valueOf(frame_count));
            }
        });

        stage.setResizable(false);
        stage.setTitle("Conway's Game of Life d4prajap");
        stage.setScene(scene);
        stage.show();
    }

    // this will be called every time to redraw the board
    public void draw_board(Group root, New_Board board, New_Board n_board) {
        board.draw_board(root);
        create_next_board(board, n_board);
        //copy over new board
        board.cloneBoard(n_board);
        //draw board
        frame_count++;
    }
}
