/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.EJB;

import scoretracker.beans.persistence.Student;
import scoretracker.beans.persistence.Test;
import scoretracker.beans.persistence.Teststudent;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Lou Cox
 */
@Stateless
public class PDFService {
    @EJB
    DataService dataservice;

    
    //Design of the HTML table
    public static final String CSS = "tr { text-align: left; } th { background-color: lightyellow; padding: 5px; } td {background-color: lightgreen;  padding: 5px; }";

    //Creating a PDF based on a specific student
    public void createPdfStudent(Student s) throws IOException, DocumentException {

        //The directory in which the PDF will be stord, including a name 
        final String PDFLOCATION = "created_PDFs/" + s.getName() + "_" + s.getPrename() + ".pdf";

        //Collect student's points
        List<Teststudent> tests = dataservice.getDataPPSTS(s);

        File file = new File(PDFLOCATION);
        //Creating the required directory structure
        file.getParentFile().mkdirs();

        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();

        //Create the table headers for our table
        StringBuilder sb = new StringBuilder();
        sb.append("<table border=\"2\">");
        sb.append("<tr>");
        sb.append("<th>Course</th>");
        sb.append("<th>Score</th>");
        sb.append("</tr>");

        //Fill the data columns with the student's points
        sb.append("<tr>");
        for (Teststudent test : tests) {
            sb.append("<td>");
            sb.append(test.toString());
            sb.append("</td>");

            sb.append("<td>");
            sb.append(test.getScore());
            sb.append("/20");
            sb.append("</td>");
        }
        sb.append("</tr>");
        sb.append("</table>");

        //Apply the CSS to our table
        CSSResolver cssResolver = new StyleAttrCSSResolver();
        CssFile cssFile = XMLWorkerHelper.getCSS(new ByteArrayInputStream(CSS.getBytes()));
        cssResolver.addCss(cssFile);

        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new ByteArrayInputStream(sb.toString().getBytes()));

        document.close();
    }
    
    

    //Creating a PDF based on a test
    public void createPdfTest(int id) throws IOException, DocumentException {
       
        EntityManagerFactory emf = null;
        EntityManager em = emf.createEntityManager();
        
        //Get information of a single test
        Query oneTest = em.createNamedQuery("Test.findById");
        oneTest.setParameter("id", id);
        Test testInfo = (Test) oneTest.getSingleResult();
        
        Query allTests = em.createNamedQuery("Teststudent.findByTest");
        allTests.setParameter("testid", id);
        List<Teststudent> testsInfo = allTests.getResultList();
        
        em.close();

        //The directory in which the PDF will be stord, including a name 
        final String PDFLOCATION = "created_PDFs/" + testInfo.getName() + ".pdf";

        File file = new File(PDFLOCATION);
        //Creating the required directory structure
        file.getParentFile().mkdirs();

        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();

        //Create the table headers for our table
        StringBuilder sb = new StringBuilder();
        sb.append("<table border=\"2\">");
        sb.append("<tr>");
        sb.append("<th>Student</th>");
        sb.append("<th>Score</th>");
        sb.append("</tr>");

        //Fill the data columns with the student's name and points
        sb.append("<tr>");
        for (Teststudent test : testsInfo) {            
            sb.append("<td>");
            sb.append(test.getStudentId().toString());
            sb.append("</td>");

            sb.append("<td>");
            sb.append(test.getScore());
            sb.append("/20");
            sb.append("</td>");
        }
        sb.append("</tr>");
        sb.append("</table>");

        //Apply the CSS to our table
        CSSResolver cssResolver = new StyleAttrCSSResolver();
        CssFile cssFile = XMLWorkerHelper.getCSS(new ByteArrayInputStream(CSS.getBytes()));
        cssResolver.addCss(cssFile);

        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new ByteArrayInputStream(sb.toString().getBytes()));

        document.close();
    }
}
