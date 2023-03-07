module src.progettoing {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens sample.controller to javafx.fxml;
    exports sample.controller;
    exports sample.utils;
    opens sample.utils to javafx.fxml;

    opens sample.model.dao ;

    opens sample.model;
    exports sample;
    opens sample to javafx.fxml;


}