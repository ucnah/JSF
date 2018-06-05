package com.jsfexample.service;

import com.jsfexample.dao.ExamDAO;
import com.jsfexample.dao.UserDAO;
import com.jsfexample.model.Exam;
import com.jsfexample.model.UserBean;
import com.jsfexample.service.exception.UserNotFoundException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class UserService implements Serializable {

    @ManagedProperty(value = "#{userDAO}")
    private UserDAO userDAO;

    @ManagedProperty(value = "#{examDAO}")
    private ExamDAO examDAO;


    public void register(UserBean userBean) {
        userDAO.createUser(userBean);
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserBean login(String username) {

        UserBean foundUser = userDAO.findUser(username);
        if (foundUser == null) {
            throw new UserNotFoundException();
        }
        return foundUser;
    }

    public List<UserBean> getStudents() {
        return userDAO.getStudents();
    }

    public void assignExam(long studentId, long examId) {
        UserBean student=userDAO.getUser(studentId);
        Exam exam= examDAO.getExam(examId);
        student.addExam(exam);
        userDAO.createUser(student);


    }

    public ExamDAO getExamDAO() {
        return examDAO;
    }

    public void setExamDAO(ExamDAO examDAO) {
        this.examDAO = examDAO;
    }
}
