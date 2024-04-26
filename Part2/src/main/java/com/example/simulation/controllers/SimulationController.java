package com.example.simulation.controllers;

import com.example.simulation.storage.SimulationStorage;
import com.example.simulation.models.Horse;
import com.example.simulation.utils.NotificationUtils;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class SimulationController implements Initializable {
    @FXML
    private Text txtShowWinner;
    @FXML
    private Button btnSave;
    @FXML
    private TextField tBreed;
    @FXML
    private ComboBox<String> cbCoatColour;
    @FXML
    private TextField tName;
    @FXML
    private TableColumn<Horse, String> colBreed;
    @FXML
    private TableColumn<Horse, String> colCoatColour;
    @FXML
    private TableColumn<Horse, String> colName;
    @FXML
    private TableColumn<Horse, Integer> colTotalRaceWon;
    @FXML
    private TableColumn<Horse, Double> colWinningOdds;
    @FXML
    private TableView<Horse> horseDataTable;
    @FXML
    private TextField betAmountField;
    @FXML
    private ComboBox<Horse> horseComboBox;
    @FXML
    private ComboBox<String> raceTrackLengthComboBox;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    private AnimationTimer animationTimer;
    private final Random random;
    private boolean raceFinished;
    private Horse winner;
    private int selectedHorseIndex;
    private ObservableList<Horse> horses;
    private NotificationUtils notificationUtils;


    public SimulationController() {
        this.notificationUtils = new NotificationUtils();
        this.random = new Random();
        this.selectedHorseIndex = -1;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.horseComboBox.setItems(FXCollections.observableArrayList(SimulationStorage.getHorses()));
        this.cbCoatColour.getItems().setAll(SimulationStorage.getCoatColours());
        this.raceTrackLengthComboBox.getItems().setAll(SimulationStorage.getRaceDistance());

        this.horses = FXCollections.observableArrayList(SimulationStorage.getHorses());

        showHorses();

        this.canvas = new Canvas(604, 291);
        anchorPane.getChildren().add(canvas);
        AnchorPane.setLeftAnchor(canvas, 439.0);
        AnchorPane.setTopAnchor(canvas, 619.0);

        this.raceFinished = false;
        horseComboBox.setItems(FXCollections.observableArrayList(SimulationStorage.getHorses()));
        updateHorseComboBox();
    }

    private void showHorses() {
        horseDataTable.setItems(horses);
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBreed.setCellValueFactory(new PropertyValueFactory<>("breed"));
        colCoatColour.setCellValueFactory(new PropertyValueFactory<>("coatColour"));
        colTotalRaceWon.setCellValueFactory(new PropertyValueFactory<>("totalRaceWon"));
        colWinningOdds.setCellValueFactory(new PropertyValueFactory<>("winningOdds"));
    }

    @FXML
    private void getData(MouseEvent event) {
        Horse horse = horseDataTable.getSelectionModel().getSelectedItem();
        if (horse == null) {
            notificationUtils.showNotification("No Horse Selected", "Please select a horse to update.");
            return;
        }
        selectedHorseIndex = horses.indexOf(horse);
        tName.setText(horse.getName());
        tBreed.setText(horse.getBreed());
        cbCoatColour.setValue(horse.getCoatColour());
        btnSave.setDisable(true);
    }

    @FXML
    private void clearFields(ActionEvent event) {
        tName.clear();
        tBreed.clear();
        txtShowWinner.setText("Final Result");
        cbCoatColour.getSelectionModel().clearSelection();
        btnSave.setDisable(false);
        showHorses();
        horseComboBox.getSelectionModel().clearSelection();
        betAmountField.clear();
        raceTrackLengthComboBox.getSelectionModel().clearSelection();
        selectedHorseIndex = -1;
    }

    @FXML
    private void createHorse(ActionEvent event) {
        String name = tName.getText();
        String breed = tBreed.getText();
        String coatColour = cbCoatColour.getValue();
        double horseConfidence = Math.round((0.5 + Math.random() * 0.5) * 10.0) / 10.0;

        if (name.isEmpty() || breed == null || coatColour == null) {
            notificationUtils.showNotification("Missing Information", "Please fill in all fields.");
            return;
        }

        Horse horse = new Horse(name, breed, coatColour);
        horse.setHorseConfidence(horseConfidence);
        SimulationStorage.addHorse(horse);
        horses.setAll(SimulationStorage.getHorses());
        updateHorseComboBox();
        showHorses();
        clearFields(null);
    }

    @FXML
    private void deleteHorse(ActionEvent event) {
        if (selectedHorseIndex == -1) {
            notificationUtils.showNotification("No Horse Selected", "Please select a horse to delete.");
            return;
        }

        notificationUtils.showNotification("Horse Deleted", "The horse has been deleted.");
        SimulationStorage.deleteHorse(horses.get(selectedHorseIndex).getName());
        horses.setAll(SimulationStorage.getHorses());
        updateHorseComboBox();
        showHorses();
        clearFields(null);
    }

    @FXML
    private void updateHorse(ActionEvent event) {
        if (selectedHorseIndex == -1) {
            notificationUtils.showNotification("No Horse Selected", "Please select a horse to update.");
            return;
        }

        String name = tName.getText();
        String breed = tBreed.getText();
        String coatColour = cbCoatColour.getValue();

        if (name.isEmpty() || breed == null || coatColour == null) {
            notificationUtils.showNotification("Missing Information", "Please fill in all fields.");
            return;
        }

        Horse updatedHorse = new Horse(name, breed, coatColour);
        updatedHorse.setHorseConfidence(horses.get(selectedHorseIndex).getHorseConfidence());
        updatedHorse.setDistanceTravelled(horses.get(selectedHorseIndex).getDistanceTravelled());
        updatedHorse.setHasFallen(horses.get(selectedHorseIndex).isHasFallen());
        updatedHorse.setTotalRaceWon(horses.get(selectedHorseIndex).getTotalRaceWon());
        updatedHorse.setWinningOdds(horses.get(selectedHorseIndex).getWinningOdds());
        SimulationStorage.deleteHorse(horses.get(selectedHorseIndex).getName());
        SimulationStorage.addHorse(updatedHorse);
        horses.setAll(SimulationStorage.getHorses());
        updateHorseComboBox();
        showHorses();
        clearFields(null);
    }

    private void updateHorseComboBox() {
        horseComboBox.setItems(FXCollections.observableArrayList(SimulationStorage.getHorses()));
    }

    @FXML
    private void startRace() {
        Horse selectedHorse = horseComboBox.getValue();
        String betAmountText = betAmountField.getText();
        String raceTrackLengthString = raceTrackLengthComboBox.getValue();

        if (selectedHorse == null || betAmountText.isEmpty() || raceTrackLengthString == null) {
            notificationUtils.showNotification("Missing Information", "Please select a horse, track length, and enter the bet amount.");
            return;
        }

        double betAmount;
        try {
            betAmount = Double.parseDouble(betAmountText);
        } catch (NumberFormatException e) {
            notificationUtils.showNotification("Invalid Information", "Please provide a valid bet amount.");
            return;
        }

        notificationUtils.showNotification("Bet Placed", "You have placed a bet on " + selectedHorse.getName() + " for $" + betAmount);

        for (Horse horse : horses) {
            horse.setDistanceTravelled(0.0);
            horse.setHasFallen(false);
        }
        raceFinished = false;
        winner = null;

        startAnimation();
    }

    private void startAnimation() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!raceFinished) {
                    updateHorsePositions();
                    drawHorses();
                } else {
                    winner.incrementTotalRaceWon();
                    for (Horse horse : horses) {
                        horse.updateWinningOdds();
                    }
                    horses.setAll(SimulationStorage.getHorses());
                    animationTimer.stop();
                    showWinnerNotification(winner.getName());
                }
            }
        };
        animationTimer.start();
    }

    private void updateHorsePositions() {
        int fallenCount = (int) horses.stream().filter(Horse::isHasFallen).count();
        int maxFallen = horses.size() / 2;
        double midDistance = canvas.getWidth() / 2;

        for (Horse horse : horses) {
            if (!raceFinished) {
                if ((Math.random() * 5) < (horse.getHorseConfidence() * 5)) {
                    horse.moveForward(random.nextDouble());
                }
                if (horse.getDistanceTravelled() >= canvas.getWidth() && !raceFinished) {
                    raceFinished = true;
                    winner = horse;
                } else if (horse.getDistanceTravelled() >= midDistance && fallenCount < maxFallen
                        && !horses.get(0).getName().equalsIgnoreCase(horse.getName())) {
                    if (Math.random() < (0.1 * horse.getHorseConfidence())) {
                        horse.setHasFallen(true);
                        fallenCount++;
                    }
                }
            }
        }
    }

    private void showWinnerNotification(String winnerName) {
        txtShowWinner.setText("And the winner is " + winnerName);
    }

    private void drawHorses() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double horseHeight = canvas.getHeight() / horses.size();
        double horseWidth = 50;

        for (int i = 0; i < horses.size(); i++) {
            Horse horse = horses.get(i);
            gc.setFill(Paint.valueOf(horse.getCoatColour()));
            double horseX = Math.min(horse.getDistanceTravelled(), canvas.getWidth() - horseWidth);
            gc.fillRect(horseX, i * horseHeight, horseWidth, horseHeight - 5);
            gc.setFill(Color.BLACK);
            gc.setFont(new Font(16));
            gc.fillText(horse.getName(), horseX - 100, (i * horseHeight) + 15);
        }

        if (raceFinished) {
            showWinnerNotification(winner.getName());
        }
    }

}