package at.poimer.invoiceui.networking;

import at.poimer.invoiceui.exception.ResponseNotOkayException;

import java.io.IOException;

/**
 * Implement this to provide networking capabilities for starting invoice generation and downloading invoice
 */
public interface NetworkProviding {
    /**
     * Checks if the requested invoice for the customer id is ready to download
     * and downloads it
     *
     * @param customerId The customer id for the invoice to be downloaded
     * @return The invoice for the customer id as bytes
     * @throws IOException              IOException
     * @throws InterruptedException     InterruptedException
     * @throws ResponseNotOkayException ResponseNotOkayException
     */
    byte[] getInvoice(int customerId) throws IOException, InterruptedException, ResponseNotOkayException;

    /**
     * Posts a request to get the invoice generation started
     *
     * @param customerId The customer id for the invoice
     * @throws IOException              IOException
     * @throws InterruptedException     InterruptedException
     * @throws ResponseNotOkayException ResponseNotOkayException
     */
    void postInvoice(int customerId) throws IOException, InterruptedException, ResponseNotOkayException;
}
