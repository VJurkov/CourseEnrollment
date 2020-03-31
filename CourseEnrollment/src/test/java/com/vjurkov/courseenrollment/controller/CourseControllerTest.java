package com.vjurkov.courseenrollment.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//statička metoda
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//klasu proširujem s dodatnim stvarima koje sping treba da bi se test pokrenuo
@ExtendWith(SpringExtension.class)
//to je nešto što simulira fake browser, fake pozive post etc... tu radi s kodom ne moram klikati
@AutoConfigureMockMvc
//označuje klasu tako da spring zna koje su klase testovi
@SpringBootTest
public class CourseControllerTest {
    //trazim od springa da mi da instancu mockmvca u ovom slucaju
    @Autowired
            //glumi browser
    MockMvc mockMvc;

    //koje metode su testovi koje ce on pokrenuti kao test
    @Test
    public void invalidCourseSubmit() throws Exception {
        this.mockMvc
                //da mvc nešto napravi
                .perform(
                        //post rikvestv koji sam gore importala i radi klasičan post rekvest
                        post("/courses")
                                //reprezentacija jedne forme (objekta) u http pozivu
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                //token koji vraca server meni nakon sto mu da moj html page i taj token moram poslati ponovo serveru kako
                                //bi on znao da je to moja forma
                                .with(csrf())
                                // da fejka usera i passwrod da test radi, da autorizacija radi
                                .with(user("MarkoMaric").password("password").roles("LECTURER", "STUDENT"))
                )
                // kad performance završi, neka tu bude okay kod, nek mi server vrati sve ok
                // ako server vrati bilo šta drugo , test je pao
                .andExpect(status().isOk())
                //ako je test dobro prošao odvedi me na addcourse, ako me nije odveo - test je pao
                .andExpect(view().name("addCourse"));
    }
    @Test
    public void validCourseSubmit() throws Exception {
        this.mockMvc
                .perform(
                        post("/courses")
                                .param("name", "Test")
                                .param("lecturer","MarkoMaric")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .with(csrf())
                                .with(user("MarkoMaric").password("password").roles("LECTURER", "STUDENT"))
                )
                //status ako je okay prošao test me mora redirectat na home
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

}
