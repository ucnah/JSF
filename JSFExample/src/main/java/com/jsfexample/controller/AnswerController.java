package com.jsfexample.controller;

import com.jsfexample.model.Question;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(eager = true)
@ViewScoped
public class AnswerController {


    private String answer;
    private List<Question> questions;

    @PostConstruct
    public void init() {
        questions = new ArrayList<>();
    }


    public void verify(){

        System.out.println(answer);
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Question> getQuestions() {
        if(questions==null) questions=new ArrayList<>();
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions.addAll(questions);
    }
}
