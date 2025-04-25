package lk.ijse.gdse.mentalhealth.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.mentalhealth.bo.custom.PaymentBO;
import lk.ijse.gdse.mentalhealth.bo.custom.impl.PaymentBOImpl;
import lk.ijse.gdse.mentalhealth.dto.PaymentDTO;
import lk.ijse.gdse.mentalhealth.view.tdm.PaymentTM;

import java.util.ArrayList;

public class FinancialReportController {

    @FXML
    private BarChart<String, Number> barChartPayments;

    @FXML
    private TableColumn<PaymentDTO, Double> colAmount;

    @FXML
    private TableColumn<PaymentDTO, String> colPaymentId;

    @FXML
    private TableColumn<PaymentDTO, String> colStatus;

    @FXML
    private AnchorPane financeReportPage;

    @FXML
    private TableView<PaymentTM> tblPayments;

    private final PaymentBO paymentBO = new PaymentBOImpl();

    public void initialize() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadPayments();
        loadPaymentStatistics();
    }

    private void loadPayments() {
        try {
            ArrayList<PaymentDTO> payments = paymentBO.loadAllPayments();
            ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

            for (PaymentDTO paymentDTO : payments) {
                PaymentTM paymentTM = new PaymentTM(
                        paymentDTO.getPaymentId(),
                        paymentDTO.getAmount(),
                        paymentDTO.getStatus()
                );

                paymentTMS.add(paymentTM);
            }
            tblPayments.setItems(paymentTMS);
        } catch (Exception e) {
            showAlert("Error", "Failed to load Payments!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadPaymentStatistics() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Payment Status");

        try {
            long completed = paymentBO.getCompletedPaymentCount();
            long pending = paymentBO.getPendingPaymentCount();

            series.getData().add(new XYChart.Data<>("Completed", completed));
            series.getData().add(new XYChart.Data<>("Pending", pending));

            barChartPayments.getData().add(series);
        } catch (Exception e) {
            showAlert("Error", "Failed to load Payment Statistics!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}