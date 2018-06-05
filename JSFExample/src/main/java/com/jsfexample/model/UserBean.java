package com.jsfexample.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    private String email;

    private Role role;

    private static Map<Role, String> possibleRoles;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "student_exam",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "exam_id")}
    )
    private List<Exam> exams = new ArrayList<>();

    static {
        possibleRoles = new HashMap<>();
        possibleRoles.put(Role.STUDENT, Role.STUDENT.getValue());
        possibleRoles.put(Role.ADMINISTRATOR, Role.ADMINISTRATOR.getValue());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Map<Role, String> getPossibleRoles() {
        return possibleRoles;
    }

    public long getId() {
        return id;
    }

    public void addExam(Exam exam){
        exams.add(exam);
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setId(long id) {
        this.id = id;
    }
}
