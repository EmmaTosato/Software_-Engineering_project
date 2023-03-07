package sample.model;

public class Dipendente{

    public String nomeDipendente;
    public String cognomeDipendente;
    public String mailDipendente;
    public String telefonoDipendente;
    public String dataNascitaDipendente;
    public String luogoNascitaDipendente;
    private String login;
    private String password;


    public Dipendente() {
    }

    public String getNomeDipendente() {
        return nomeDipendente;
    }

    public void setNomeDipendente(String nomeDipendente) {
        this.nomeDipendente = nomeDipendente;
    }

    public String getCognomeDipendente() {
        return cognomeDipendente;
    }

    public void setCognomeDipendente(String cognomeDipendente) {
        this.cognomeDipendente = cognomeDipendente;
    }

    public String getMailDipendente() {
        return mailDipendente;
    }

    public void setMailDipendente(String mailDipendente) {
        this.mailDipendente = mailDipendente;
    }

    public String getTelefonoDipendente() {
        return telefonoDipendente;
    }

    public void setTelefonoDipendente(String telefonoDipendente) {
        this.telefonoDipendente = telefonoDipendente;
    }

    public String getDataNascitaDipendente() {
        return dataNascitaDipendente;
    }

    public void setDataNascitaDipendente(String dataNascitaDipendente) {this.dataNascitaDipendente = dataNascitaDipendente; }

    public String getLuogoNascitaDipendente() {
        return luogoNascitaDipendente;
    }

    public void setLuogoNascitaDipendente(String luogoNascitaDipendente) {
        this.luogoNascitaDipendente = luogoNascitaDipendente;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String login) {
        this.password = password;
    }

}
