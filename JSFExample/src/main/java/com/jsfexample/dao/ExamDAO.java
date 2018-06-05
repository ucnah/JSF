package com.jsfexample.dao;

import com.jsfexample.model.Exam;
import com.jsfexample.model.UserBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Query;

@ManagedBean
@SessionScoped
public class ExamDAO implements BaseDAO {

    public Exam createExam(Exam exam) {
        if (!transactionObj.isActive())
            transactionObj.begin();
        entityMgrObj.persist(exam);
        transactionObj.commit();
        return exam;
    }

    public Exam getExam(long examId) {
        if (!transactionObj.isActive())
            transactionObj.begin();
        Query query = entityMgrObj.
                createQuery("Select e from Exam e where e.id=:id");
        query.setParameter("id", examId);
        Exam foundExam =(Exam) query.getSingleResult();
        transactionObj.commit();
        return foundExam;
    }
}
