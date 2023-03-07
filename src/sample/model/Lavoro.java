package sample.model;

public class Lavoro {

    //private static Lavoro instance = new Lavoro();
    private int chiave;
    private String nomeLavoro;
    private String idLavoratore;
    private String nomeAzienda;
    private String luogoLavoro;
    private int retribuzioneLorda;
    private int retribuzioneGiornaliera;
    private String mansione_1;
    private String mansione_2;
    private String dataInizio;
    private String dataFine;

    public Lavoro(){}

    public Lavoro(int chiave, String nomeLavoro, String idLavoratore, String nomeAzienda, String luogoLavoro, int retribuzioneLorda,
                  int retribuzioneGiornaliera, String mansione_1, String mansione_2, String dataInizio, String dataFine) {
        this.chiave = chiave;
        this.nomeLavoro = nomeLavoro;
        this.idLavoratore = idLavoratore;
        this.nomeAzienda = nomeAzienda;
        this.luogoLavoro = luogoLavoro;
        this.retribuzioneLorda = retribuzioneLorda;
        this.retribuzioneGiornaliera = retribuzioneGiornaliera;
        this.mansione_1 = mansione_1;
        this.mansione_2 = mansione_2;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }
    /*
    public static Lavoro getInstance(){
        if(instance == null){
            return new Lavoro();
        }else{
            return instance;
        }
    }
    */

    public int getChiave() {
        return chiave;
    }

    public void setChiave(int num) {
        this.chiave = num;
    }

    public String getNomeLavoro() {
        return nomeLavoro;
    }

    public void setNomeLavoro(String nomeLavoro) {
        this.nomeLavoro = nomeLavoro;
    }

    public String getIdLavoratore() {
        return idLavoratore;
    }

    public void setIdLavoratore(String idLavoratore) {
        this.idLavoratore = idLavoratore;
    }

    public String getMansione_1() {
        return mansione_1;
    }
    public void setMansione_1(String mansione_1) {
        this.mansione_1 = mansione_1;
    }

    public String getMansione_2() {
        return mansione_2;
    }
    public void setMansione_2(String mansione_2) {
        this.mansione_2 = mansione_2;
    }

    public String getNomeAzienda() {
        return nomeAzienda;
    }
    public void setNomeAzienda(String nomeAzienda) {
        this.nomeAzienda = nomeAzienda;
    }

    public String getLuogoLavoro(){return luogoLavoro;}
    public void setLuogoLavoro(String luogoLavoro){this.luogoLavoro = luogoLavoro;}

    public int getRetribuzioneLorda(){return retribuzioneLorda;}
    public void setRetribuzioneLorda(int retribuzioneLorda) {
        this.retribuzioneLorda = retribuzioneLorda;
    }

    public int getRetribuzioneGiornaliera(){return retribuzioneGiornaliera;}
    public void setRetribuzioneGiornaliera(int retribuzioneGiornaliera) {
        this.retribuzioneGiornaliera = retribuzioneGiornaliera;
    }

    public String getDataInizio() {
        return dataInizio;
    }
    public void setDataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }
    public void setDataFine(String dataFine) {
        this.dataFine = dataFine;
    }

}
