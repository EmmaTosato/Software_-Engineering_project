package sample.controller;

import sample.Main;
import sample.model.dao.LavoroDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.model.Lavoro;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModificaLavoro {
    @FXML
    private TextField nomeAzienda;
    @FXML
    private Label errNomeAzienda;
    @FXML
    private TextField luogoLavoro;
    @FXML
    private Label errLuogoLavoro;
    @FXML
    private TextField retribuzioneLorda;
    @FXML
    private Label errRetribuzioneLorda;
    @FXML
    private TextField retribuzioneGiornaliera;
    @FXML
    private Label errRetribuzioneGiornaliera;
    @FXML
    private TextField insLavoro;
    @FXML
    private Label errInsLavoro;
    @FXML
    private TextField mansione_1;
    @FXML
    private Label errMansione_1;
    @FXML
    private TextField mansione_2;
    @FXML
    private Label errMansione_2;
    @FXML
    private TextField data_inizio;
    @FXML
    private Label errDataInizio;
    @FXML
    private TextField data_fine;
    @FXML
    private Label errDataFine;
    @FXML
    private Label lavoratoreScelto;
    @FXML
    private Label errLavoratoreScelto;
    @FXML
    private Button confermaAgg;

    @FXML
    private Button goBack;
    @FXML
    private MenuButton menuButtonLavori;


    private Lavoro lavoro = new Lavoro();
    int chiave;


    public void visualizzaLavoratore (String idLavoratore){
        lavoratoreScelto.setText(idLavoratore);
        // Inseriamo tutti i lavori nel menu
        LavoroDaoImpl lavoroDao = new LavoroDaoImpl();
        setMenu(lavoroDao.getAllLavori(idLavoratore));
    }


    public void setMenu(List<Lavoro> listaLavori) {
        if( listaLavori.isEmpty() ){
            MenuItem menuItem = new MenuItem("Lista vuota");
            menuButtonLavori.getItems().add(menuItem);
        }

        for (int i = 0; i < listaLavori.size(); i++) {
            MenuItem menuItem = new MenuItem(listaLavori.get(i).getNomeLavoro()+ " " + listaLavori.get(i).getDataInizio() + " " + listaLavori.get(i).getDataFine());
            menuButtonLavori.getItems().add(menuItem);

            Lavoro lavoroScelto = listaLavori.get(i);
            int chiaveTemp = listaLavori.get(i).getChiave();

            menuItem.setOnAction(event -> {
                chiave = chiaveTemp;
                setCampi(lavoroScelto);

            });
        }
    }

    public void setCampi(Lavoro lavoroScelto){
        insLavoro.setText(lavoroScelto.getNomeLavoro());
        nomeAzienda.setText(lavoroScelto.getNomeAzienda());
        luogoLavoro.setText(lavoroScelto.getLuogoLavoro());
        retribuzioneLorda.setText(String.valueOf(lavoroScelto.getRetribuzioneLorda()));
        retribuzioneGiornaliera.setText(String.valueOf(lavoroScelto.getRetribuzioneGiornaliera()));
        mansione_1.setText(lavoroScelto.getMansione_1());
        mansione_2.setText(lavoroScelto.getMansione_2());
        data_inizio.setText(lavoroScelto.getDataInizio());
        data_fine.setText(lavoroScelto.getDataFine());

    };


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

    public boolean soloNumeri(String stringa) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(stringa);
        return m.matches();
    }

    public boolean maggioreZero(String stringa) {
        int valore = Integer.parseInt(stringa);
        if (valore > 0) {
            return true;
        }
        return false;
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
        int annoCinqueFa = annoOggi - 5;
        String meseStringa = stringa.substring(0, 2);
        String annoStringa = stringa.substring(3);
        int mese = Integer.parseInt(meseStringa);
        int anno = Integer.parseInt(annoStringa);
        if (mese > 12 || mese < 0) {
            return false;
        }
        if (anno < annoCinqueFa) {
            return false;
        }
        if(anno > annoOggi){
            return false;
        }
        if(anno == annoOggi){
            if(mese > meseOggi) {
                return false;
            }
        }
        return true;
    }

    // Controllo che data2 sia maggiore di data1
    public boolean controlloDate(String data_1, String data_2) {
        if (data_1.length() != 0 && data_2.length() != 0) {
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



    public void tornaIndietro(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("view/AggiornamentoLavori.fxml");
    }


    // Gestione errori quando preme su conferma
    public void confermaInserimento(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();

        if (nomeAzienda.getText().isBlank() || !soloLettere(nomeAzienda.getText()) ||
                luogoLavoro.getText().isBlank() || !controlloComuniInLista(luogoLavoro.getText()) ||
                retribuzioneLorda.getText().isBlank() || !soloNumeri(retribuzioneLorda.getText()) ||
                !maggioreZero(retribuzioneLorda.getText()) || retribuzioneGiornaliera.getText().isBlank() ||
                !soloNumeri(retribuzioneGiornaliera.getText()) || !maggioreZero(retribuzioneGiornaliera.getText()) ||
                insLavoro.getText().isBlank() || !soloLettere(insLavoro.getText()) ||
                mansione_1.getText().isBlank() || !soloLettere(mansione_1.getText()) ||
                !soloLettere(mansione_2.getText()) || data_inizio.getText().isBlank() ||
                !controlloSeparazione(data_inizio.getText()) || !controlloLunghezzaData(data_inizio.getText()) ||
                !controlloSoloNumeriData(data_inizio.getText()) || !controlloMesiAnni(data_inizio.getText())
                || data_fine.getText().isBlank() || !controlloSeparazione(data_fine.getText()) ||
                !controlloLunghezzaData(data_fine.getText()) || !controlloSoloNumeriData(data_fine.getText()) ||
                !controlloMesiAnni(data_fine.getText()) || !controlloDate(data_inizio.getText(), data_fine.getText()) ||
                menuButtonLavori.getItems().isEmpty()
        ) {
            if (nomeAzienda.getText().isBlank() || !soloLettere(nomeAzienda.getText())) {
                errNomeAzienda.setText("Campo errato");
                nomeAzienda.setText("");
            } else {
                errNomeAzienda.setText("");
            }

            if (luogoLavoro.getText().isBlank() || !controlloComuniInLista(luogoLavoro.getText())) {
                errLuogoLavoro.setText("Campo errato");
                luogoLavoro.setText("");
            } else {
                errLuogoLavoro.setText("");
            }

            if (retribuzioneLorda.getText().isBlank() || !soloNumeri(retribuzioneLorda.getText()) ||
                    !maggioreZero(retribuzioneLorda.getText())) {
                errRetribuzioneLorda.setText("Campo errato");
                retribuzioneLorda.setText("");
            } else {
                errRetribuzioneLorda.setText("");
            }

            if (retribuzioneGiornaliera.getText().isBlank() || !soloNumeri(retribuzioneGiornaliera.getText()) ||
                    !maggioreZero(retribuzioneGiornaliera.getText())) {
                errRetribuzioneGiornaliera.setText("Campo errato");
                retribuzioneGiornaliera.setText("");
            } else {
                errRetribuzioneGiornaliera.setText("");
            }

            if (insLavoro.getText().isBlank() || !soloLettere(insLavoro.getText())) {
                errInsLavoro.setText("Campo errato");
                insLavoro.setText("");
            } else {
                errInsLavoro.setText("");
            }

            if (mansione_1.getText().isBlank() || !soloLettere(mansione_1.getText())) {
                errMansione_1.setText("Mancante");
                mansione_1.setText("");
            } else {
                errMansione_1.setText("");
            }

            if (!soloLettere(mansione_2.getText())) {
                errMansione_2.setText("Campo errato");
                mansione_2.setText("");
            } else {
                errMansione_2.setText("");
            }

            if (data_inizio.getText().isBlank() || !controlloSeparazione(data_inizio.getText()) ||
                    !controlloLunghezzaData(data_inizio.getText()) || !controlloSoloNumeriData(data_inizio.getText()) ||
                    !controlloMesiAnni(data_inizio.getText())) {
                errDataInizio.setText("Campo errato");
                data_inizio.setText("");
            } else {
                errDataInizio.setText("");
            }

            if (data_fine.getText().isBlank() || !controlloSeparazione(data_fine.getText()) ||
                    !controlloLunghezzaData(data_fine.getText()) || !controlloSoloNumeriData(data_fine.getText()) ||
                    !controlloMesiAnni(data_fine.getText()) || !controlloDate(data_inizio.getText(), data_fine.getText())) {
                errDataFine.setText("Campo errato");
                data_fine.setText("");
            } else {
                errDataFine.setText("");
            }
            if(menuButtonLavori.getItems().isEmpty()){
                errLavoratoreScelto.setText("Non hai selezionato nessun lavoro");
            }
            else{
                errLavoratoreScelto.setText("");
            }
        } else {

            // Risetto i dati per passare l'oggetto
            lavoro.setChiave(chiave);
            lavoro.setNomeLavoro(insLavoro.getText());
            lavoro.setIdLavoratore(lavoratoreScelto.getText());
            lavoro.setNomeAzienda(nomeAzienda.getText());
            lavoro.setLuogoLavoro(luogoLavoro.getText());
            lavoro.setRetribuzioneLorda(Integer.parseInt(retribuzioneLorda.getText()));
            lavoro.setRetribuzioneGiornaliera(Integer.parseInt(retribuzioneGiornaliera.getText()));
            lavoro.setMansione_1(mansione_1.getText());
            lavoro.setMansione_2(mansione_2.getText());
            lavoro.setDataInizio((data_inizio.getText()));
            lavoro.setDataFine((data_fine.getText()));



            // Chiamo il DAO per l'update
            LavoroDaoImpl lavoroDao = new LavoroDaoImpl();
            lavoroDao.updateLavoro(lavoro);



            nuovaScena.changeScene("view/HomePage.fxml");
        }
    }








}
