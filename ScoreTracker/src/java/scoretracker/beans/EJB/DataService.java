/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.EJB;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import scoretracker.beans.persistence.Course;
import scoretracker.beans.persistence.Student;
import scoretracker.beans.persistence.Teststudent;

/**
 *
 * @author Jamie
 */
@Stateless
public class DataService {

    //    private String[] messages = {"hello world from EJB !", "Bonjour", "Yolo !"};
    @PersistenceContext
    private EntityManager em;

    public List<Teststudent> getDataPPSTS(Student student) {
        Query q = em.createQuery("SELECT ts from Teststudent ts WHERE ts.studentId.id = :studentId");
        q.setParameter("studentId", student.getId());
        return q.getResultList();
    }

    public List<Course> getDataPPSPC(Student student) {
        Query q = em.createQuery("SELECT c FROM Course c WHERE c.id IN (SELECT l.courseId.id FROM Lesson l WHERE l.classId.id = :classId)");
        q.setParameter("classId", student.getClassId().getId());
        return q.getResultList();
    }

    public Boolean studentFollowsCourse(Student student, Course course) {
        Query q = em.createQuery("SELECT l FROM Lesson l WHERE l.classId.id = :classId AND l.courseId.id = :courseId");
        q.setParameter("classId", student.getClassId().getId());
        q.setParameter("courseId", course.getId());
        List results = q.getResultList();
        if (results.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public String getTotalCourse(Student student, Course course) {
        Query q = em.createQuery("SELECT (100 * ts.score / 20) FROM Teststudent ts WHERE ts.testId.id IN (SELECT t.id FROM Test t WHERE t.courseId.id = :courseId) AND ts.studentId.id = :studentId");
        q.setParameter("courseId", course.getId());
        q.setParameter("studentId", student.getId());
        List<Integer> results = q.getResultList();
        if (results.size() == 1) {
            return results.get(0).toString() + "% (1)";
        }
        if (results.size() > 1) {
            int total = 0;
            for (Integer number : results) {
                total = total + number;
            }
            Integer grandTotal = total / results.size();
            return grandTotal.toString() + "% (" + results.size() + ")";
        } else {
            return "No tests";
        }
    }

    public String getTotalCourseAll(Student student, List<Course> courses) {
        Integer bigTotal = 0;
        Integer numberOfTests = 0;
        Boolean foundSomething = false;
        for (Course c : courses) {
            if (studentFollowsCourse(student, c)) {
                Query q = em.createQuery("SELECT (100 * ts.score / 20) FROM Teststudent ts WHERE ts.testId.id IN (SELECT t.id FROM Test t WHERE t.courseId.id = :courseId) AND ts.studentId.id = :studentId");
                q.setParameter("courseId", c.getId());
                q.setParameter("studentId", student.getId());
                List<Integer> results = q.getResultList();
                if (results.size() == 1) {
                    bigTotal = bigTotal + results.get(0);
                    foundSomething = true;
                    numberOfTests++;
                }
                if (results.size() > 1) {
                    int total = 0;
                    for (Integer number : results) {
                        total = total + number;

                    }
                    numberOfTests++;
                    Integer grandTotal = total / results.size();
                    bigTotal = bigTotal + grandTotal;
                    foundSomething = true;
                } else {
                }
            }
        }
        if (!foundSomething) {
            return "N/A";
        } else {
            bigTotal = bigTotal / numberOfTests;
            return bigTotal.toString() + '%';
        }

    }

    public List<Teststudent> getDataPPT(int classId, int courseId, int testId) {
        String query = "SELECT ts from Teststudent ts";
        Query q = em.createQuery(query);
        Boolean first = false;
        if (classId != 0 || courseId != 0 || testId != 0) {
            query += " WHERE ";
            if (classId != 0) {
                query += "ts.testId IN (SELECT t FROM Test t WHERE t.classId.id = :classId)";
                first = true;
            }
            if (courseId != 0) {
                if (first) {
                    query += " AND ";
                } else {
                    first = true;
                }
                query += "ts.testId IN (SELECT t FROM Test t WHERE t.courseId.id = :courseId)";
            }
            if (testId != 0) {
                if (first) {
                    query += " AND ";
                }
                query += "ts.testId IN (SELECT t from Test t WHERE t.id = :testId)";
            }
            q = em.createQuery(query);
            if (classId != 0) {
                q.setParameter("classId", classId);
            }
            if (courseId != 0) {
                q.setParameter("courseId", courseId);
            }
            if (testId != 0) {
                q.setParameter("testId", testId);
            }
        } else {
            q = em.createQuery(query);
        }
        return q.getResultList();
    }

    public Student getStudentFromTeststudents(int userId) {
        Query q = em.createQuery("Select u from Student u WHERE u.id = ?");
        q.setParameter(1, userId);
        return (Student) q.getSingleResult();
    }

    //Ajax Methods
    public String getClassTest(int klasId) {
        String result = "";
        Query q = em.createQuery("SELECT c from Teststudent c WHERE c.userId IN (SELECT u FROM Student u WHERE u.klasId = ?)");
        q.setParameter(1, klasId);
        List<Teststudent> u = q.getResultList();
        for (Teststudent t : u) {
            result += "<tr><th>" + t.getTestId().getName() + "</th><th>" + t.getTestId().getCourseId().getName() + "</th><th>" + t.getStudentId().getRNr() + "</th><th>" + t.getScore() + "</th><th>" + t.getTestId().getMaxScore() + "</th><th>" + t.getStudentId().getClassId() + "</th></tr>";
        }

        return result;
    }

    /*public StudentClasses getCourseTest(){
        
     }
    
     public StudentClasses getTest(){
        
     }*/
    //End ajax methods
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
