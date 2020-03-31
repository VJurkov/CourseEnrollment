package com.vjurkov.courseenrollment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authorities")
public class Roles {

    @Id
    @Column()
    private String username;

    @Column(name = "authority")
    private String role;


}