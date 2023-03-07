package sample.model.dao;


import java.util.List;
import sample.model.Lavoratore;


public interface LavoratoreDao {

    void insertLavoratore(Lavoratore lavoratore);

    void updateLavoratore (Lavoratore lavoratore);

    void deleteLavoratore(String id) ;

    Lavoratore getLavoratore(String idLavoratore);

    List<Lavoratore> getAllLavoratori();

    List<String> getAllLuoghiResidenza();

    List<String> getAllDateInizio();

    List<String> getAllDateFine();


}