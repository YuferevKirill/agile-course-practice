package ru.unn.agile.fractioncalculator.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static final String TITLE_OF_APPLICATION = "FractionCalculator";
    private static final String SCENE = "FractionCalculator.fxml";

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource(SCENE));
        primaryStage.setTitle(TITLE_OF_APPLICATION);
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
