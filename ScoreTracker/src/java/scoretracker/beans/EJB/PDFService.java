/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.EJB;

import scoretracker.beans.persistence.Student;
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
        final String PDFLOCATION = "C:\\created_PDFs\\" + s.getName() + s.getPrename() + "_" + s.getRNr() + "_" + s.getClassId().getName() + ".pdf";

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
        sb.append("<h4>");
        sb.append("Overview of all tests made by: ");
        sb.append(s.getName());
        sb.append(" ");
        sb.append(s.getPrename());
        sb.append("</h4>");
        sb.append("<p>Class: ");
        sb.append(s.getClassId().getName());
        sb.append("</p><br/>");
        sb.append("<table border=\"2\">");
        sb.append("<tr>");
        sb.append("<th>Course</th>");
        sb.append("<th>Score</th>");
        sb.append("</tr>");

        //Fill the data columns with the student's points
        for (Teststudent test : tests) {
            sb.append("<tr>");
            sb.append("<td>");
            sb.append(test.getTestId().getName());
            sb.append(" - " + test.getTestId().getCourseId().getName());
            sb.append("</td>");

            sb.append("<td>");
            sb.append(test.getScore());
            sb.append("/20");
            sb.append("</td>");
            sb.append("</tr>");
        }

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
    public void createPdfTest(Teststudent t) throws IOException, DocumentException {

        List<Teststudent> testsInfo = dataservice.getDataPPT(0, 0, t.getTestId().getId());

        //The directory in which the PDF will be stord, including a name 
        final String PDFLOCATION = "C:\\created_PDFs\\" + t.getTestId().getName() + "_" + t.getTestId().getClassId().getName() + ".pdf";

        File file = new File(PDFLOCATION);
        //Creating the required directory structure
        file.getParentFile().mkdirs();

        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();

        //Create the table headers for our table
        StringBuilder sb = new StringBuilder();
        sb.append("<h4>Results for: ");
        sb.append(t.getTestId().getName());
        sb.append(" - ");
        sb.append(t.getTestId().getCourseId().getName());
        sb.append("</h4>");
        sb.append("<p>Voor klas ");
        sb.append(t.getTestId().getClassId().getName());
        sb.append("</p><br/>");
        sb.append("<table border=\"2\">");
        sb.append("<tr>");
        sb.append("<th>Student</th>");
        sb.append("<th>Score</th>");
        sb.append("</tr>");

        //Fill the data columns with the student's name and points
        for (Teststudent test : testsInfo) {
            sb.append("<tr>");
            sb.append("<td>");
            sb.append(test.getStudentId().getName());
            sb.append(" ");
            sb.append(test.getStudentId().getPrename());
            sb.append("</td>");

            sb.append("<td>");
            sb.append(test.getScore());
            sb.append("/20");
            sb.append("</td>");
            sb.append("</tr>");
        }

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
