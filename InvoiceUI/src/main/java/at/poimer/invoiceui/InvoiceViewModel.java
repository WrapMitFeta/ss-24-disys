package at.poimer.invoiceui;

import at.poimer.invoiceui.exception.ResponseNotOkayException;
import at.poimer.invoiceui.networking.NetworkHandler;
import at.poimer.invoiceui.networking.NetworkProviding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InvoiceViewModel {

    public InvoiceViewModel(InvoiceProgressDelegate listener) {
        this.listener = listener;
    }

    private final InvoiceProgressDelegate listener;
    private final NetworkProviding networkHandler = new NetworkHandler();

    private ExecutorService executor;

    public void postInvoice(int customerId) {
        if (executor != null) {
            System.out.println("shutting down executor");
            executor.shutdownNow();
        }

        try {
            executor = Executors.newFixedThreadPool(1);

            executor.execute(() -> {
                try {
                    networkHandler.postInvoice(customerId);
                } catch (Exception exception) {
                    System.out.println("post failed");
                    listener.invoiceFailed();
                    return;
                }
                getInvoice(customerId);
            });
        } catch (Exception ignored) {
            listener.invoiceFailed();
        }
    }

    private void getInvoice(int customerId) {
        var downloadSuccessful = false;
        byte[] pdfBytes = null;

        while (!downloadSuccessful) {
            try {
                pdfBytes = networkHandler.getInvoice(customerId);

                downloadSuccessful = true;
            } catch (ResponseNotOkayException ignored) {
                try {
                    System.out.println("no pdf available, retrying");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    return;
                }
            } catch (Exception ignored) {
                System.out.println("download failed");
                listener.invoiceFailed();
                return;
            }
        }

        String userHomePath = System.getProperty("user.home");

        String filePath = userHomePath + File.separator + "Downloads" + File.separator + "invoice_" + customerId + ".pdf";
        File file = new File(filePath);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(pdfBytes);
            fileOutputStream.close();

            System.out.println("invoice successfully written to disk");
            listener.invoiceReady(file);
        } catch (IOException exception) {
            listener.invoiceFailed();
        }
    }
}
