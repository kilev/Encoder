package com.kil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.util.BigReal;

import javax.annotation.Nonnull;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class MainWindowController {

    @FXML
    private TextField tpm11;

    @FXML
    private TextField tpm21;

    @FXML
    private TextField tpm31;

    @FXML
    private TextField tpm12;

    @FXML
    private TextField tpm22;

    @FXML
    private TextField tpm32;

    @FXML
    private TextField tpm13;

    @FXML
    private TextField tpm23;

    @FXML
    private TextField tpm33;

    @FXML
    private TextField vis1;

    @FXML
    private TextField vis2;

    @FXML
    private TextField vis3;

    @FXML
    private TextField cycleCount;

    @FXML
    private Label soughtProbability1;

    @FXML
    private Label soughtProbability2;

    @FXML
    private Label soughtProbability3;

    @FXML
    void initialize() {

    }

    @FXML
    void compute(ActionEvent event) {
        try {
            Model model = getModel();
            Validator.validate(model);
            ComputedModel computedModel = Computer.compute(model);
            showComputedModel(computedModel);
        } catch (ModelException e) {
            showError(e);
        }
    }

    private Model getModel() throws ModelException {
        Model model = new Model();
        try {
            model.setTransitionProbabilityMatrix(getTransitionProbabilityMatrix());
        } catch (NullPointerException | NumberFormatException e) {
            throw new ModelException("Неправильные входные данные для матрицы вероятностей перехода за один шаг");
        }
        try {
            model.setInitialStatesVector(getInitialStatesVector());
        } catch (NullPointerException | NumberFormatException e) {
            throw new ModelException("Неправильные входные данные для вектора начальных состояний");
        }

        try {
            model.setIterationsCount(getInteger(cycleCount));
        } catch (NullPointerException | NumberFormatException e) {
            throw new ModelException("Неправильные входные данные для количества циклов");
        }
        return model;
    }

    private FieldMatrix<BigReal> getTransitionProbabilityMatrix() {
        return MatrixUtils.createFieldMatrix(new BigReal[][]{
                new BigReal[]{getBigReal(tpm11), getBigReal(tpm12), getBigReal(tpm13)},
                new BigReal[]{getBigReal(tpm21), getBigReal(tpm22), getBigReal(tpm23)},
                new BigReal[]{getBigReal(tpm31), getBigReal(tpm32), getBigReal(tpm33)}
        });
    }

    private FieldVector<BigReal> getInitialStatesVector() {
        return MatrixUtils.createFieldVector(new BigReal[]{getBigReal(vis1), getBigReal(vis2), getBigReal(vis3)});
    }

    private void showComputedModel(ComputedModel computedModel) {
        List<BigReal> probability = Arrays.asList(computedModel.getProbability().toArray());
        soughtProbability1.setText(getAsString(probability.get(0)));
        soughtProbability2.setText(getAsString(probability.get(1)));
        soughtProbability3.setText(getAsString(probability.get(2)));
    }

    private BigReal getBigReal(TextField textField) {
        return new BigReal(textField.getText());
    }

    private Integer getInteger(TextField textField) {
        return Integer.parseInt(textField.getText());
    }

    private String getAsString(BigReal bigReal) {
        return bigReal.bigDecimalValue().round(new MathContext(3,  RoundingMode.DOWN)).toString();
    }

    private void showError(@Nonnull Throwable error) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(error.getMessage());
        alert.show();
    }

}
