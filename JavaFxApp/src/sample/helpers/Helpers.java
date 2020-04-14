package sample.helpers;

import javafx.scene.control.Alert;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class Helpers {


    public static RestTemplate createRestTemplate(MediaType type){
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(type);
        converter.setSupportedMediaTypes(mediaTypes);
        messageConverters.add(converter);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }


    public static void postMessage(String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
