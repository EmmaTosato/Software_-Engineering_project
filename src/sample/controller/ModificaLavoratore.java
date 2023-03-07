package sample.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import sample.Main;
import sample.model.dao.LavoratoreDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.model.Lavoratore;
import org.controlsfx.control.CheckComboBox;
import sample.utils.DatabaseConnection;

import static java.lang.Integer.parseInt;
import java.time.LocalDate;
import java.time.format.*;


public class ModificaLavoratore implements Initializable {

    /* VARIABILI JAVAFX */
    // CONTROLS CONTAINERS
    @FXML
    private TextField Nome;
    @FXML
    private TextField Cognome;
    @FXML
    private TextField Mail;
    @FXML
    private TextField Telefono;
    @FXML
    private DatePicker DataNascita;
    @FXML
    private TextField LuogoNascita;
    @FXML
    private TextField Via;
    @FXML
    private TextField NumeroCivico;
    @FXML
    private TextField Paese;
    @FXML
    private TextField Nazionalità;
    @FXML
    private CheckComboBox<String> Esperienza;
    @FXML
    private CheckComboBox<String> Lingua;
    @FXML
    private ComboBox Patente;
    @FXML
    private CheckBox Automunito;
    @FXML
    private TextField dataInizioDisponibilità_comune1;
    @FXML
    private TextField dataFineDisponibilità_comune1;
    @FXML
    private TextField dataInizioDisponibilità_comune2;
    @FXML
    private TextField dataFineDisponibilità_comune2;
    @FXML
    private TextField Comune1;
    @FXML
    private TextField Comune2;
    @FXML
    private TextField NomeEmergenza;
    @FXML
    private TextField CognomeEmergenza;
    @FXML
    private TextField MailEmergenza;
    @FXML
    private TextField TelefonoEmergenza;

    // ETICHETTE
    @FXML
    private Label errNome;
    @FXML
    private Label errCognome;
    @FXML
    private Label errMail;
    @FXML
    private Label errTelefono;
    @FXML
    private Label errDataNascita;
    @FXML
    private Label errLuogoNascita;
    @FXML
    private Label errVia;
    @FXML
    private Label errNumeroCivico;
    @FXML
    private Label errPaese;
    @FXML
    private Label errNazionalità;
    @FXML
    private Label errEsperienza;
    @FXML
    private Label errLingua;
    @FXML
    private Label errPatente;
    @FXML
    private Label errDataInizioDisponibilità_comune1;
    @FXML
    private Label errDataFineDisponibilità_comune1;
    @FXML
    private Label errDataInizioDisponibilità_comune2;
    @FXML
    private Label errDataFineDisponibilità_comune2;
    @FXML
    private Label errNomeEmergenza;
    @FXML
    private Label errCognomeEmergenza;
    @FXML
    private Label errMailEmergenza;
    @FXML
    private Label errTelefonoEmergenza;
    @FXML
    private Label errComune1;
    @FXML
    private Label errComune2;
    @FXML
    private Label lavoratoreScelto;
    @FXML
    private Button Registra;
    @FXML
    private Button GoBack;


