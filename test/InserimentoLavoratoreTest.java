import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.controller.InserimentoLavoratore;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InserimentoLavoratoreTest {

    @Test
    @DisplayName("Controllo ci siano solo numeri")
    void soloNumeriTest() {
        InserimentoLavoratore inserimentoLavoratore = new InserimentoLavoratore();

        boolean controlCorretto = inserimentoLavoratore.soloNumeri("124543");
        boolean controlErrato1 = inserimentoLavoratore.soloNumeri("abc123");
        boolean controlErrato2 = inserimentoLavoratore.soloNumeri("abcde");
        boolean controlErrato3 = inserimentoLavoratore.soloNumeri("sb4@?$");
        assertEquals(true, controlCorretto, "E' richiesta presenza di soli numeri");
        assertEquals(false, controlErrato1, "Dovrebbe essere richiesta presenza di soli numeri");
        assertEquals(false, controlErrato2, "Dovrebbe essere richiesta presenza di soli numeri");
        assertEquals(false, controlErrato3, "Dovrebbe essere richiesta presenza di soli numeri");

    }

    @Test
    @DisplayName("Controllo ci siano solo lettere")
    void soloLettereTest() {
        InserimentoLavoratore inserimentoLavoratore = new InserimentoLavoratore();

        boolean controlCorretto = inserimentoLavoratore.soloLettere("abcde");
        boolean controlCorretto2 = inserimentoLavoratore.soloLettere("pescòèàùé");
        boolean controlErrato1 = inserimentoLavoratore.soloLettere("abc123");
        boolean controlErrato2 = inserimentoLavoratore.soloLettere("12345");
        boolean controlErrato3 = inserimentoLavoratore.soloLettere("sb4@?$");


        assertEquals(true, controlCorretto, "E' richiesta presenza di sole lettere (anche accentate)");
        assertEquals(true, controlCorretto2, "E' richiesta presenza di sole lettere (anche accentate)");
        assertEquals(false, controlErrato1, "Dovrebbe essere richiesta presenza di sole lettere");
        assertEquals(false, controlErrato2, "Dovrebbe essere richiesta presenza di sole lettere");
        assertEquals(false, controlErrato3, "Dovrebbe essere richiesta presenza di sole lettere");
    }

    @Test
    @DisplayName("Controllo che il lavoratore automunito sia anche patentato con patente B o superiore")
    void controllaAutomunitoEPatenteTest() {
        InserimentoLavoratore inserimentoLavoratore = new InserimentoLavoratore();

        boolean controlCorretto1 = inserimentoLavoratore.controllaAutomunitoEPatente(true, "B");
        boolean controlCorretto2 = inserimentoLavoratore.controllaAutomunitoEPatente(true, "C");
        boolean controlCorretto3 = inserimentoLavoratore.controllaAutomunitoEPatente(true, "D");

        boolean controlErrato1 = inserimentoLavoratore.controllaAutomunitoEPatente(true, "null");
        boolean controlErrato2 = inserimentoLavoratore.controllaAutomunitoEPatente(true, "A");
        boolean controlErrato3 = inserimentoLavoratore.controllaAutomunitoEPatente(true, "A1");
        boolean controlErrato4 = inserimentoLavoratore.controllaAutomunitoEPatente(true, "-Nessuna-");

        assertEquals(true, controlCorretto1, "Richiesta patente B o superiore");
        assertEquals(true, controlCorretto2, "Richiesta patente B o superiore");
        assertEquals(true, controlCorretto3, "Richiesta patente B o superiore");
        assertEquals(false, controlErrato1, "Patente attesa A o inferiore");
        assertEquals(false, controlErrato2, "Patente attesa A o inferiore");
        assertEquals(false, controlErrato3, "Patente attesa A o inferiore");
        assertEquals(false, controlErrato4, "Patente attesa A o inferiore");

    }

    @Test
    @DisplayName("Controllo che il lavoratore abbia più di 16 anni")
    void getDataTest() {
        InserimentoLavoratore inserimentoLavoratore = new InserimentoLavoratore();

        LocalDate LOCAL_DATE_1 = LocalDate.of(2006, 9, 17);
        boolean controlCorretto1 = inserimentoLavoratore.getData(LOCAL_DATE_1);

        LocalDate LOCAL_DATE_4 = LocalDate.of(2022, 9, 19);
        boolean controlCorretto2 = inserimentoLavoratore.getData(LOCAL_DATE_4);

        LocalDate LOCAL_DATE_2 = LocalDate.of(2024, 9, 17);
        boolean controlErrato1 = inserimentoLavoratore.getData(LOCAL_DATE_2);

        LocalDate LOCAL_DATE_3 = LocalDate.of(2012, 9, 27);
        boolean controlErrato2 = inserimentoLavoratore.getData(LOCAL_DATE_3);


        assertEquals(true, controlCorretto1, "Richiesta età di 16 anni");
        assertEquals(true, controlCorretto2, "Richiesta età di 16 anni");
        assertEquals(false, controlErrato1, "Età attesa minore di 16 anni");
        assertEquals(false, controlErrato2, "Età attesa minore di 16 anni");

    }



    @Test
    @DisplayName("Controllo che la data sia formata da numeri")
    void controlloSoloNumeriDataTest() {
        InserimentoLavoratore inserimentoLavoratore = new InserimentoLavoratore();

        boolean controlCorretto1 = inserimentoLavoratore.controlloSoloNumeriData("05/2020");
        boolean controlErrato1 = inserimentoLavoratore.controlloSoloNumeriData("a5/20b0");
        boolean controlErrato2 = inserimentoLavoratore.controlloSoloNumeriData("2020/03");

        assertEquals(true, controlCorretto1, "Richiesta data con formato mm/yyyy");
        assertEquals(false, controlErrato1, "Richiesta data con formato diverso da mm/yyyy");
        assertEquals(false, controlErrato2, "Richiesta data con formato diverso da mm/yyyy");
    }

    @Test
    @DisplayName("Controllo che la data non sia nel passato")
    void controlloMesiAnniTest() {
        InserimentoLavoratore inserimentoLavoratore = new InserimentoLavoratore();

        boolean controlCorretto1 = inserimentoLavoratore.controlloMesiAnni("09/2023");
        boolean controlCorretto2 = inserimentoLavoratore.controlloMesiAnni("09/2022");
        boolean controlErrato1 = inserimentoLavoratore.controlloMesiAnni("08/2022");
        boolean controlErrato2 = inserimentoLavoratore.controlloMesiAnni("10/2020");
        boolean controlErrato3 = inserimentoLavoratore.controlloMesiAnni("25/2020");


        assertEquals(true, controlCorretto1, "Richiesta data possibile futura");
        assertEquals(true, controlCorretto2, "Richiesta data possibile futura");
        assertEquals(false, controlErrato1, "Richiesta data passata o impossibile");
        assertEquals(false, controlErrato2, "Richiesta data passata o impossibile");
        assertEquals(false, controlErrato3, "Richiesta data passata o impossibile");

    }

    @Test
    @DisplayName("Controlla, per uno stesso comune, che la data finale sia successiva a quella iniziale")
    void controlloDateTest() {
        InserimentoLavoratore inserimentoLavoratore = new InserimentoLavoratore();

        boolean controlCorretto1 = inserimentoLavoratore.controlloDate("09/2023", "10/2023");
        boolean controlCorretto2 = inserimentoLavoratore.controlloDate("09/2022", "09/2022");
        boolean controlErrato1 = inserimentoLavoratore.controlloDate("10/2023", "01/2023");


        assertEquals(true, controlCorretto1, "Richieste date non sovrapposte");
        assertEquals(true, controlCorretto2, "Richieste date non sovrapposte");
        assertEquals(false, controlErrato1, "Richiesta data sovrapposta");

    }

    @Test
    @DisplayName("Controlla che le date non si sovrappongano ")
    void controlloDatePerComuni() {
        InserimentoLavoratore inserimentoLavoratore = new InserimentoLavoratore();

        boolean controlCorretto1 = inserimentoLavoratore.controlloDatePerComuni("09/2023", "10/2023", "Casaleone", "Casaleone");
        boolean controlCorretto2 = inserimentoLavoratore.controlloDatePerComuni("09/2022", "12/2023", "Casaleone", "Casaleone");
        boolean controlCorretto3 = inserimentoLavoratore.controlloDatePerComuni("09/2022", "12/2023", "Casaleone", "Cerea");
        boolean controlCorretto4 = inserimentoLavoratore.controlloDatePerComuni("09/2023", "08/2023", "Casaleone", "Cerea");
        boolean controlErrato1 = inserimentoLavoratore.controlloDatePerComuni("10/2023", "01/2023", "Casaleone", "Casaleone");
        boolean controlErrato2 = inserimentoLavoratore.controlloDatePerComuni("10/2023", "10/2023", "Casaleone", "Casaleone");


        assertEquals(true, controlCorretto1, "Richieste date non sovrapposte");
        assertEquals(true, controlCorretto2, "Richieste date non sovrapposte");
        assertEquals(true, controlCorretto3, "Richieste date non sovrapposte");
        assertEquals(true, controlCorretto4, "Richieste date non sovrapposte");
        assertEquals(false, controlErrato1, "Richiesta data sovrapposta");
        assertEquals(false, controlErrato2, "Richiesta data sovrapposta");



    }

    @Test
    @DisplayName("Controllo che il comune esista")
    void controlloComuniInListaTest() throws FileNotFoundException {
        InserimentoLavoratore inserimentoLavoratore = new InserimentoLavoratore();

        boolean controlCorretto1 = inserimentoLavoratore.controlloComuniInLista("Casaleone");
        boolean controlCorretto2 = inserimentoLavoratore.controlloComuniInLista("Isola della Scala");
        boolean controlErrato1 = inserimentoLavoratore.controlloComuniInLista("Jack");
        boolean controlErrato2 = inserimentoLavoratore.controlloComuniInLista("C@sal&one!");

        assertEquals(true, controlCorretto1, "Richiesto comune esistente");
        assertEquals(true, controlCorretto2, "Richiesto comune esistente");
        assertEquals(false, controlErrato1, "Richiesto comune non esistente");
        assertEquals(false, controlErrato2, "Richiesto comune non esistente");

    }


    @Test
    @DisplayName("Controllo che la nazionalità esista")
    void controlloNazioniListaTest() throws FileNotFoundException {
        InserimentoLavoratore inserimentoLavoratore = new InserimentoLavoratore();

        boolean controlCorretto1 = inserimentoLavoratore.controlloNazioniLista("Italiana");
        boolean controlErrato1 = inserimentoLavoratore.controlloNazioniLista("Jack");
        boolean controlErrato2 = inserimentoLavoratore.controlloNazioniLista("C@sal&one!");

        assertEquals(true, controlCorretto1, "Richiesta nazione esistente");
        assertEquals(false, controlErrato1, "Richiesta comune non esistente");
        assertEquals(false, controlErrato2, "Richiesta comune non esistente");

    }

}