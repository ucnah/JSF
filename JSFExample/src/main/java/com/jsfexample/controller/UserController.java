package com.jsfexample.controller;

import com.jsfexample.model.Exam;
import com.jsfexample.model.Role;
import com.jsfexample.model.UserBean;
import com.jsfexample.model.converters.FileUtils;
import com.jsfexample.service.ExamService;
import com.jsfexample.service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;

@ManagedBean(eager = true)
@ViewScoped
public class UserController implements Serializable {

    @ManagedProperty(value = "#{userBean}")
    private UserBean userBean;

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    @ManagedProperty(value = "#{examService}")
    private ExamService examService;

    private Part file;

    @ManagedProperty(value = "#{exam}")
    private Exam exam;


    private UserBean foundUser;

    public UserController() {
        this.foundUser = new UserBean();
    }

    public String register() {
        userService.register(userBean);
        return "login";
    }

    @PostConstruct
    public void init() {
        foundUser = userService.login(userBean.getUsername());
    }

    public String login() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        userService.login(userBean.getUsername());
        session.setAttribute("user", foundUser);
        if (foundUser.getRole().getValue().equals(Role.STUDENT.getValue())) {
            session.setAttribute("exams",foundUser.getExams());
            return "secured/student/hellostudent";
        }
        return "secured/admin/helloadmin";
    }

    public void upload() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        UserBean user = (UserBean) session.getAttribute("user");
        Exam exam = new Exam(user, file);
        try (InputStream input = file.getInputStream()) {
            Files.copy(input, exam.getFile().toPath());
        } catch (IOException e) {
            // Show faces message?
        }
        exam.setQuestions(FileUtils.parse(exam.getFile()));
        this.exam = examService.register(exam);
        session.setAttribute("examId", exam.getId());
        facesContext.getExternalContext().redirect("secured/admin/assignExam.xhtml");
    }


    public void setFile(Part file) {
        this.file = file;
    }

    public Part getFile() {
        return file;
    }

    public ExamService getExamService() {
        return examService;
    }

    public void setExamService(ExamService examService) {
        this.examService = examService;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public UserBean getFoundUser() {
        return foundUser;
    }

    public void setFoundUser(UserBean foundUser) {
        this.foundUser = foundUser;
    }
}
