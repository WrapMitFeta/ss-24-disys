package at.poimer.invoiceservice;

import at.poimer.invoiceservice.controller.InvoiceController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceController service;

    @Test
    void testReturnsFailureIfCustomerIdIsInvalid() throws Exception {
        // given
        int customerId = 0;

        // when
        when(service.postInvoice(customerId)).thenReturn(
                ResponseEntity.badRequest().build()
        );

        // then
        this.mockMvc.perform(post("/customers/{customerId}/invoice", customerId))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testReturnsOkIfCustomerIdIsValid() throws Exception {
        // given
        int customerId = 1;

        // when
        when(service.postInvoice(customerId)).thenReturn(
                ResponseEntity.ok().build()
        );

        // then
        this.mockMvc.perform(post("/customers/1/invoice"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testReturnsInvoiceIfFound() throws Exception {
        // given
        int customerId = 1;
        byte[] pdfBytes = new byte[1];

        // when
        when(service.getInvoice(customerId)).thenReturn(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(pdfBytes)
        );

        // then
        this.mockMvc.perform(get("/customers/{customerId}/invoice", customerId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(content().bytes(pdfBytes));
    }

    @Test
    void testReturnFailureIfInvoiceNotFound() throws Exception {
        // given
        int customerId = 1;

        // when
        when(service.getInvoice(customerId)).thenReturn(
                ResponseEntity.notFound()
                        .build()
        );

        // then
        this.mockMvc.perform(get("/customers/{customerId}/invoice", customerId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testReturnFailureIfInvoiceCustomerIdIsInvalid() throws Exception {
        int customerId = 0;

        when(service.getInvoice(customerId)).thenReturn(
                ResponseEntity.notFound()
                        .build()
        );

        this.mockMvc.perform(get("/customers/{customerId}/invoice", customerId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
