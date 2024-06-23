# Setup Guide

* Install [docker desktop or cli](https://www.docker.com/get-started/)
* checkout the project
* open terminal in `ss-24-disys/project`
* run the following command `docker compose up`
* open all the projects in IntelliJ
    * DataCollectionDispatcher
    * DataCollectionReceiver
    * invoice-service
    * InvoiceUI
    * PDFGenerator
    * StationDataCollector
* run every project


# User Guide
In the InvoiceUI window there is a textfield where a customer id can be input.
Upon pressing the button `Generate Invoice` a request is sent to start the invoice pdf generation.
The InvoiceUI checks every few seconds if the generated pdf is ready to download and if, downloads it.