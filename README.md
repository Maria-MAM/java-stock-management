Create a Java application to manage the products stock based on received orders.

The application will read the initial stocks from XML files stored at a given path from the file-system. After the processing is finished, the XML files will be moved to another file-system path.

The application will receive orders using JSON messages sent to the COMENZI queue. After processing the order, it will post a JSON message to the REZULTATE_COMENZI queue. There are 2 possible order statuses: ACCEPTAT and STOC_INSUFICIENT.

All the required configuration is made by editing the application.properties file.
