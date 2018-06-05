package com.jsfexample.dao;

import com.jsfexample.model.UserBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@ManagedBean(name = "userDAO")
@SessionScoped
public class UserDAO implements BaseDAO {


    public void createUser(UserBean user) {
        if (!transactionObj.isActive())
            transactionObj.begin();
        entityMgrObj.persist(user);
        transactionObj.commit();
    }

    public UserBean findUser(String username) {
        if (!transactionObj.isActive())
            transactionObj.begin();
        Query query = entityMgrObj.
                createQuery("Select e from UserBean e where e.username=:username");
        query.setParameter("username", username);
        UserBean foundUser = (UserBean) query.getSingleResult();
        transactionObj.commit();
        return foundUser;
    }

    public List<UserBean> getStudents() {
        if (!transactionObj.isActive())
            transactionObj.begin();
        Query query = entityMgrObj.
                createQuery("Select e from UserBean e where e.role=0");
        List<UserBean> foundStudents = query.getResultList();
        transactionObj.commit();
        return foundStudents;
    }

    public UserBean getUser(long studentId) {
        if (!transactionObj.isActive())
            transactionObj.begin();
        Query query = entityMgrObj.
                createQuery("Select e from UserBean e where e.id=:id");
        query.setParameter("id", studentId);
        UserBean foundStudent =(UserBean) query.getSingleResult();
        transactionObj.commit();
        return foundStudent;
    }
}
