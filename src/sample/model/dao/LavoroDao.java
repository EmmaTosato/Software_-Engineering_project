package sample.model.dao;

import java.util.List;
import sample.model.Lavoro;

public interface LavoroDao {
    void insertLavoro(Lavoro lavoro);

    void updateLavoro(Lavoro lavoro);

    void deleteLavoro(int id);

    List<Lavoro> getLavori(String idLavoro);

    List<Lavoro> getAllLavori(String id);

    List<String> getAllMansioni();

}
