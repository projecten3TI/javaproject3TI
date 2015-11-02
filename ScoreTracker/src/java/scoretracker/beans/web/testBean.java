/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*package info.toegepaste.www.web;

import info.toegepaste.www.entity.Klas;
import info.toegepaste.www.entity.Test;
import info.toegepaste.www.entity.Vak;
import info.toegepaste.www.service.KlasService;
import info.toegepaste.www.service.TestService;
import info.toegepaste.www.service.VakService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean
@ViewScoped
public class testBean {

    @EJB
    private KlasService klasService;

    @EJB
    private VakService vakService;

    @EJB
    private TestService testService;

    private Klas klas = new Klas();
    private Vak vak = new Vak();
    private Test test = new Test();

    private String succesMessage;

    public InsertBean() {
        test.setKlas(new Klas());
        test.setVak(new Vak());
    }

    public KlasService getKlasService() {
        return klasService;
    }

    public void setKlasService(KlasService klasService) {
        this.klasService = klasService;
    }

    public void klasToevoegen() {
        klasService.klasToevoegen(klas);
        klas = new Klas();
        succesMessage = "De test is toegevoegd";

    }

    public void vakToevoegen() {
        vakService.vakToevoegen(vak);
        vak = new Vak();
        succesMessage = "De test is toegevoegd";

    }

    public void testToevoegen() {
        test.setDatum(new Date());
        testService.testToevoegen(test);
        test = new Test();
        klas = new Klas();

        succesMessage = "De test is toegevoegd";

    }

    //getters en setters
    public Klas getKlas() {
        return klas;
    }

    public void setKlas(Klas klas) {
        this.klas = klas;
    }

    public Vak getVak() {
        return vak;
    }

    public void setVak(Vak vak) {
        this.vak = vak;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void listener(AjaxBehaviorEvent event) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String klasId = (String) params.get("klasId");

        klas.setId(Long.parseLong(klasId));

    }

    public List<Vak> getAllVakkenKlas() {
        List<Vak> vakken;
        if (klas != null && klas.getId() != null && klas.getId() != 0) {
            klas = klasService.getKlasById(klas.getId());
            vakken = klas.getVakken();
        } else {
            vakken = new ArrayList<Vak>();
        }

        return vakken;
    }

    public String getSuccesMessage() {
        return succesMessage;
    }

    public void setSuccesMessage(String succesMessage) {
        this.succesMessage = succesMessage;
    }

}*/