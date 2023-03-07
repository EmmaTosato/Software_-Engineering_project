import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.model.dao.DipendenteDaoImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginTest {
    @Test
    @DisplayName("Controllo accesso")
    void accessoTest(){
        int controlCorretto = DipendenteDaoImpl.checkCredenziali("DIP01", "test1");
        int controlErrato1 = DipendenteDaoImpl.checkCredenziali("dipendente", "test1");
        int controlErrato2 = DipendenteDaoImpl.checkCredenziali("DIP01", "ciao");
        assertEquals(1, controlCorretto, "return per l'accesso corretto è 1");
        assertEquals(0, controlErrato1, "return per l'accesso corretto è 1");
        assertEquals(0, controlErrato2, "return per l'accesso corretto è 1");

    }
}