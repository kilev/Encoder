package com.kil;

import com.google.common.collect.Sets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class MainWindowController {
    private static final String VALIDATION_ERROR_PREFIX = "Ошибка валидации: ";

    @FXML
    private TableView<Slice> multiplicityA;

    @FXML
    private TableColumn<Slice, Double> multiplicityAColumnAlvl;

    @FXML
    private TableColumn<Slice, Double> multiplicityAColumnLow;

    @FXML
    private TableColumn<Slice, Double> multiplicityAColumnHigh;

    @FXML
    private TableView<Slice> multiplicityB;

    @FXML
    private TableColumn<Slice, Double> multiplicityBColumnAlvl;

    @FXML
    private TableColumn<Slice, Double> multiplicityBColumnLow;

    @FXML
    private TableColumn<Slice, Double> multiplicityBColumnHigh;

    @FXML
    private ToggleButton lessIndicator;

    @FXML
    private ToggleButton moreIndicator;

    @FXML
    private ToggleButton equalIndicator;

    @FXML
    private Button plusButton;

    @FXML
    private Button minusButton;

    @FXML
    private Button multiButton;

    @FXML
    private Button divideButton;

    @FXML
    private TableView<Slice> multiplicityC;

    @FXML
    private TableColumn<Slice, Double> multiplicityCColumnAlvl;

    @FXML
    private TableColumn<Slice, Double> multiplicityCColumnLow;

    @FXML
    private TableColumn<Slice, Double> multiplicityCColumnHigh;

    @FXML
    private AreaChart<Number, Number> chart;

    private final MultiplicityService multiplicityService = new MultiplicityService();

    @FXML
    void initialize() {
        multiplicityA.setEditable(true);
        multiplicityAColumnAlvl.setCellValueFactory(new PropertyValueFactory<>("alphaLevel"));
        multiplicityAColumnLow.setCellValueFactory(new PropertyValueFactory<>("low"));
        multiplicityAColumnHigh.setCellValueFactory(new PropertyValueFactory<>("high"));
        multiplicityAColumnAlvl.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        multiplicityAColumnLow.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        multiplicityAColumnHigh.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        multiplicityAColumnAlvl.setOnEditCommit(this::onSliceChanged);
        multiplicityAColumnLow.setOnEditCommit(this::onSliceChanged);
        multiplicityAColumnHigh.setOnEditCommit(this::onSliceChanged);

        multiplicityB.setEditable(true);
        multiplicityBColumnAlvl.setCellValueFactory(new PropertyValueFactory<>("alphaLevel"));
        multiplicityBColumnLow.setCellValueFactory(new PropertyValueFactory<>("low"));
        multiplicityBColumnHigh.setCellValueFactory(new PropertyValueFactory<>("high"));
        multiplicityBColumnAlvl.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        multiplicityBColumnLow.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        multiplicityBColumnHigh.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        multiplicityBColumnAlvl.setOnEditCommit(this::onSliceChanged);
        multiplicityBColumnLow.setOnEditCommit(this::onSliceChanged);
        multiplicityBColumnHigh.setOnEditCommit(this::onSliceChanged);

        multiplicityC.setEditable(true);
        multiplicityCColumnAlvl.setCellValueFactory(new PropertyValueFactory<>("alphaLevel"));
        multiplicityCColumnLow.setCellValueFactory(new PropertyValueFactory<>("low"));
        multiplicityCColumnHigh.setCellValueFactory(new PropertyValueFactory<>("high"));
        multiplicityCColumnAlvl.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        multiplicityCColumnLow.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        multiplicityCColumnHigh.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        multiplicityCColumnAlvl.setOnEditCommit(this::onSliceChanged);
        multiplicityCColumnLow.setOnEditCommit(this::onSliceChanged);
        multiplicityCColumnHigh.setOnEditCommit(this::onSliceChanged);

        ObservableList<Slice> sliceARowValues = FXCollections.observableArrayList(
                new Slice(0d, 1d, 9d),
                new Slice(0.5d, 2d, 8d),
                new Slice(1d, 3d, 4d));
        multiplicityA.setItems(sliceARowValues);

        ObservableList<Slice> sliceBRowValues = FXCollections.observableArrayList(
                new Slice(0d, 1d, 9d),
                new Slice(0.5d, 3d, 6d),
                new Slice(1.0d, 4d, 5d),
                new Slice(0.2d, 2d, 7d));
        multiplicityB.setItems(sliceBRowValues);

        onSliceChanged(null);
    }

    @FXML
    void divideMultiplicity(ActionEvent event) {
        clearCharts();
        Multiplicity multiplicityC = multiplicityService.divide(
                getMultiplicity(MultiplicityName.A),
                getMultiplicity(MultiplicityName.B));
        this.multiplicityC.setItems(FXCollections.observableArrayList(multiplicityC.getSlices()));
        clearCharts();
        drawCharts();
    }

    @FXML
    void minusMultiplicity(ActionEvent event) {
        clearCharts();
        Multiplicity multiplicityC = multiplicityService.minus(
                getMultiplicity(MultiplicityName.A),
                getMultiplicity(MultiplicityName.B));
        this.multiplicityC.setItems(FXCollections.observableArrayList(multiplicityC.getSlices()));
        clearCharts();
        drawCharts();
    }

    @FXML
    void multiMultiplicity(ActionEvent event) {
        clearCharts();
        Multiplicity multiplicityC = multiplicityService.multi(
                getMultiplicity(MultiplicityName.A),
                getMultiplicity(MultiplicityName.B));
        this.multiplicityC.setItems(FXCollections.observableArrayList(multiplicityC.getSlices()));
        clearCharts();
        drawCharts();
    }

    @FXML
    void plusMultiplicity(ActionEvent event) {
        clearCharts();
        Multiplicity multiplicityC = multiplicityService.plus(
                getMultiplicity(MultiplicityName.A),
                getMultiplicity(MultiplicityName.B));
        this.multiplicityC.setItems(FXCollections.observableArrayList(multiplicityC.getSlices()));
        clearCharts();
        drawCharts();
    }

    private void onSliceChanged(TableColumn.CellEditEvent<Slice, Double> e) {
        if (e != null) {
            Slice sliceToChange = e.getRowValue();
            int columnIndex = e.getTablePosition().getColumn();
            Double newValue = e.getNewValue();
            if (columnIndex == 0) {
                sliceToChange.setAlphaLevel(newValue);
            } else if (columnIndex == 1) {
                sliceToChange.setLow(newValue);
            } else {
                sliceToChange.setHigh(newValue);
            }
        }

        multiplicityC.setItems(FXCollections.observableArrayList());
        clearCompareIndicators();
        clearCharts();
        setArithmeticOperationsDisable(true);
        List<Multiplicity> multiplicities = getInputMultiplicities();

        multiplicityService.prepare(multiplicities);
        try {
            multiplicityService.validate(multiplicities);
        } catch (MultiplicityValidationException exception) {
            showDialog(VALIDATION_ERROR_PREFIX + exception.getMessage());
            return;
        }

        drawCharts();
        compare();
        setArithmeticOperationsDisable(false);
    }

    private void clearCompareIndicators(){
        equalIndicator.setSelected(false);
        moreIndicator.setSelected(false);
        lessIndicator.setSelected(false);
    }

    private void clearCharts() {
        chart.getData().clear();
    }

    private void drawCharts() {
        chart.getData().add(getSeries(multiplicityA, "A"));
        chart.getData().add(getSeries(multiplicityB, "B"));
        chart.getData().add(getSeries(multiplicityC, "C"));
    }

    private void setArithmeticOperationsDisable(boolean disable){
        plusButton.setDisable(disable);
        minusButton.setDisable(disable);
        divideButton.setDisable(disable);
        multiButton.setDisable(disable);
    }

    private void compare() {
        int compareResult = multiplicityService.getComparator().compare(
                getMultiplicity(MultiplicityName.A),
                getMultiplicity(MultiplicityName.B));
        if (compareResult == 0) {
            equalIndicator.setSelected(true);
        } else if (compareResult > 0) {
            moreIndicator.setSelected(true);
        } else {
            lessIndicator.setSelected(true);
        }
    }

    private XYChart.Series<Number, Number> getSeries(TableView<Slice> table, String seriesName) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(seriesName);

        List<Slice> sortedRows = table.getItems().stream()
                .sorted(Comparator.comparingDouble(Slice::getAlphaLevel))
                .collect(Collectors.toList());

        for (Slice row : sortedRows) {
            series.getData().add(new XYChart.Data<>(row.getLow(), row.getAlphaLevel()));
        }

        Collections.reverse(sortedRows);
        for (Slice row : sortedRows) {
            series.getData().add(new XYChart.Data<>(row.getHigh(), row.getAlphaLevel()));
        }

        return series;
    }

    private List<Multiplicity> getInputMultiplicities() {
        return Arrays.asList(
                getMultiplicity(MultiplicityName.A),
                getMultiplicity(MultiplicityName.B));
    }

    private Multiplicity getMultiplicity(MultiplicityName name) {
        switch (name) {
            case A -> {
                return new Multiplicity(name, new ArrayList<>(multiplicityA.getItems()));
            }
            case B -> {
                return new Multiplicity(name, new ArrayList<>(multiplicityB.getItems()));
            }
            case C -> {
                return new Multiplicity(name, new ArrayList<>(multiplicityC.getItems()));
            }
            default -> throw new IllegalStateException("Unexpected value: " + name);
        }
    }

    private void showDialog(@Nonnull String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
