package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class Controller {


    @FXML
    private TableView coursesTable;

    @FXML
    public void onGetCoursesClick(ActionEvent event) {
        System.out.println("YAAAAY");
    }
}
