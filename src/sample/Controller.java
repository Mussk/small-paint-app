package sample;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;


public class Controller {

    @FXML
    private ToggleButton Pencile, Eraser, kwadratB, liniaB, KoloB;

    @FXML
    private MenuItem SaveB;

    @FXML
    private Button ButtonText;

    @FXML
    private Slider PensileSlide, EraseSlide, ShapeSlide;

    @FXML
    private VBox Toolgrid = new VBox();

    @FXML
    private Canvas Area = new Canvas();

    @FXML
    private ColorPicker CPicker;

    @FXML
    private TextField TextField, x, y;

    private GraphicsContext gc;


    @FXML
    public void initialize() {

        gc = Area.getGraphicsContext2D();

        rysuj();

        erase();

        text();

        figury();

        save();
    }


    private void rysuj() {

        Pencile.setOnAction(event -> {

            gc.setLineWidth(PensileSlide.getValue());

            if (Pencile.isSelected()) {

                Eraser.setSelected(false);

                kwadratB.setSelected(false);

                liniaB.setSelected(false);

                KoloB.setSelected(false);

                resetHandlers();

                Area.setOnMousePressed(ev -> {

                    PensileSlide.setOnMouseDragged(eventSlide -> {

                        gc.setLineWidth(PensileSlide.getValue());

                    });

                    gc.setStroke(CPicker.getValue());

                    gc.beginPath();

                    gc.lineTo(ev.getX(), ev.getY());


                });

                Area.setOnMouseDragged(e -> {

                    gc.lineTo(e.getX(), e.getY());

                    gc.stroke();


                });

                Area.setOnMouseReleased(e -> {

                    gc.lineTo(e.getX(), e.getY());
                    gc.stroke();
                    gc.closePath();


                });


            }

        });


    }

    private void erase() {

        Eraser.setOnAction(event -> {

            gc.setLineWidth(EraseSlide.getValue());

            if (Eraser.isSelected()) {

                Pencile.setSelected(false);

                kwadratB.setSelected(false);

                resetHandlers();

                Area.setOnMousePressed(event1 -> {

                    EraseSlide.setOnMouseDragged(events -> {

                        gc.setLineWidth(EraseSlide.getValue());

                    });


                    double lineWidth = gc.getLineWidth();
                    gc.clearRect(event1.getX() - lineWidth / 2, event1.getY() - lineWidth / 2, lineWidth, lineWidth);

                });

                Area.setOnMouseDragged(e -> {

                    double lineWidth = gc.getLineWidth();
                    gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);
                });

                Area.setOnMouseReleased(e -> {

                    double lineWidth = gc.getLineWidth();
                    gc.clearRect(e.getX() - lineWidth / 2, e.getY() - lineWidth / 2, lineWidth, lineWidth);

                    gc.closePath();

                });

            }

        });
    }


    private void text() {

        ButtonText.setOnAction(event -> {

            gc.setLineWidth(1);

            Pencile.setSelected(false);

            Eraser.setSelected(false);

            resetHandlers();

            gc.setStroke(CPicker.getValue());

            try {

                gc.strokeText(TextField.getText(), Integer.parseInt(x.getText()), Integer.parseInt(y.getText()));

            }catch (Exception ex){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong number!");
                alert.setContentText("Please, write a number into felds X and Y");

                alert.showAndWait();
            }
        });

    }

    private void figury() {

        Rectangle kwadrat = new Rectangle();

        Line linia = new Line();

        Circle kolo = new Circle();


        kwadratB.setOnAction(event -> {

            if (kwadratB.isSelected()) {

                Pencile.setSelected(false);

                Eraser.setSelected(false);

                liniaB.setSelected(false);

                KoloB.setSelected(false);

                resetHandlers();

                gc.setLineWidth(ShapeSlide.getValue());

                Area.setOnMousePressed(ev -> {

                    ShapeSlide.setOnMouseDragged(event32 -> {

                        gc.setLineWidth(ShapeSlide.getValue());

                    });


                    gc.closePath();

                    gc.setStroke(CPicker.getValue());

                    kwadrat.setX(ev.getX());
                    kwadrat.setY(ev.getY());


                });

                Area.setOnMouseReleased(event1 -> {

                    kwadrat.setWidth(event1.getX() - kwadrat.getX());

                    kwadrat.setHeight(kwadrat.getY() - event1.getY());

                    gc.strokeRect(kwadrat.getX(), kwadrat.getY(), kwadrat.getWidth(), kwadrat.getHeight());


                });
            }

        });

        liniaB.setOnAction(event -> {

            if (liniaB.isSelected()) {

                Pencile.setSelected(false);

                Eraser.setSelected(false);

                kwadratB.setSelected(false);

                KoloB.setSelected(false);

                resetHandlers();

                gc.setLineWidth(ShapeSlide.getValue());


                Area.setOnMousePressed(event12 -> {

                    ShapeSlide.setOnMouseDragged(event32 -> {

                        gc.setLineWidth(ShapeSlide.getValue());

                    });

                    gc.setStroke(CPicker.getValue());

                    linia.setStartX(event12.getX());

                    linia.setStartY(event12.getY());


                });

                Area.setOnMouseReleased(event22 -> {

                    linia.setEndX(event22.getX());

                    linia.setEndY(event22.getY());

                    gc.strokeLine(linia.getStartX(), linia.getStartY(), linia.getEndX(), linia.getEndY());

                });
            }

        });


        KoloB.setOnAction(event -> {

            if (KoloB.isSelected()) {

                Pencile.setSelected(false);

                Eraser.setSelected(false);

                kwadratB.setSelected(false);

                liniaB.setSelected(false);

                resetHandlers();

                gc.setLineWidth(ShapeSlide.getValue());


                Area.setOnMousePressed(event12 -> {

                    ShapeSlide.setOnMouseDragged(event32 -> {

                        gc.setLineWidth(ShapeSlide.getValue());

                    });

                    gc.setStroke(CPicker.getValue());

                    kolo.setCenterX(event12.getX());

                    kolo.setCenterY(event12.getY());


                });

                Area.setOnMouseReleased(event22 -> {

                    kolo.setRadius(Math.abs(event22.getX() - kolo.getCenterX()));

                    gc.strokeOval(kolo.getCenterX(), kolo.getCenterY(), kolo.getRadius(), kolo.getRadius());

                });
            }

        });


    }

    public void resetHandlers() {

        Area.setOnMousePressed(null);

        Area.setOnMouseDragged(null);

        Area.setOnMouseReleased(null);

    }

    public void save() {

        SaveB.setOnAction((event) -> {

            FileChooser fc = new FileChooser();
            fc.setTitle("Save File");

            File file = fc.showSaveDialog(Main.getPrimaryStage());
            if (file != null) {
                try {
                    WritableImage wI = new WritableImage((int)Area.getWidth(),(int)Area.getHeight());
                    Area.snapshot(null, wI);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(wI, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    Alert alertsave = new Alert(Alert.AlertType.INFORMATION);
                    alertsave.setTitle("Error");
                    alertsave.setHeaderText("File cant be saved");
                    alertsave.setContentText("Please, try one more time");

                    alertsave.showAndWait();
                }
            }

        });
    }
}




