/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.EJB;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.Part;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import scoretracker.beans.persistence.Course;
import scoretracker.beans.persistence.Klas;
import scoretracker.beans.persistence.Student;
import scoretracker.beans.persistence.Test;
import scoretracker.beans.persistence.Teststudent;


/**
 *
 * @author robin
 */
@Stateless
public class UploadService {
    
    @PersistenceContext
    private EntityManager em;
    
    public Boolean upload(Part file) throws IOException{
        Boolean upload = true;
        InputStream inputStream = file.getInputStream();
        Query q;
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        List<Teststudent> studenten = new ArrayList<Teststudent>();
        
        
        //Klas ophalen uit Excel
        Row nextRow = iterator.next();
        q = em.createNamedQuery("Klas.findByName");
        Klas klas = new Klas();
        q.setParameter("name", nextRow.getCell(1).getStringCellValue());
        klas = (Klas) q.getSingleResult();
        
         
        //vak ophalen uit Excel
        nextRow = iterator.next();
        q = em.createNamedQuery("Course.findByName");
        Course course = new Course();
        q.setParameter("name",nextRow.getCell(1).getStringCellValue());
        course = (Course) q.getSingleResult();
        
        //Test object aanmaken
        Test test = new Test();
        nextRow = iterator.next();
        test.setClassId(klas);
        test.setCourseId(course);
        test.setName(nextRow.getCell(1).getStringCellValue());
        nextRow = iterator.next();
        Double maxScore = nextRow.getCell(2).getNumericCellValue();
        test.setMaxScore(maxScore.intValue());
        
        nextRow = firstSheet.getRow(5);
        while(iterator.hasNext())
        {
          nextRow = iterator.next();
          Student student = new Student();
          q = em.createNamedQuery("Student.findByRNr");
          q.setParameter("rNr",nextRow.getCell(0).getStringCellValue());
          student = (Student) q.getSingleResult();
          
          if (student.getId() != null)
          {
          Teststudent testStudent = new Teststudent();
          
          testStudent.setTestId(test);
          testStudent.setStudentId(student);
          Double score = nextRow.getCell(2).getNumericCellValue();
          testStudent.setScore(score.intValue());
          
          studenten.add(testStudent);
          }
          else
          {
              upload=false;
          }
        }
        
        workbook.close();
        inputStream.close();
        //fouthandeling en toevoegen
        if(upload){
            if(klas.getId() != null && course.getId() != null)
            {
                em.persist(test);
                for(Teststudent t : studenten) {
                    em.persist(t);
                }
                
            }
            else{
                upload=false;
            }
        }
        return upload;
            
        
        
    }
    
    
}
