module at.poimer.invoiceui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.desktop;

    opens at.poimer.invoiceui to javafx.fxml;
    exports at.poimer.invoiceui;
    exports at.poimer.invoiceui.controller;
    opens at.poimer.invoiceui.controller to javafx.fxml;
    exports at.poimer.invoiceui.exception;
    opens at.poimer.invoiceui.exception to javafx.fxml;
    exports at.poimer.invoiceui.networking;
    opens at.poimer.invoiceui.networking to javafx.fxml;
}