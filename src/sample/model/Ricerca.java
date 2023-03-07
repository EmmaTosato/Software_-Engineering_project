package sample.model;

import java.util.ArrayList;
import java.util.List;

public class Ricerca {

    private List<String> ricercaLavoratori;
    private List<String> ricercaLingue;
    private List<String> ricercaMansioni;
    private List<String> ricercaLuoghiResidenza;
    private List<String> ricercaPatenti;
    private List<String> ricercaAutomuniti;
    private List<String> ricercaPeriodoInizio;
    private List<String> ricercaPeriodoFine;
    private List<String> tipoParametro;


    public Ricerca(){
        this.ricercaLavoratori = new ArrayList<>();
        this.ricercaLingue = new ArrayList<>();
        this.ricercaMansioni = new ArrayList<>();
        this.ricercaLuoghiResidenza = new ArrayList<>();
        this.ricercaPatenti = new ArrayList<>();
        this.ricercaAutomuniti = new ArrayList<>();
        this.ricercaPeriodoInizio = new ArrayList<>();
        this.ricercaPeriodoFine = new ArrayList<>();
        this.tipoParametro = new ArrayList<>();
    }


    public void setRicercaLavoratori(String lavoratore) {
        this.ricercaLavoratori.add(lavoratore);
    }

    public List<String> getRicercaLavoratori() {
        return ricercaLavoratori;
    }

    public void setRicercaLingue(String lingua) {
        this.ricercaLingue.add(lingua);
    }

    public List<String> getRicercaLingue() {
        return ricercaLingue;
    }

    public void setRicercaMansioni(String mansione) {
        this.ricercaMansioni.add(mansione);
    }

    public List<String> getRicercaMansioni() {
        return ricercaMansioni;
    }

    public void setRicercaLuoghiResidenza(String luogo) {
        this.ricercaLuoghiResidenza.add(luogo);
    }

    public List<String> getRicercaLuoghiResidenza() {
        return ricercaLuoghiResidenza;
    }

    public void setRicercaPatenti(String patente) {
        this.ricercaPatenti.add(patente) ;
    }

    public List<String> getRicercaPatenti() {
        return ricercaPatenti;
    }

    public void setRicercaAutomuniti(String automunito) {
        this.ricercaAutomuniti.add(automunito) ;
    }

    public List<String> getRicercaAutomuniti() {
        return ricercaAutomuniti;
    }

    public void setRicercaPeriodoInizio(String periodoInizio) {
        this.ricercaPeriodoInizio.add(periodoInizio) ;
    }

    public List<String> getRicercaPeriodoInizio() {
        return ricercaPeriodoInizio;
    }

    public void setRicercaPeriodoFine(String periodoFine) {
        this.ricercaPeriodoFine.add(periodoFine) ;
    }

    public List<String> getRicercaPeriodoFine() {
        return ricercaPeriodoFine;
    }

    public void setTipoParametro(String tipo){
        this.tipoParametro.add(tipo);
    }

    public List<String> getTipoParametro() {
        return tipoParametro;
    }

}

