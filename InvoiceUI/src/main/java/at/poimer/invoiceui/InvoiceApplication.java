package at.poimer.invoiceui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InvoiceApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setMinWidth(320);
        stage.setMinHeight(240);

        FXMLLoader fxmlLoader = new FXMLLoader(InvoiceApplication.class.getResource("invoice-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Invoice UI");
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> System.exit(0));

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}