package com.vjurkov.courseenrollment.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vjurkov.courseenrollment.model.Course;
import com.vjurkov.courseenrollment.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class CourseRestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
            //moramo pretvarati objekte u json jer api prima json
    ObjectMapper objectMapper;

    @Test
    public void findAll() throws Exception {
        this.mockMvc
                .perform(
                        get("/api/course")
                                .with(user("MarkoMaric").password("password").roles("LECTURER"))
                )
                .andExpect(status().isOk());
    }

    @Test
    //tu imamo invlaid course jer nema ime
    public void saveInvalidLecturer() throws Exception {
        User lecturer = new User();
        lecturer.setUserName("MarkoMaric");
        lecturer.setPassword("");
        lecturer.setAuthority("LECTURER");
        lecturer.setEnabled(true);
        Course course = new Course();
        course.setLecturer(lecturer);
        this.mockMvc
                .perform(
                        post("/api/course")
                                .content(objectMapper.writeValueAsString(course))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(user("MarkoMaric").password("password").authorities(new SimpleGrantedAuthority("LECTURER")))
                )
                .andExpect(status().isBadRequest());
    }
    @Test
    //tu je validni course
    public void saveValidLecturer() throws Exception {
        User lecturer = new User();
        lecturer.setUserName("MarkoMaric");
        Course course = new Course();
        course.setName("Test");
        course.setLecturer(lecturer);
        this.mockMvc
                .perform(
                        post("/api/course")
                                .content(objectMapper.writeValueAsString(course))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(user("MarkoMaric").password("password").authorities(new SimpleGrantedAuthority("LECTURER")))
                )
                .andExpect(status().isCreated());
    }

}