    private Lavoratore lavoratore = new Lavoratore();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inizializziamo i dati per le ComboBox
        try {
            setComboBoxData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void visualizzaLavoratore (String idLavoratore){
        lavoratoreScelto.setText(idLavoratore);

        // Ottengo il lavoratore scelto
        LavoratoreDaoImpl lavoratoreDao = new LavoratoreDaoImpl();
        setCampi(lavoratoreDao.getLavoratore(idLavoratore));
    }



    public void setCampi(Lavoratore lavoratore){
        Nome.setText(lavoratore.getNome());
        Cognome.setText(lavoratore.getCognome());
        Mail.setText(lavoratore.getMail());
        Telefono.setText(lavoratore.getTelefono());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = lavoratore.getDataNascita();
        LocalDate localDate = LocalDate.parse(date, formatter);
        DataNascita.setValue(localDate);

        LuogoNascita.setText(lavoratore.getLuogoNascita());
        Via.setText(lavoratore.getVia());
        NumeroCivico.setText(lavoratore.getNumeroCivico());
        Paese.setText(lavoratore.getPaese());
        Nazionalità.setText(lavoratore.getNazionalità());
        Patente.getSelectionModel().select(lavoratore.getPatente());
        if (lavoratore.getAutomunito().equals("Automunito")){
            Automunito.setSelected(true);
        }
        else{
            Automunito.setSelected(false);
        }
        for(String esperienza: lavoratore.getEsperienze()){
            Esperienza.getCheckModel().check(esperienza);
        }
        for(String lingua: lavoratore.getLingue()){
            Lingua.getCheckModel().check(lingua);
        }
        dataInizioDisponibilità_comune1.setText(lavoratore.getDataInizioDisponibilità_comune1());
        dataFineDisponibilità_comune1.setText(lavoratore.getDataFineDisponibilità_comune1());
        Comune1.setText(lavoratore.getComune1());
        dataInizioDisponibilità_comune2.setText(lavoratore.getDataInizioDisponibilità_comune2());
        dataFineDisponibilità_comune2.setText(lavoratore.getDataFineDisponibilità_comune2());
        Comune2.setText(lavoratore.getComune2());
        NomeEmergenza.setText(lavoratore.getNomeEmergenza());
        CognomeEmergenza.setText(lavoratore.getCognomeEmergenza());
        MailEmergenza.setText(lavoratore.getMailEmergenza());
        TelefonoEmergenza.setText(lavoratore.getTelEmergenza());

    };



    /* POPOLA LE COMBOBOX E LE CHECKBOX */
    public void setComboBoxData() throws SQLException {
        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        // Queries
        String queryEsperienze = "SELECT * FROM ESPERIENZE ORDER BY esperienza ASC";
        String queryPatente = "SELECT * FROM PATENTE";
        String queryLingue = "SELECT * FROM LINGUE ORDER BY lingua ASC";

        try {
            // Per visualizzare i dati a lista
            ResultSet rs1 = connectDB.createStatement().executeQuery(queryEsperienze);
            ResultSet rs2 = connectDB.createStatement().executeQuery(queryPatente);
            ResultSet rs4 = connectDB.createStatement().executeQuery(queryLingue);
            ObservableList data1 = FXCollections.observableArrayList();
            ObservableList data2 = FXCollections.observableArrayList();
            ObservableList data4 = FXCollections.observableArrayList();


            while(rs1.next()){
                data1.add(new String(rs1.getString("esperienza")));
            }
            Esperienza.getItems().addAll(data1);

            while(rs2.next()){
                data2.add(new String(rs2.getString("patente")));
            }
            Patente.setItems(data2);

            while(rs4.next()){
                data4.add(new String(rs4.getString("lingua")));
            }
            Lingua.getItems().addAll(data4);


        } catch (SQLException e) {
            Logger.getLogger(ModificaLavoratore.class.getName()).log(Level.SEVERE,null, e);
            e.printStackTrace();
        }
    }


    // Metodo per il controllo del numero civico e del numero di telefono: controllo che le stringhe inserite siano numeri
    public boolean soloNumeri(String stringa) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(stringa);

        return m.matches();
    }

