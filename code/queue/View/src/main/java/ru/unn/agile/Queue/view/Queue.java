package ru.unn.agile.Queue.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.unn.agile.Queue.viewmodel.ViewModel;

public class Queue {

    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField queueNewElement;
    @FXML
    private Button addElement;
    @FXML
    private Button popElement;
    @FXML
    private Button clearQueue;
    @FXML
    private Button getHead;
    @FXML
    private Button getTail;
    @FXML
    private Label queueResult;
    @FXML
    private Label status;

}