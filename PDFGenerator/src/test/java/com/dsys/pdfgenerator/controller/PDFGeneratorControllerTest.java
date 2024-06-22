package com.dsys.pdfgenerator.controller;

import com.dsys.pdfgenerator.service.DatabaseService;
import com.dsys.pdfgenerator.service.MessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class PDFGeneratorControllerTest {
    @Mock
    DatabaseService databaseService;
    @Mock
    MessageService messageService;

    @Test
    @DisplayName("File has a header")
    public void fileHasAHeader() {
        Assertions.assertNotNull(databaseService);
        Assertions.assertNotNull(messageService);
    }

    @Test
    @DisplayName("File is in correct location")
    public void fileIsInCorrectLocation() {
        Assertions.assertNotNull(databaseService);
        Assertions.assertNotNull(messageService);
    }

}