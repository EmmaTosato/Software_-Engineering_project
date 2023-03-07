/* TABELLE GIA' SCRITTE E NON MODIFICABILI*/
create table ESPERIENZE (
       esperienza varchar(30),
       PRIMARY KEY (esperienza)
);

create table PATENTE(
       patente varchar(20),
       PRIMARY KEY (patente)
);

create table LINGUE(
          lingua varchar(30),
          PRIMARY KEY (lingua)
);

/* TABELLE GENERATE DALL'INSERIMENTO*/
create table LAVORATORE(
        idLavoratore varchar(100) NOT NULL,
        nomeDipendente varchar(30),
        cognomeDipendente varchar(30),
        mailDipendente varchar(100),
        telefonoDipendente varchar(15),
        dataNascitaDipendente varchar(20),
        luogoNascitaDipendente varchar(30),
        via varchar(30),
        numeroCivico varchar(10),
        paese varchar(30),
        nazionalità varchar(30),
        patente varchar(20),
        automunito varchar(20),
        comune1 varchar(30),
        comune2 varchar(30),
        dataInizioDisponibilità_comune1 varchar(20),
        dataFineDisponibilità_comune1 varchar(20),
        dataInizioDisponibilità_comune2 varchar(20),
        dataFineDisponibilità_comune2 varchar(20),
        nomeEmergenza varchar(30),
        cognomeEmergenza varchar(30),
        mailEmergenza varchar(100),
        telefonoEmergenza varchar(10),
        PRIMARY KEY (idLavoratore)
);


create table LAVORO(
        chiaveSec int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
        idLavoratore varchar(100) REFERENCES LAVORATORE(idLavoratore),
        nomeLavoro varchar(30),
        nomeAzienda varchar(50),
        luogoLavoro varchar(30),
        retribuzioneGiornaliera varchar(100),
        retribuzioneLorda varchar(100),
        mansione_1 varchar(100),
        mansione_2 varchar(100),
        dataInizio varchar(20),
        dataFine varchar(20),
        PRIMARY KEY(idLavoratore, chiaveSec)
);

create table LINGUECONOSCIUTE(
        idLavoratore varchar(100) REFERENCES LAVORATORE(idLavoratore),
        lingua varchar(30),
        PRIMARY KEY (idLavoratore, lingua)
);

create table ESPERIENZEPRECEDENTI(
        idLavoratore varchar(100) REFERENCES LAVORATORE(idLavoratore),
        esperienza varchar(30),
        PRIMARY KEY (idLavoratore, esperienza)
);

create table CREDENZIALI(
        nomeDipendente varchar(20),
        cognomeDipendente varchar(20),
        dataNascitaDipendente varchar(20),
        luogoNascitaDipendente varchar(20),
        nazionalità varchar(20),
        mailDipendente varchar(500),
        telefonoDipendente varchar(15),
        username varchar(20),
        pass varchar(20) NOT NULL,
        PRIMARY KEY (username)
);

/*POPOLAMENTO ESPERIENZE*/
INSERT INTO ESPERIENZE(esperienza) VALUES ('-Nessuna-');
INSERT INTO ESPERIENZE(esperienza) VALUES ('bagnino');
INSERT INTO ESPERIENZE(esperienza) VALUES ('barman');
INSERT INTO ESPERIENZE(esperienza) VALUES ('istruttore di nuoto');
INSERT INTO ESPERIENZE(esperienza) VALUES ('viticultore');
INSERT INTO ESPERIENZE(esperienza) VALUES ('floricultore');

/*POPOLAMENTO PATENTE*/
INSERT INTO PATENTE(patente) VALUES ('-Nessuna-');
INSERT INTO PATENTE(patente) VALUES ('A1');
INSERT INTO PATENTE(patente) VALUES ('A');
INSERT INTO PATENTE(patente) VALUES ('B');
INSERT INTO PATENTE(patente) VALUES ('C');
INSERT INTO PATENTE(patente) VALUES ('D');

/*POPOLAMENTO LINGUE*/
INSERT INTO LINGUE(lingua) VALUES ('Albanese');
INSERT INTO LINGUE(lingua) VALUES ('Arabo');
INSERT INTO LINGUE(lingua) VALUES ('Bielorusso');
INSERT INTO LINGUE(lingua) VALUES ('Bulgaro');
INSERT INTO LINGUE(lingua) VALUES ('Bosniaco');
INSERT INTO LINGUE(lingua) VALUES ('Ceco');
INSERT INTO LINGUE(lingua) VALUES ('Cinese mandarino');
INSERT INTO LINGUE(lingua) VALUES ('Congolese');
INSERT INTO LINGUE(lingua) VALUES ('Croato');
INSERT INTO LINGUE(lingua) VALUES ('Estone');
INSERT INTO LINGUE(lingua) VALUES ('Finlandese');
INSERT INTO LINGUE(lingua) VALUES ('Francese');
INSERT INTO LINGUE(lingua) VALUES ('Rriulano');
INSERT INTO LINGUE(lingua) VALUES ('Ganda');
INSERT INTO LINGUE(lingua) VALUES ('Giapponese');
INSERT INTO LINGUE(lingua) VALUES ('Greco');
INSERT INTO LINGUE(lingua) VALUES ('Hindi');
INSERT INTO LINGUE(lingua) VALUES ('Indonesiano');
INSERT INTO LINGUE(lingua) VALUES ('Inglese');
INSERT INTO LINGUE(lingua) VALUES ('Italiano');
INSERT INTO LINGUE(lingua) VALUES ('Maltese');
INSERT INTO LINGUE(lingua) VALUES ('Napoletano');
INSERT INTO LINGUE(lingua) VALUES ('Polacco');
INSERT INTO LINGUE(lingua) VALUES ('Portoghese');
INSERT INTO LINGUE(lingua) VALUES ('Romeno');
INSERT INTO LINGUE(lingua) VALUES ('Russo');
INSERT INTO LINGUE(lingua) VALUES ('Sardo');
INSERT INTO LINGUE(lingua) VALUES ('Siciliano');
INSERT INTO LINGUE(lingua) VALUES ('Spagnolo');
INSERT INTO LINGUE(lingua) VALUES ('Tedesco');
INSERT INTO LINGUE(lingua) VALUES ('Turco');
INSERT INTO LINGUE(lingua) VALUES ('Ucraino');
INSERT INTO LINGUE(lingua) VALUES ('Ungherese');

/*POPOLAMENTO CREDENZIALI*/
INSERT INTO CREDENZIALI(nomeDipendente, cognomeDipendente, dataNascitaDipendente, luogoNascitaDipendente, nazionalità, mailDipendente, telefonoDipendente, username, pass) VALUES ('Mario', 'Rossi','1990-06-12', 'Verona', 'Italiana', 'mariorossi@gmail.com', '3411677123', 'DIP01','test1');
INSERT INTO CREDENZIALI(nomeDipendente, cognomeDipendente, dataNascitaDipendente, luogoNascitaDipendente, nazionalità, mailDipendente, telefonoDipendente, username, pass) VALUES ('Elena', 'Bianchi','1992-09-06', 'Verona', 'Italiana', 'elenabianchi@gmail.com', '3491617186','DIP02','test2');
