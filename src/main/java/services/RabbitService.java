package services;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitService {

    public static RabbitService instance;
    private final ConnectionFactory factory;
    private final PropertiesService ps;

    private RabbitService() {
        ps = PropertiesService.getInstance();
        factory = new ConnectionFactory();
        factory.setHost(ps.getProperty("rabbitmq.host"));
    }

    public static RabbitService getInstance() {
        if (instance == null) {
            instance = new RabbitService();
        }
        return instance;
    }

    // FOR TESTING PURPOSES
    public static void main(String[] argv) throws Exception {
        String insufficient_message = "{\n" +
                "\t\"nume_client\" : \"Marc\",\n" +
                "\t\"id_produs\" : 1,\n" +
                "\t\"cantitate\" : 100\n" +
                "}\n";
        String ok_message = "{\n" +
                "\t\"nume_client\" : \"Gigel\",\n" +
                "\t\"id_produs\" : 2,\n" +
                "\t\"cantitate\" : 100\n" +
                "}\n";

        RabbitService svc = RabbitService.getInstance();
        svc.sendMessage(insufficient_message, "COMENZI");
        svc.sendMessage(ok_message, "COMENZI");
    }

    public void sendMessage(String message, String queue_name) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queue_name, false, false, false, null);
            channel.basicPublish("", queue_name, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
