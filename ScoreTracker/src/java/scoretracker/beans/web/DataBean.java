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
import scoretracker.beans.persistence.Class;
import scoretracker.beans.persistence.Test;
import scoretracker.beans.persistence.Student;
import scoretracker.beans.persistence.Teststudent;
import scoretracker.beans.service.CourseService;
import scoretracker.beans.service.DataService;
import scoretracker.beans.service.ClassService;
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
    private ClassService classService;

    @EJB
    private CourseService courseService;

    @EJB
    private TestService testService;
    
    private Class clas = new Class();
    private Course course = new Course();
    private Test test = new Test();
    private List<Course> courses = new ArrayList<>();
    
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

    public ClassService getClassService() {
        return classService;
    }

    public void setClassService(ClassService klasService) {
        this.classService = klasService;
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

    public Class getClas() {
        return clas;
    }

    public void setClass(Class klas) {
        this.clas = klas;
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
    
    public List<Course> getAllCourses(){
        return courses;
    }
    
    public final void setCourses(){
        this.courses = courseService.getCourses();
    }
    //End getters & setters
    
    
    // Ajax Listener

    
    public void listener(AjaxBehaviorEvent event) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int courseId = Integer.parseInt(params.get("courseId"));
        int testId = Integer.parseInt(params.get("testId"));
        int klasId = Integer.parseInt(params.get("klasId"));
        if (courseId != 0){
            course = courseService.getCourse(courseId);
        } 
        if (klasId != 0){
            clas = classService.getClass(klasId);
        }
        if (testId != 0){
             test = testService.getTest(testId);
        }
    }
    //End Ajax section
    
    
    public void runSubmit() {
        System.out.println("Submit executed");
    }
    
    public List<Class> getClassses(){
        return service.getClassses();
    }
    
    public Class getClass(int id){
        return service.getClass(id);
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
    
    //GET POINTS PER STUDENT
    public List<Teststudent> getDataPPT(){
        return service.getDataPPT(clas.getId(),course.getId(),test.getId());
    }
    
    //Getting all data for POINTS PER STUDENT
     public List<Teststudent> getDataPPSTS(Student student){
         return service.getDataPPSTS(student);
     }
     
     public List<Course> getDataPPSPC(Student student){
         return service.getDataPPSPC(student);
     }
    
    public List<Student> getDataPPS(){
        return service.getDataPPS();
    }
    
     public Boolean studentFollowsCourse(Student student,Course course){
         return service.studentFollowsCourse(student,course);
     }
     
     public String getTotalCourse(Student student, Course course){
         return service.getTotalCourse(student,course);
     }
    
    public Student getStudentsFromTeststudents(int userId){
        return service.getStudentFromTeststudents(userId);   
    }
}
