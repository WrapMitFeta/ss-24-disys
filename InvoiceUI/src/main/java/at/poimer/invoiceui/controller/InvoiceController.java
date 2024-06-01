package at.poimer.invoiceui.controller;

import at.poimer.invoiceui.InvoiceProgressDelegate;
import at.poimer.invoiceui.InvoiceViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Handles all the user input in the JavaFX UI
 */
public class InvoiceController implements InvoiceProgressDelegate {

    @FXML
    private TextField customerIdTextField;

    private final InvoiceViewModel viewModel = new InvoiceViewModel(this);

    @FXML
    protected void onGenerateInvoiceButtonClick() {
        try {
            int customerId = Integer.parseInt(customerIdTextField.getText());
            viewModel.postInvoice(customerId);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Input is not a number");
            alert.showAndWait();
        }
    }

    /**
     * Shows an alert that invoice is ready
     *
     * @param invoice The invoice to show
     */
    @Override
    public void invoiceReady(File invoice) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Invoice ready");
            alert.setContentText("Invoice successfully downloaded. Would you like to open it now?");
            var result = alert.showAndWait();
            if (result.isEmpty()) {
                return;
            }

            if (result.get() == ButtonType.OK) {
                try {
                    Desktop.getDesktop().open(invoice.getParentFile());
                } catch (IOException ignored) {
                    alert.close();
                }
            } else if (result.get() == ButtonType.CANCEL) {
                alert.close();
            }
        });
    }

    /**
     * Shows an alert that something went wrong while getting the invoice
     */
    @Override
    public void invoiceFailed() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something went wrong. Please try again later.");
            alert.showAndWait();
        });
    }
}
