/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import scoretracker.beans.persistence.Course;
import scoretracker.beans.persistence.Klas;
import scoretracker.beans.persistence.Test;
import scoretracker.beans.persistence.Teststudent;
import scoretracker.beans.persistence.User;
import scoretracker.beans.service.CourseService;
import scoretracker.beans.service.DataService;
import scoretracker.beans.service.KlasService;
import scoretracker.beans.service.TestService;

/**
 *
 * @author Jamie
 */
@ManagedBean
@RequestScoped
public class DataBean {
    
    @EJB
    private DataService service;
    
    @EJB
    private KlasService klasService;

    @EJB
    private CourseService courseService;

    @EJB
    private TestService testService;
    
    private Klas klas = new Klas();
    private Course course = new Course();
    private Test test = new Test();
    
    private String success;
    
    public DataBean(){
    }
    
    // Getters & Setters

    public DataService getService() {
        return service;
    }

    public void setService(DataService service) {
        this.service = service;
    }

    public KlasService getKlasService() {
        return klasService;
    }

    public void setKlasService(KlasService klasService) {
        this.klasService = klasService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public TestService getTestService() {
        return testService;
    }

    public void setTestService(TestService testService) {
        this.testService = testService;
    }

    public Klas getKlas() {
        return klas;
    }

    public void setKlas(Klas klas) {
        this.klas = klas;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
    
    //End getters & setters
    
    
    // Ajax Listener

    
    public void listener(AjaxBehaviorEvent event) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String courseId = (String) params.get("courseId");
        String testId = (String) params.get("testId");
        String klasId = (String) params.get("klasId");
        course.setId(Integer.parseInt(courseId));
        klas.setId(Integer.parseInt(klasId));
        test.setId(Integer.parseInt(testId));
    }
    //End Ajax section
    
    
    public void runSubmit() {
        System.out.println("Submit executed");
    }
    
    public List<Klas> getKlasses(){
        return service.getKlasses();
    }
    
    public Klas getKlas(int id){
        return service.getKlas(id);
    }
    
    public Course getCourse(int id){
        return service.getCourse(id);
    }
    
    public List<Course> getCourses(){
        return service.getCourses();
    }
    
    public List<Test> getTests(){
        return service.getTests();
    }
    
    public List<Teststudent> getData(){
        return service.getTeststudents(klas.getId(),course.getId(),test.getId());
    }
    
    public User getUsersFromTeststudents(int userId){
        return service.getUserFromTeststudents(userId);   
    }
}
