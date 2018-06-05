package com.jsfexample.model;

import com.jsfexample.model.converters.FileUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import javax.servlet.http.Part;
import java.io.File;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "exam")
@Entity
@SessionScoped
public class Exam implements Serializable {


    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private long id;

    @Transient
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserBean owner;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "exam_id", referencedColumnName = "id")
    private List<Question> questions;

    public Exam(UserBean owner) {
        this.owner = owner;
    }

    public Exam(UserBean owner, Part partFile) {
        this.owner = owner;
        file = FileUtils.createFile(partFile);
        file.mkdirs();
    }

    public Exam() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    public UserBean getOwner() {
        return owner;
    }

    public void setOwner(UserBean user) {
        this.owner = user;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }
}
