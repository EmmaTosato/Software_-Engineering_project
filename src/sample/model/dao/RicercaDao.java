package sample.model.dao;

import sample.model.Ricerca;

import java.util.List;

public interface RicercaDao {

   public  List<String> ricercaSemplice (Ricerca ricerca);
   public  List<String> ricercaComplessaAND(Ricerca ricerca);
   public  List<String> ricercaComplessaOR(Ricerca ricerca);






}
