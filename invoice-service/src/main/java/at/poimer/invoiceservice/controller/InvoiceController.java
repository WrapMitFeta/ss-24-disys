package at.poimer.invoiceservice.controller;

import at.poimer.invoiceservice.producer.StartProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
@RestController
public class InvoiceController {

    private final StartProducer startProducer;

    // Dependency injection via constructor
    public InvoiceController(StartProducer startProducer) {
        this.startProducer = startProducer;
    }

    /**
     * Posting to this route starts the invoice generation for the given customer id
     *
     * @param customerId The customer id for which invoice should be generated
     * @return 200 ok if customer id is valid
     */
    @PostMapping(value = "/customers/{customerId}/invoice")
    public ResponseEntity<Void> postInvoice(@PathVariable int customerId) {
        log.info("Posted invoice generation");

        if (customerId <= 0) {
            log.info("Customer id {} is invalid", customerId);
            return ResponseEntity.badRequest().build();
        }

        log.info("Sending message to start invoice generation");
        startProducer.send(customerId);

        return ResponseEntity.ok().build();
    }

    /**
     * This service returns the invoice for the given customer id
     *
     * @param customerId The customer id for which the invoice should be returned
     * @return 200 ok if invoice for customer is found,
     * 401 if invoice is not found,
     * 500 if an error occurs
     */
    @GetMapping("/customers/{customerId}/invoice")
    public ResponseEntity<byte[]> getInvoice(@PathVariable int customerId) {
        log.info("Get invoice generation");

        if (customerId <= 0) {
            log.info("Customer id: {} is invalid", customerId);
            return ResponseEntity.badRequest().build();
        }

        try {
            String sourcePath = "Invoice " + customerId + ".pdf";
            var path = Paths.get(sourcePath);
            var invoice = new UrlResource(path.toUri());

            if (!invoice.exists()) {
                log.info("Invoice not found");
                throw new FileNotFoundException("Invoice not found");
            }

            var byteArray = invoice.getContentAsByteArray();

            log.info("Invoice found");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(byteArray);

        } catch (FileNotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
