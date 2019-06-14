package org.demoiselle.jee.gateway.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gladson
 */
@Entity
@Table
@XmlRootElement
public class Resume implements Serializable {

    @Id
    private UUID id;

    private Integer qtde;

    @Temporal(TemporalType.DATE)
    private Date dia;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getQtde() {
        return qtde;
    }

    public void setQtde(Integer qtde) {
        this.qtde = qtde;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

}