    //Controllo che una stringa sia formata solo da lettere, maiuscole e minuscole e spazi
    public boolean soloLettere(String stringa) {
        char ch;

        for(int i = 0 ; i < stringa.length() ; i++){
            ch = stringa.charAt(i);
            if(ch != ' ') {
                if (ch != 'à' && ch != 'é' && ch != 'è' && ch != 'ù' && ch != 'ò') {
                    if (ch < 'a' || ch > 'z') {
                        if (ch < 'A' || ch > 'Z') {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /* CONTROLLI SUI DATI INSERITI */
    public boolean controllaAutomunitoEPatente(boolean automunito, String patente){
        // Se ho selezionato che l'utente ha una patente
        if (automunito == true) {
            // Ma non ha selezionato il tipo oppure ho selezionato una patente che non preveda la guida della macchina
            if (patente.compareTo("null") == 0 || patente.compareTo("A1") == 0 || patente.compareTo("A") == 0 || patente.compareTo("-Nessuna-") == 0) {
                // Allora ritorno falso
                return false;
            }
        }
        return true;
    }

    // Metodo per controllare l'inserimento della data
    public boolean getData(LocalDate miaData){
        LocalDate oggi =  LocalDate.now();

        if (miaData != null ) {// Se non si è inserito niente, ritorna subito false
            // Conversione ad interi
            int giornoNascita = miaData.getDayOfMonth();
            int meseNascita = miaData.getMonthValue();
            int annoNascita = miaData.getYear();

            int giornoOggi = oggi.getDayOfMonth();
            int meseOggi = oggi.getMonthValue();
            int annoOggi = oggi.getYear();

            // Controllo per DATE FUTURE
            if(annoNascita > annoOggi){
                return false;
            }else if(annoNascita == annoOggi && meseNascita > meseOggi){
                return false;
            }else if (annoNascita == annoOggi && meseNascita == meseOggi && giornoNascita > giornoOggi){
                return false;
            }

            // Controllo per DATE PASSATE
            int controlAnno = annoOggi - annoNascita;
            int controlMese = meseOggi - meseNascita;
            int controlGiorno = giornoOggi - giornoNascita;

            //boolean isMagg = controlAnno > 16 || (controlAnno == 16 && (controlMese > 0 || (controlMese == 0 && controlGiorno >= 0)));
            if(controlAnno > 16){
                return true;
            }else if(controlAnno == 16 && controlMese > 0){
                return true;
            }else if (controlGiorno >= 0){
                return true;
            }

            return false;

            //return isMagg;
        }
        return false;
    }


    //Controllo se la mail contiene i caratteri . @ il servizio di posta it/com => devono essere 4
    public boolean controllaMail(String stringa) {
        String[] permesse = {"com", "it", ".", "@"};
        int cont = 0;


        for(int i = 0 ; i < permesse.length ; i++){
            String s = permesse[i];
            if (stringa.contains(s)) {
                cont++;
            }
        }

        return cont == 3;
    }

    // Controllo se la lunghezza è minore di 15 cifre
    public boolean controllaTelefono(String stringa) {
        char ch;
        if(stringa.length() == 0){
            return true;
        }
        if(stringa.length() != 10){
            return false;
        }

        return soloNumeri(stringa);
    }

    public boolean controllaCivico(String stringa) {
        char ch;

        if(stringa.length() > 6){
            return false;
        }

        return true;
    }


    public String convertiData(LocalDate miaData) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedString = miaData.format(formatter);
        return formattedString;
    }

    public String convertiAutomunito(boolean automunito) {
        String stringa ;

        if (automunito == true) {
            stringa ="Automunito";
        }
        else{
            stringa ="Non automunito";
        }
        return stringa;
    }


    public void tornaIndietro(ActionEvent actionEvent)throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("view/AggiornamentoLavoratori.fxml");
    }


    public String upperCaseFirst(String val) {
        val.toLowerCase();
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }

    public boolean controlloLunghezzaData(String stringa) {
        int lunghezza = stringa.length();
        if (lunghezza == 7) {
            return true;
        }
        return false;
    }

    public boolean controlloSeparazione(String stringa) {
        String struttura = stringa.substring(2, 3);
        String confronto = "/";
        int result = struttura.compareTo(confronto);
        if (result == 0) {
            return true;
        }
        return false;
    }

    public boolean controlloSoloNumeriData(String stringa) {
        char[] data = stringa.toCharArray();
        for (int i = 0; i < stringa.length(); i++) {
            if (i == 2) {
                continue;
            } else {
                String temp = Character.toString(data[i]);
                if (soloNumeri(temp)) {
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean controlloMesiAnni(String stringa) {
        LocalDate oggi = LocalDate.now();
        int annoOggi = oggi.getYear();
        int meseOggi = oggi.getMonthValue();

        String meseStringa = stringa.substring(0, 2);
        String annoStringa = stringa.substring(3);
        int mese = Integer.parseInt(meseStringa);
        int anno = Integer.parseInt(annoStringa);
        if (mese > 12 || mese < 0) {
            return false;
        }
        if (anno < annoOggi) {
            return false;
        }

        if (anno == annoOggi){
            if(mese < meseOggi){
                return false;
            }
        }
        return true;
    }

    public boolean controlloDate(String data_1, String data_2) {
        if (data_1.length() != 0 && data_2.length() != 0){
            String meseStringa_1 = data_1.substring(0, 2);
            String annoStringa_1 = data_1.substring(3);
            int mese_1 = Integer.parseInt(meseStringa_1);
            int anno_1 = Integer.parseInt(annoStringa_1);
            String meseStringa_2 = data_2.substring(0, 2);
            String annoStringa_2 = data_2.substring(3);
            int mese_2 = Integer.parseInt(meseStringa_2);
            int anno_2 = Integer.parseInt(annoStringa_2);

            if (anno_2 > anno_1) {
                return true;
            }
            if (anno_2 == anno_1) {
                if (mese_2 >= mese_1) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean controlloDatePerComuni(String data_1, String data_2, String comune_1, String comune_2){
        int result = comune_1.compareTo(comune_2);
        if(result != 0){
            return true;
        }
        if ((data_1.length() != 0) && (data_2.length() != 0)){
            String meseStringa_1 = data_1.substring(0, 2);
            String annoStringa_1 = data_1.substring(3);
            int mese_1 = Integer.parseInt(meseStringa_1);
            int anno_1 = Integer.parseInt(annoStringa_1);
            String meseStringa_2 = data_2.substring(0, 2);
            String annoStringa_2 = data_2.substring(3);
            int mese_2 = Integer.parseInt(meseStringa_2);
            int anno_2 = Integer.parseInt(annoStringa_2);

            if (anno_2 > anno_1) {
                return true;
            }
            if (anno_2 == anno_1) {
                if (mese_2 > mese_1) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean controlloComuniInLista(String comune) throws FileNotFoundException {
        File getCSVFiles = new File("/Users/emmatosato/Documents/UNI/UNI 3/Locale Ingegneria del software/ProgettoIng/src/sample/utils/comuni.csv");
        Scanner sc = new Scanner(getCSVFiles);
        String temp;
        int result = 1;
        while (sc.hasNext())
        {
            temp = sc.nextLine();
            result = temp.compareTo(comune);
            if(result == 0){
                return true;
            }
        }
        sc.close();
        return false;
    }


    public boolean controlloNazioniLista(String nazione) throws FileNotFoundException {
        File getCSVFiles = new File("/Users/emmatosato/Documents/UNI/UNI 3/Locale Ingegneria del software/ProgettoIng/src/sample/utils/nazionalità.csv");
        Scanner sc = new Scanner(getCSVFiles);

        String temp;
        int result = 1;

        while (sc.hasNext())
        {
            temp = sc.nextLine();
            if(temp.equals(nazione)){
                return true;
            }
        }
        sc.close();
        return false;
    }

    public void creazioneAnagrafica(ActionEvent actionEvent)throws IOException{
        Main nuovaScena = new Main();

        // Gestione errori
        if( Nome.getText().isBlank() || !soloLettere(Nome.getText()) || Cognome.getText().isBlank() ||
                !soloLettere(Cognome.getText()) || Mail.getText().isBlank() || !controllaMail(Mail.getText()) ||
                !controllaTelefono(Telefono.getText()) ||
                !getData(DataNascita.getValue()) || LuogoNascita.getText().isBlank() ||
                !soloLettere(LuogoNascita.getText()) || Paese.getText().isBlank() ||
                Via.getText().isBlank() || !soloLettere(Via.getText()) || NumeroCivico.getText().isBlank() ||
                !controllaCivico(NumeroCivico.getText()) || Nazionalità.getText().isBlank() ||
                !controlloNazioniLista(Nazionalità.getText()) || Esperienza.getCheckModel().isEmpty() ||
                Lingua.getCheckModel().isEmpty() || Patente.getSelectionModel().isEmpty() ||
                !controllaAutomunitoEPatente(Automunito.isSelected(), Patente.getSelectionModel().getSelectedItem().toString()) ||
                Comune1.getText().isBlank()  || Comune2.getText().isBlank() ||
                !controlloComuniInLista(Comune1.getText()) || !controlloComuniInLista(Comune1.getText()) ||
                !controlloComuniInLista(Paese.getText()) ||
                NomeEmergenza.getText().isBlank() || !soloLettere(NomeEmergenza.getText()) ||
                CognomeEmergenza.getText().isBlank() || !soloLettere(CognomeEmergenza.getText()) ||
                MailEmergenza.getText().isBlank() || !controllaMail(MailEmergenza.getText()) ||
                TelefonoEmergenza.getText().isBlank() || !controllaTelefono(TelefonoEmergenza.getText()) ||
                dataInizioDisponibilità_comune1.getText().isBlank() ||
                !controlloSeparazione(dataInizioDisponibilità_comune1.getText()) ||
                !controlloLunghezzaData(dataInizioDisponibilità_comune1.getText()) ||
                !controlloSoloNumeriData(dataInizioDisponibilità_comune1.getText()) ||
                !controlloMesiAnni(dataInizioDisponibilità_comune1.getText()) ||
                !controlloDate(dataInizioDisponibilità_comune1.getText(), dataFineDisponibilità_comune1.getText()) ||
                dataFineDisponibilità_comune1.getText().isBlank() ||
                !controlloSeparazione(dataFineDisponibilità_comune1.getText()) ||
                !controlloLunghezzaData(dataFineDisponibilità_comune1.getText())
                || !controlloSoloNumeriData(dataFineDisponibilità_comune1.getText()) ||
                !controlloMesiAnni(dataFineDisponibilità_comune1.getText()) ||
                dataInizioDisponibilità_comune2.getText().isBlank() ||
                !controlloSeparazione(dataInizioDisponibilità_comune2.getText()) ||
                !controlloLunghezzaData(dataInizioDisponibilità_comune2.getText()) ||
                !controlloSoloNumeriData(dataInizioDisponibilità_comune2.getText()) ||
                !controlloMesiAnni(dataInizioDisponibilità_comune2.getText()) ||
                !controlloDate(dataInizioDisponibilità_comune2.getText(), dataFineDisponibilità_comune2.getText()) ||
                dataFineDisponibilità_comune2.getText().isBlank() ||
                !controlloSeparazione(dataFineDisponibilità_comune2.getText()) ||
                !controlloLunghezzaData(dataFineDisponibilità_comune2.getText())
                || !controlloSoloNumeriData(dataFineDisponibilità_comune2.getText()) ||
                !controlloMesiAnni(dataFineDisponibilità_comune2.getText()) ||
                !controlloDatePerComuni(dataFineDisponibilità_comune1.getText(), dataInizioDisponibilità_comune2.getText(), Comune1.getText(), Comune2.getText())
        ){
            if(Nome.getText().isBlank() || !soloLettere(Nome.getText())){
                errNome.setText("Campo Errato");
                Nome.setText("");
            }else{
                errNome.setText("");
            }
            if(Cognome.getText().isBlank() || !soloLettere(Cognome.getText())){
                errCognome.setText("Campo Errato");
                Cognome.setText("");
            }else{
                errCognome.setText("");
            }
            if(Mail.getText().isBlank() || !controllaMail(Mail.getText())){
                errMail.setText("Campo Errato. Richieste :@, . , it o com");
                Mail.setText("");
            }else{
                errMail.setText("");
            }
            if(!controllaTelefono(Telefono.getText())){
                errTelefono.setText("Campo Errato: 10 cifre richieste");
                Telefono.setText("");
            }else {
                errTelefono.setText("");
            }
            //Se il metodo mi ritorna false (< di 16 anni o vuoto) io entro nell'if grazie al negato
            if(  !getData(DataNascita.getValue()) ){
                errDataNascita.setText("Età >= 16 anni");
            }else {
                errDataNascita.setText("");
            }
            if( LuogoNascita.getText().isBlank() || !soloLettere(LuogoNascita.getText()) ){
                errLuogoNascita.setText("Campo Errato");
                LuogoNascita.setText("");
            }else {
                errLuogoNascita.setText("");
            }
            if(Via.getText().isBlank() || !soloLettere(Via.getText())){
                errVia.setText("Campo Errato");
                Via.setText("");
            }else{
                errVia.setText("");
            }
            if(NumeroCivico.getText().isBlank() || !controllaCivico(NumeroCivico.getText())){
                errNumeroCivico.setText("Campo Errato. Max 6 cifre");
                NumeroCivico.setText("");
            }else{
                errNumeroCivico.setText("");
            }
            if(Paese.getText().isBlank() ||  !controlloComuniInLista(Paese.getText())){
                errPaese.setText("Campo Errato o comune non esistente");
                Paese.setText("");
            }else{
                errPaese.setText("");
            }
            if(Nazionalità.getText().isBlank() || !controlloNazioniLista(Nazionalità.getText())){
                errNazionalità.setText("Campo Errato: inserire uno stato esistente");
                Nazionalità.setText("");
            }else{
                errNazionalità.setText("");
            }
            if (Esperienza.getCheckModel().isEmpty()) {
                errEsperienza.setText("Seleziona almeno una voce");
            }else{
                errEsperienza.setText("");
            }
            if(Lingua.getCheckModel().isEmpty()){
                errLingua.setText("Seleziona almeno una voce");
            }else{
                errLingua.setText("");
            }
            if (Patente.getSelectionModel().isEmpty() || !controllaAutomunitoEPatente(Automunito.isSelected(), (String) Patente.getSelectionModel().getSelectedItem())) {
                errPatente.setText("Non hai selezionato una voce oppure hai selezionato una patente non idonea");
                Patente.getSelectionModel().clearSelection();
            }else{
                errPatente.setText("");
            }
            if (Comune1.getText().isBlank()  || !controlloComuniInLista(Comune1.getText())) {
                errComune1.setText("Campo errato o comune non esistente");
            }else{
                errComune1.setText("");
            }
            if ( Comune2.getText().isBlank() || !controlloComuniInLista(Comune2.getText())) {
                errComune2.setText("Campo errato o comune non esistente");
            }else{
                errComune2.setText("");
            }
            if(NomeEmergenza.getText().isBlank() || !soloLettere(NomeEmergenza.getText())){
                errNomeEmergenza.setText("Campo Errato");
                NomeEmergenza.setText("");
            }else{
                errNomeEmergenza.setText("");
            }
            if(CognomeEmergenza.getText().isBlank() || !soloLettere(CognomeEmergenza.getText())){
                errCognomeEmergenza.setText("Campo Errato");
                CognomeEmergenza.setText("");
            }else{
                errCognomeEmergenza.setText("");
            }
            if(MailEmergenza.getText().isBlank() || !controllaMail(MailEmergenza.getText())){
                errMailEmergenza.setText("Campo Errato");
                MailEmergenza.setText("");
            }else{
                errMailEmergenza.setText("");
            }
            if(TelefonoEmergenza.getText().isBlank() || !controllaTelefono(TelefonoEmergenza.getText())){
                errTelefonoEmergenza.setText("Campo Errato. Deve essere di 10 cifre");
                TelefonoEmergenza.setText("");
            }else {
                errTelefonoEmergenza.setText("");
            }
            // Controllo COMUNE1
            if(dataInizioDisponibilità_comune1.getText().isBlank() || !controlloSeparazione(dataInizioDisponibilità_comune1.getText()) ||
                    !controlloLunghezzaData(dataInizioDisponibilità_comune1.getText()) || !controlloSoloNumeriData(dataInizioDisponibilità_comune1.getText()) ||
                    !controlloMesiAnni(dataInizioDisponibilità_comune1.getText())){
                errDataInizioDisponibilità_comune1.setText("Campo errato");
                dataInizioDisponibilità_comune1.setText("");
            }else{
                errDataInizioDisponibilità_comune1.setText("");
            }
            if(dataFineDisponibilità_comune1.getText().isBlank() || !controlloSeparazione(dataFineDisponibilità_comune1.getText()) ||
                    !controlloLunghezzaData(dataFineDisponibilità_comune1.getText()) || !controlloSoloNumeriData(dataFineDisponibilità_comune1.getText()) ||
                    !controlloMesiAnni(dataFineDisponibilità_comune1.getText())|| !controlloDate(dataInizioDisponibilità_comune1.getText(), dataFineDisponibilità_comune1.getText())){
                errDataFineDisponibilità_comune1.setText("Campo errato");
                dataFineDisponibilità_comune1.setText("");
            }else{
                errDataFineDisponibilità_comune1.setText("");
            }
            // Controllo COMUNE2
            if (dataInizioDisponibilità_comune2.getText().isBlank() || !controlloSeparazione(dataInizioDisponibilità_comune2.getText()) ||
                    !controlloLunghezzaData(dataInizioDisponibilità_comune2.getText()) || !controlloSoloNumeriData(dataInizioDisponibilità_comune2.getText()) ||
                    !controlloMesiAnni(dataInizioDisponibilità_comune2.getText())) {
                errDataInizioDisponibilità_comune2.setText("Campo errato");
                dataInizioDisponibilità_comune2.setText("");
            } else {
                errDataInizioDisponibilità_comune2.setText("");
            }
            if (dataFineDisponibilità_comune2.getText().isBlank() || !controlloSeparazione(dataFineDisponibilità_comune2.getText()) ||
                    !controlloLunghezzaData(dataFineDisponibilità_comune2.getText()) || !controlloSoloNumeriData(dataFineDisponibilità_comune2.getText()) ||
                    !controlloMesiAnni(dataFineDisponibilità_comune2.getText()) || !controlloDate(dataInizioDisponibilità_comune2.getText(), dataFineDisponibilità_comune2.getText())) {
                errDataFineDisponibilità_comune2.setText("Campo errato");
                dataFineDisponibilità_comune2.setText("");
            } else {
                errDataFineDisponibilità_comune2.setText("");
            }
            // Controllo data finale comune 1 e data iniziale comune 2 (periodo comune1 ANTECEDENTE comune 2)
            if (!controlloDatePerComuni(dataFineDisponibilità_comune1.getText(), dataInizioDisponibilità_comune2.getText(), Comune1.getText(), Comune2.getText())) {
                errDataFineDisponibilità_comune2.setText("Sovrapposizione di periodi per\nlo stesso comune o data comune 1\nsuccessica a data comune 2");
                dataFineDisponibilità_comune2.setText("");
                dataInizioDisponibilità_comune2.setText("");
            } else {
                errDataFineDisponibilità_comune2.setText("");
            }

        }
        else {
            List<String> listEsperienze =  Esperienza.getCheckModel().getCheckedItems();
            List<String> listLingue =  Lingua.getCheckModel().getCheckedItems();


            // Risetto i dati per passare l'oggetto
            lavoratore.setIdLavoratore(lavoratoreScelto.getText());
            lavoratore.setNome(Nome.getText());
            lavoratore.setCognome(Cognome.getText());
            lavoratore.setMail(Mail.getText());
            lavoratore.setTelefono(Telefono.getText());
            lavoratore.setDataNascita(convertiData(DataNascita.getValue()));
            lavoratore.setLuogoNascita(LuogoNascita.getText());
            lavoratore.setVia(Via.getText());
            lavoratore.setNumeroCivico(NumeroCivico.getText());
            lavoratore.setPaese(Paese.getText());
            lavoratore.setNazionalità(Nazionalità.getText());
            lavoratore.setEsperienze(listEsperienze);
            lavoratore.setLingue(listLingue);
            lavoratore.setPatente(Patente.getSelectionModel().getSelectedItem().toString());
            lavoratore.setAutomunito(convertiAutomunito(Automunito.isSelected()));
            lavoratore.setComune1(Comune1.getText());
            lavoratore.setComune2(Comune2.getText());
            lavoratore.setDataInizioDisponibilità_comune1(dataInizioDisponibilità_comune1.getText());
            lavoratore.setDataFineDisponibilità_comune1(dataFineDisponibilità_comune1.getText());
            lavoratore.setDataInizioDisponibilità_comune2(dataInizioDisponibilità_comune2.getText());
            lavoratore.setDataFineDisponibilità_comune2(dataFineDisponibilità_comune2.getText());
            lavoratore.setNomeEmergenza(NomeEmergenza.getText());
            lavoratore.setCognomeEmergenza(CognomeEmergenza.getText());
            lavoratore.setMailEmergenza(MailEmergenza.getText());
            lavoratore.setTelEmergenza(TelefonoEmergenza.getText());


            // Chiamo il DAO per l'update
            LavoratoreDaoImpl lavoratoreDao = new LavoratoreDaoImpl();
            lavoratoreDao.updateLavoratore(lavoratore);


            nuovaScena.changeScene("view/HomePage.fxml");
        }
    }
}
