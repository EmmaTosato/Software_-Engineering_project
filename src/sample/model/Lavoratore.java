package sample.model;

import java.util.List;

public class Lavoratore{
    //private static Lavoratore instance = new Lavoratore();
    public String idLavoratore;
    public String nome;
    public String cognome;
    public String mail;
    public String telefono;
    public String dataNascita;
    public String luogoNascita;
    private String via;
    private String numeroCivico;
    private String paese;
    private String nazionalità;
    private List<String> esperienze;
    private List<String> lingue;
    private String patente;
    private String automunito;
    private String dataInizioDisponibilità_comune1;
    private String dataFineDisponibilità_comune1;
    private String dataInizioDisponibilità_comune2;
    private String dataFineDisponibilità_comune2;
    private String comune1;
    private String comune2;
    private String nomeEmergenza;
    private String cognomeEmergenza;
    private String mailEmergenza;
    private String telEmergenza;

    public Lavoratore(){

    }

    public Lavoratore(String idLavoratore, String nome, String cognome){
        this.idLavoratore = idLavoratore;
        this.nome = nome;
        this.cognome = cognome;
    }


    /*
    public static Lavoratore getInstance(){
        if(instance == null){
            return new Lavoratore();
        }else{
            return instance;
        }
    }*/

    public String getIdLavoratore() {
        return idLavoratore;
    }

    public void setIdLavoratore(String id) {
        this.idLavoratore = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {this.dataNascita = dataNascita; }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }

    public String getNazionalità() {
        return nazionalità;
    }

    public void setNazionalità(String nazionalità) {
        this.nazionalità = nazionalità;
    }

    public void setEsperienze(List<String> esperienze) {
        this.esperienze = esperienze;
    }

    public List<String> getEsperienze() {
        return esperienze;
    }

    public void setLingue(List<String> lingue) {
        this.lingue = lingue;
    }

    public List<String> getLingue() {
        return lingue;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getAutomunito(){
        return automunito;
    }

    public void setAutomunito(String automunito){
        this.automunito = automunito;
    }

    public String getComune1(){
        return comune1;
    }

    public void setComune1(String comune1){
        this.comune1 = comune1;
    }

    public String getComune2(){
        return comune2;
    }

    public void setComune2(String comune2){
        this.comune2 = comune2;
    }

    public String getDataInizioDisponibilità_comune1(){return dataInizioDisponibilità_comune1;}

    public void setDataInizioDisponibilità_comune1(String dataInizioDisponibilità_comune1){this.dataInizioDisponibilità_comune1 = dataInizioDisponibilità_comune1;}

    public String getDataFineDisponibilità_comune1(){return dataFineDisponibilità_comune1;}

    public void setDataFineDisponibilità_comune1(String dataFineDisponibilità_comune1){this.dataFineDisponibilità_comune1 = dataFineDisponibilità_comune1;}

    public String getDataInizioDisponibilità_comune2(){return dataInizioDisponibilità_comune2;}

    public void setDataInizioDisponibilità_comune2(String dataInizioDisponibilità_comune2){this.dataInizioDisponibilità_comune2 = dataInizioDisponibilità_comune2;}

    public String getDataFineDisponibilità_comune2(){return dataFineDisponibilità_comune2;}
    
    public void setDataFineDisponibilità_comune2(String dataFineDisponibilità_comune2){this.dataFineDisponibilità_comune2 = dataFineDisponibilità_comune2;}

    public String getNomeEmergenza(){
        return nomeEmergenza;
    }

    public void setNomeEmergenza(String nomeEmergenza){
        this.nomeEmergenza = nomeEmergenza;
    }

    public String getCognomeEmergenza(){
        return cognomeEmergenza;
    }

    public void setCognomeEmergenza(String cognomeEmergenza){
        this.cognomeEmergenza = cognomeEmergenza;
    }

    public String getMailEmergenza(){
        return mailEmergenza;
    }

    public void setMailEmergenza(String mailEmergenza){
        this.mailEmergenza = mailEmergenza;
    }

    public String getTelEmergenza(){
        return telEmergenza;
    }

    public void setTelEmergenza(String telEmerg){
        this.telEmergenza= telEmerg;
    }


}
