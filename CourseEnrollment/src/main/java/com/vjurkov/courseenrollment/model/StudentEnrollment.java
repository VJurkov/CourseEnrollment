package com.vjurkov.courseenrollment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

//skup podataka koje korisnik moze poslati ali to nemam u bazi
public class StudentEnrollment {


    private Long courseId;
    private String studentId;


}
