package at.poimer.invoiceui;

import java.io.File;

/**
 * Implement this to be notified of download progress and failure
 */
public interface InvoiceProgressDelegate {
    /**
     * Tells the receiver that the invoice is ready
     *
     * @param invoice The File where the invoice is written to
     */
    void invoiceReady(File invoice);

    /**
     * Tells the receiver that something went wrong while getting the invoice
     */
    void invoiceFailed();
}
