/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import scoretracker.beans.EJB.CourseFacade;
import scoretracker.beans.EJB.KlasFacade;
import scoretracker.beans.EJB.LessonFacade;
import scoretracker.beans.EJB.StudentFacade;
import scoretracker.beans.EJB.TestFacade;
import scoretracker.beans.EJB.TeststudentFacade;
import scoretracker.beans.persistence.Student;

/**
 *
 * @author John
 */
@Named(value = "CRUD")
@SessionScoped
public class CRUD implements Serializable {

    @EJB
    KlasFacade classFL;
    
    @EJB
    CourseFacade courseFL;
    
    @EJB
    LessonFacade lessonFL;
    
    @EJB
    StudentFacade studentFL;
    
    @EJB
    TestFacade testFL;
    
    @EJB
    TeststudentFacade teststudentFL;
    /**
     * Creates a new instance of CRUD
     */
    public CRUD() {
    }
    
    public void deleteStudent(Student s){
        this.studentFL.remove(s);
    }
}
