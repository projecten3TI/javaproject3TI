/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.web;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import scoretracker.beans.EJB.CourseFacade;
import scoretracker.beans.EJB.DataService;
import scoretracker.beans.EJB.KlasFacade;
import scoretracker.beans.EJB.LessonFacade;
import scoretracker.beans.EJB.StudentFacade;
import scoretracker.beans.EJB.TestFacade;
import scoretracker.beans.EJB.TeststudentFacade;
import scoretracker.beans.persistence.Course;
import scoretracker.beans.persistence.Klas;
import scoretracker.beans.persistence.Student;
import scoretracker.beans.persistence.Test;
import scoretracker.beans.persistence.Teststudent;

/**
 *
 * @author John
 */
@Named(value = "data")
@SessionScoped
public class data implements Serializable {

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
    
    @EJB
    DataService service;
    
    private Klas clas = new Klas();
    private Course course = new Course();
    private Test test = new Test();
    
    private List<Teststudent> tss;
    boolean saveButtonShow = false;
    
    /**
     * Creates a new instance of data
     */
    public data() {
    }

    public Klas getClas() {
        return clas;
    }

    public void setClas(Klas clas) {
        this.clas = clas;
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

    public boolean isSaveButtonShow() {
        return saveButtonShow;
    }

    public void setSaveButtonShow(boolean saveButtonShow) {
        this.saveButtonShow = saveButtonShow;
    }
    
    // Ajax Listener

    
    public void listener(AjaxBehaviorEvent event) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int courseId = Integer.parseInt(params.get("courseId"));
        int testId = Integer.parseInt(params.get("testId"));
        int klasId = Integer.parseInt(params.get("klasId"));
        if (courseId != 0){
            course = courseFL.find(courseId);
        } 
        if (klasId != 0){
            clas = classFL.find(klasId);
        }
        if (testId != 0){
             test = testFL.find(testId);
        }
    }
    //End Ajax section
    
    public List<Klas> getClassses(){
        return classFL.findAll();
    }
    
    public Klas getClass(int id){
        return classFL.find(id);
    }
    
    public List<Student> getStudents(){
        return studentFL.findAll();
    }
    
    public Student getStudent(int id){
        return studentFL.find(id);
    }
    
    public Course getCourse(int id){
        return courseFL.find(id);
    }
    
    public List<Course> getCourses(){
        return courseFL.findAll();
    }
    
    public List<Test> getTests(){
        return testFL.findAll();
    }

    public List<Teststudent> getTss() {
        return tss;
    }

    public void setTss(List<Teststudent> tss) {
        this.tss = tss;
    }
    
    public void initTSS(){
        this.tss = teststudentFL.findAll();
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
    
     public Boolean studentFollowsCourse(Student student,Course course){
         return service.studentFollowsCourse(student,course);
     }
     
     public String getTotalCourse(Student student, Course course){
         return service.getTotalCourse(student,course);
     }
    
    public Student getStudentsFromTeststudents(int userId){
        return service.getStudentFromTeststudents(userId);   
    }
    
     public String getTotalCourseAll(Student student) {
         return service.getTotalCourseAll(student, courseFL.findAll());
     }
     
     public String editScore(Teststudent ts){
         this.saveButtonShow = true;
         tss.get(tss.indexOf(ts)).setEditable(true);
         return null;
     }
     
     public void saveAll(){
         for (Teststudent ts : tss){
             Teststudent tsO = teststudentFL.find(ts.getId());
             if (ts.getScore() != tsO.getScore()){
             teststudentFL.remove(tsO);
             teststudentFL.create(ts);
             }
             
         }
         tss = new ArrayList<>();
     }
}
