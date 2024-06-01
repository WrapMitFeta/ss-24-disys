package at.poimer.invoiceui.networking;

import at.poimer.invoiceui.exception.ResponseNotOkayException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NetworkHandler implements NetworkProviding {

    @Override
    public void postInvoice(int customerId) throws IOException, InterruptedException, ResponseNotOkayException {
        var url = "http://localhost:8080/customers/" + customerId + "/invoice";

        var client = HttpClient.newBuilder().build();

        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            throw new ResponseNotOkayException("Response was not okay");
        }

        response.body();
    }

    @Override
    public byte[] getInvoice(int customerId) throws IOException, InterruptedException, ResponseNotOkayException {
        var url = "http://localhost:8080/customers/" + customerId + "/invoice";

        var client = HttpClient.newBuilder().build();

        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            throw new ResponseNotOkayException("Response was not okay");
        }

        return response.body();
    }
}