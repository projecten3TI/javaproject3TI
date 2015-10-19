/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoretracker.beans.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jamie
 */
@Entity
@Table(name = "teststudent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Teststudent.findAll", query = "SELECT t FROM Teststudent t"),
    @NamedQuery(name = "Teststudent.findById", query = "SELECT t FROM Teststudent t WHERE t.id = :id"),
    @NamedQuery(name = "Teststudent.findByScore", query = "SELECT t FROM Teststudent t WHERE t.score = :score")})
public class Teststudent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "score")
    private Integer score;
    @JoinColumn(name = "testId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Test testId;
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User userId;

    public Teststudent() {
    }

    public Teststudent(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Test getTestId() {
        return testId;
    }

    public void setTestId(Test testId) {
        this.testId = testId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof Teststudent)) {
            return false;
        }
        Teststudent other = (Teststudent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "scoretracker.beans.entity.Teststudent[ id=" + id + " ]";
    }
    
}
