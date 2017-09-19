package com.sose.sample.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Sinif.
 */
@Entity
@Table(name = "sinif")
public class Sinif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "adi")
    private String adi;

    @Column(name = "aciklama")
    private String aciklama;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdi() {
        return adi;
    }

    public Sinif adi(String adi) {
        this.adi = adi;
        return this;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Sinif aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sinif sinif = (Sinif) o;
        if (sinif.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sinif.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sinif{" +
            "id=" + getId() +
            ", adi='" + getAdi() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            "}";
    }
}
