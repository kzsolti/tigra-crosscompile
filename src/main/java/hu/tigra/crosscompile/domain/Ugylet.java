package hu.tigra.crosscompile.domain;

import java.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ugylet.
 */
@Entity
@Table(name = "ugylet")
public class Ugylet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ugyletszam")
    private String ugyletszam;

    @Column(name = "ugyfel")
    private String ugyfel;

    @Column(name = "tajszam")
    private String tajszam;

    @Column(name = "beerkezes_ideje")
    private LocalDate beerkezesIdeje;

    @Column(name = "melleklet_szam")
    private Integer mellekletSzam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUgyletszam() {
        return ugyletszam;
    }

    public void setUgyletszam(String ugyletszam) {
        this.ugyletszam = ugyletszam;
    }

    public String getUgyfel() {
        return ugyfel;
    }

    public void setUgyfel(String ugyfel) {
        this.ugyfel = ugyfel;
    }

    public String getTajszam() {
        return tajszam;
    }

    public void setTajszam(String tajszam) {
        this.tajszam = tajszam;
    }

    public LocalDate getBeerkezesIdeje() {
        return beerkezesIdeje;
    }

    public void setBeerkezesIdeje(LocalDate beerkezesIdeje) {
        this.beerkezesIdeje = beerkezesIdeje;
    }

    public Integer getMellekletSzam() {
        return mellekletSzam;
    }

    public void setMellekletSzam(Integer mellekletSzam) {
        this.mellekletSzam = mellekletSzam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ugylet ugylet = (Ugylet) o;
        return Objects.equals(id, ugylet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ugylet{" +
            "id=" + id +
            ", ugyletszam='" + ugyletszam + "'" +
            ", ugyfel='" + ugyfel + "'" +
            ", tajszam='" + tajszam + "'" +
            ", beerkezesIdeje='" + beerkezesIdeje + "'" +
            ", mellekletSzam='" + mellekletSzam + "'" +
            '}';
    }
}
