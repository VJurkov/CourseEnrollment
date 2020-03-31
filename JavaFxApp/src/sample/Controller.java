package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import sample.model.Course;

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
    public void onGetCoursesClick(ActionEvent event) {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.ALL);
        converter.setSupportedMediaTypes(mediaTypes);
        messageConverters.add(converter);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(messageConverters);

        String fooResourceUrl
                = "http://localhost:8081/api/course/all";
        ResponseEntity<Course[]> response = restTemplate.getForEntity(fooResourceUrl, Course[].class);
        System.out.println("Status code: " + response.getStatusCode());
        Course[] courses = response.getBody();

        List<Course> courseList = Arrays.asList(courses);
        ObservableList<Course> observableList = FXCollections.observableArrayList(courseList);

        coursesTable.getItems().setAll(observableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
        lecuturerName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getLecturer().getUserName()));
    }
}
