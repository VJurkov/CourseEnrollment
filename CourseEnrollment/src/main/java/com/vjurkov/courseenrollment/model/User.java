package com.vjurkov.courseenrollment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
@SecondaryTables({
        @SecondaryTable(name="authorities")
})
public class User {

    @Id
    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(table="authorities")
    private String authority;
    //builder pattern
    //statička klasa builder
    public static class Builder{

        private String userName;

        private String password;

        private boolean enabled;

        private String authority;

        public Builder withUsername(String userName){
            this.userName = userName;
            return this;
        }

        public Builder withPassword(String password){
            this.password = password;
            return this;
        }

        public Builder asLecturer(){
            this.authority = "LECTURER";
            return this;
        }

        public Builder asStudent(){
            this.authority = "STUDENT";
            return this;
        }

        public User build(){
            User user = new User();
            user.setUserName(this.userName);
            user.setPassword(this.password);
            user.setEnabled(true);
            user.setAuthority(this.authority);
            return user;
        }
    }
}