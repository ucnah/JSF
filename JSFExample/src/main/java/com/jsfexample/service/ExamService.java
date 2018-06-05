package com.jsfexample.service;

import com.jsfexample.dao.ExamDAO;
import com.jsfexample.model.Exam;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ExamService {

    @ManagedProperty(value = "#{examDAO}")
    private ExamDAO examDAO;

    public Exam register(Exam exam) {
        return examDAO.createExam(exam);
    }

    public void setExamDAO(ExamDAO examDAO) {
        this.examDAO = examDAO;
    }
}
