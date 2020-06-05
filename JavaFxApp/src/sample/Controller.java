package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import sample.helpers.Helpers;
import sample.model.Course;
import sample.model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    public TableColumn<Course, String> lecuturerName;

    @FXML
    private TableView<Course> coursesTable;

    @FXML
    private TableColumn<Course, String> nameColumn;


    @FXML
    private TextField txtCourseId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLecturer;
    @FXML
    private TableView<User> tbStudents;
    @FXML
    private TableColumn<User, String> studentNameColumn;


    private void clearScreen(){

        txtCourseId.setText("");
        txtName.setText("");
        txtLecturer.setText("");
        tbStudents.getItems().clear();

    }

    @FXML
    public void onGetCoursesClick() {

    try{
        //kreiran novi rest template prema media tipu koji je poslan - svi tipovi
        RestTemplate restTemplate = Helpers.createRestTemplate(MediaType.ALL);


        String fooResourceUrl
                = "http://localhost:8081/api/course/all";
        ResponseEntity<Course[]> response = restTemplate.getForEntity(fooResourceUrl, Course[].class);
        System.out.println("Status code: " + response.getStatusCode());
        Course[] courses = response.getBody();

        List<Course> courseList = Arrays.asList(courses);
        ObservableList<Course> observableList = FXCollections.observableArrayList(courseList);

        coursesTable.getItems().setAll(observableList);

        coursesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            this.getCourse();
        });
    }catch (Exception e){

        Helpers.postMessage("Error getting courses", Alert.AlertType.ERROR);
    }
    }


    @FXML
    private void getCourse() {
        try{
            Course selectedCourse = coursesTable.getSelectionModel().getSelectedItem();

            if(selectedCourse == null){
                return;
            }
            RestTemplate restTemplate = Helpers.createRestTemplate(MediaType.ALL);
            String courseUrl = "http://localhost:8081/api/course/" + selectedCourse.getCourseId();

            ResponseEntity<Course> response = restTemplate.getForEntity(courseUrl, Course.class);

            Course course = response.getBody();


            txtCourseId.setText(Long.toString(course.getCourseId()));
            txtName.setText(course.getName());
            txtLecturer.setText(course.getLecturer().getUserName());

            ObservableList<User> userList = FXCollections.observableArrayList(course.getStudents());

            tbStudents.getItems().setAll(userList);


        }catch (Exception e){
            Helpers.postMessage("Error getting course", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onCourseAdd(ActionEvent event) {
        try{

            Course course = new Course();
            course.setName(txtName.getText());
            User lecturer = new User();
            lecturer.setUserName(txtLecturer.getText());
            course.setLecturer(lecturer);

            RestTemplate restTemplate = Helpers.createRestTemplate(MediaType.APPLICATION_JSON);
            String courseUrl = "http://localhost:8081/api/course";

            restTemplate.postForEntity(courseUrl, course, Course.class);



            this.onGetCoursesClick();

        }catch (Exception e){
            Helpers.postMessage("Error adding course", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onCourseUpdate(ActionEvent event) {
        try{
            Course selectedCourse = new Course();
            selectedCourse.setCourseId(Long.parseLong(txtCourseId.getText()));
            selectedCourse.setName(txtName.getText());
            User lecturer = new User();
            lecturer.setUserName(txtLecturer.getText());
            selectedCourse.setLecturer(lecturer);
            selectedCourse.setStudents(tbStudents.getItems());

            RestTemplate restTemplate = Helpers.createRestTemplate(MediaType.APPLICATION_JSON);
            String courseUrl = "http://localhost:8081/api/course/" + selectedCourse.getCourseId();

            HttpEntity<Course> requestUpdate = new HttpEntity<>(selectedCourse);
            restTemplate.exchange(courseUrl, HttpMethod.PUT, requestUpdate, Void.class);


            this.onGetCoursesClick();
            clearScreen();


        }catch (Exception e){
            Helpers.postMessage("Error updating course", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onCourseDelete(ActionEvent event) {
        try{

            long courseId = Long.parseLong(txtCourseId.getText());
            RestTemplate restTemplate = Helpers.createRestTemplate(MediaType.ALL);
            String courseUrl = "http://localhost:8081/api/course/" + courseId;

            restTemplate.delete(courseUrl);

            this.onGetCoursesClick();

            clearScreen();

        }catch (Exception e){
            Helpers.postMessage("Error deleting course", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
        studentNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getUserName()));
        lecuturerName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getLecturer().getUserName()));

    }
}
