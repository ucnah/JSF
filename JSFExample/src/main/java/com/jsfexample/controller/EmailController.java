package com.jsfexample.controller;

import com.jsfexample.model.Exam;
import com.jsfexample.model.UserBean;
import com.jsfexample.service.MailServiceImpl;
import com.jsfexample.service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean(eager = true)
@ViewScoped
public class EmailController implements Serializable {

    private long hiddenId;
    private String hiddenUsername;
    private String hiddenEmail;
    private long examId;
    private List<UserBean> studentsList = new ArrayList<>();
    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    @ManagedProperty(value = "#{mailServiceImpl}")
    private MailServiceImpl mailService;


    @PostConstruct
    public void init() {
        studentsList = buildList();
    }

    public void assignExam() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> parameterMap = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        for (String key : parameterMap.keySet()) {
            if (key.contains("hiddenId")) {
                hiddenId = Long.valueOf(parameterMap.get(key));
            } else if (key.contains("hiddenUsername")) {
                hiddenUsername = parameterMap.get(key);
            } else if (key.contains("hiddenEmail")) {
                hiddenEmail = parameterMap.get(key);
            }
        }
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        examId = (long) session.getAttribute("examId");
        userService.assignExam(hiddenId, examId);
        mailService.sendMail(hiddenId, hiddenEmail, hiddenUsername, examId);
    }

    private List<UserBean> buildList() {
        return userService.getStudents();
    }

    public long getHiddenId() {
        return hiddenId;
    }

    public void setHiddenId(long hiddenId) {
        this.hiddenId = hiddenId;
    }

    public String getHiddenUsername() {
        return hiddenUsername;
    }

    public void setHiddenUsername(String hiddenUsername) {
        this.hiddenUsername = hiddenUsername;
    }

    public String getHiddenEmail() {
        return hiddenEmail;
    }

    public void setHiddenEmail(String hiddenEmail) {
        this.hiddenEmail = hiddenEmail;
    }

    public void setStudentsList(List<UserBean> studentsList) {
        this.studentsList = studentsList;
    }

    public List<UserBean> getStudentsList() {
        return studentsList;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public MailServiceImpl getMailService() {
        return mailService;
    }

    public void setMailService(MailServiceImpl mailService) {
        this.mailService = mailService;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public void takeExam() throws IOException {
        long selectedExamId = 0;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> parameterMap = facesContext.
                getExternalContext().getRequestParameterMap();
        for (String key : parameterMap.keySet()) {
            if (key.contains("selectedExamId")) {
                selectedExamId = Long.valueOf(parameterMap.get(key));
                break;
            }
        }
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        List<Exam> exams = (List<Exam>) session.getAttribute("exams");
        Exam selectedExam = null;
        for (Exam exam : exams) {
            if (exam.getId() == selectedExamId) {
                selectedExam = exam;
                break;
            }
        }
        session.setAttribute("questions",selectedExam.getQuestions());
        facesContext.getExternalContext().redirect("examPage.xhtml");
    }
}
