/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.web;

/**
 *
 * @author robin
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import scoretracker.beans.EJB.UploadService;


/**
 *
 * @author robin
 */
@ManagedBean
@ViewScoped
public class uploadBean {
    private File file;
    private String fileContent;
    private String message;
    
    
    @EJB
    UploadService uploadService;
    public void upload() throws IOException{
        //try {
           // fileContent = new Scanner(file.getInputStream()).useDelimiter("\\A").next();
            
           // fileContent = file.getPath();
        //} catch (IOException e){
            
        //}
           // message = "";
            //if(!"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(file.getContentType())){
            
            //message = "Not an Excel File";
            //}
            //else {
               Boolean upgeload = this.uploadService.upload("C:\\Users\\robin\\Desktop\\3Ti Semester 1\\Applicatieontwikkeling\\Java\\Voorbeeld Test Resultaten.xlsx");
               if (upgeload = false) {
                   message = "Upload Failed";
               }
               else
               {
                   message = "Upload succesful";
               }
            //}
            
        
    }
    
    public File getFile(){
        return file;
    }
    
    public void setFile(File file){
        this.file = file;
    }
    
    public String getFileContent(){
        return fileContent;
    }
    
    public void setFileContent(String fileContent){
        this.fileContent = fileContent;
    }
    
    
    public String getMessage(){
        return message;
    }
    
    public void setMessage(String message){
        this.message = message;
    }
    
    
}

