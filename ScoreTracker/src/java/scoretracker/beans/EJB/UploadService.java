/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.EJB;


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
        Test test = new Test();
        
        
        //Klas ophalen uit Excel
        Row nextRow = iterator.next();
        q = em.createNamedQuery("Klas.findByName");
        List<Klas> klassen = new ArrayList<>();
        q.setParameter("name", nextRow.getCell(1).getStringCellValue());
        klassen = q.getResultList();
        
        if(klassen.size() != 1){
        //vak ophalen uit Excel
        nextRow = iterator.next();
        q = em.createNamedQuery("Course.findByName");
        List<Course> courses = new ArrayList<>();
        q.setParameter("name",nextRow.getCell(1).getStringCellValue());
        courses = q.getResultList();
        
        if(courses.size() != 1){
        //Test object aanmaken
        
        nextRow = iterator.next();
        test.setClassId(klassen.get(0));
        test.setCourseId(courses.get(0));
        test.setName(nextRow.getCell(1).getStringCellValue());
        nextRow = iterator.next();
        Double maxScore = nextRow.getCell(1).getNumericCellValue();
        test.setMaxScore(maxScore.intValue());
        
        nextRow = firstSheet.getRow(5);
        while(iterator.hasNext())
        {
          nextRow = iterator.next();
          List<Student> students = new ArrayList<>();
          q = em.createNamedQuery("Student.findByRNr");
          q.setParameter("rNr",nextRow.getCell(0).getStringCellValue());
          students = q.getResultList();
          
          if (students.size() != 1)
          {
          Student student = students.get(0);
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
        }
        else
        {
            upload = false;
        }
        }
        else
        {
            upload = false;
        }
        
        workbook.close();
        inputStream.close();
        //fouthandeling en toevoegen
        if(upload){
            
                em.persist(test);
                for(Teststudent t : studenten) {
                    em.persist(t);
                }
                
            }
            else{
                upload=false;
            }
    
        return upload;
            
        
        
    }
    
    
}
