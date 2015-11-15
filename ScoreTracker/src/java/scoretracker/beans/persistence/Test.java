/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.persistence;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author John
 */
@Entity
@Table(name = "test")
@XmlRootElement
@NamedQueries({
    //Collects all tests 
    @NamedQuery(name = "Test.findAll", query = "SELECT t FROM Test t"),
    //Collects all tests with a specific ID
    @NamedQuery(name = "Test.findById", query = "SELECT t FROM Test t WHERE t.id = :id"),
    //Collects all tests with a specific maximum score
    @NamedQuery(name = "Test.findByMaxScore", query = "SELECT t FROM Test t WHERE t.maxScore = :maxScore"),
    //Collects all tests with a specific name
    @NamedQuery(name = "Test.findByName", query = "SELECT t FROM Test t WHERE t.name = :name")})
public class Test implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "maxScore")
    private int maxScore;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "classId", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Klas classId;
    @JoinColumn(name = "courseId", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Course courseId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "testId", fetch = FetchType.EAGER)
    private List<Teststudent> teststudentList;

    public Test() {
        this.id = 0;
    }

    public Test(Integer id) {
        this.id = id;
    }

    public Test(Integer id, int maxScore, String name) {
        this.id = id;
        this.maxScore = maxScore;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Klas getClassId() {
        return classId;
    }

    public void setClassId(Klas classId) {
        this.classId = classId;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }

    @XmlTransient
    public List<Teststudent> getTeststudentList() {
        return teststudentList;
    }

    public void setTeststudentList(List<Teststudent> teststudentList) {
        this.teststudentList = teststudentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Test)) {
            return false;
        }
        Test other = (Test) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "scoretracker.beans.persistence.Test[ id=" + id + " ]";
    }
    
}
