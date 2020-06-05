package com.vjurkov.courseenrollment.integration;

import com.vjurkov.courseenrollment.dao.UserRepository;
import com.vjurkov.courseenrollment.model.User;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//statička metoda
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.liquibase.enabled=false",
        "spring.flyway.enabled=false"
})
public class RegisterIntergrationTest {
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void registerUser() throws Exception {
        User user = new User();
        user.setAuthority("LECTURER");
        user.setEnabled(true);
        user.setPassword("password");
        user.setUserName("Test");
        MockMultipartFile firstFile = new MockMultipartFile("image", "image.png", "image/png", "someimage".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/register")
                .file(firstFile)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", user.getUserName())
                .param("password", user.getPassword())
                .param("authority", user.getAuthority())
                .param("enabled", "true")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

        User addedUser = userRepository.getOne(user.getUserName());
        //provjeravamo da li je user upisan u bazu
        assertThat(addedUser).isNotEqualTo(null);
        assertThat(addedUser.getUserName()).isEqualTo(user.getUserName());
    }
}
