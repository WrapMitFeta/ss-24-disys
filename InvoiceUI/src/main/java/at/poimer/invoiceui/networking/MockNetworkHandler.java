package at.poimer.invoiceui.networking;

import at.poimer.invoiceui.exception.ResponseNotOkayException;

import java.io.IOException;

public class MockNetworkHandler implements NetworkProviding {

    public MockNetworkHandler(int retries) {
        this.retries = retries;
    }

    public int retryCount = 0;
    private final int retries;

    @Override
    public byte[] getInvoice(int customerId) throws ResponseNotOkayException {
        if (retryCount < retries) {
            retryCount++;
            throw new ResponseNotOkayException("");
        }

        return new byte[0];
    }

    @Override
    public void postInvoice(int customerId) throws InterruptedException {
        Thread.sleep(1000);
    }
}
