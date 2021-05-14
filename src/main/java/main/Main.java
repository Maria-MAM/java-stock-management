package main;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import entities.db.Comenzi;
import entities.db.Produse;
import entities.db.StatusComanda;
import entities.json.Comanda;
import entities.json.RezultatComanda;
import entities.xml.Stoc;
import entities.xml.Stocuri;
import services.EntityManagerService;
import services.PropertiesService;
import services.RabbitService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {

    public static PropertiesService propsSvc = PropertiesService.getInstance();
    public static RabbitService rabbitSvc = RabbitService.getInstance();
    public static EntityManagerService emSvc = EntityManagerService.getInstance();

    public static void main(String[] args) {
        // xml processing
        try {
            updateStocuriFromXmlFiles();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Eroare la cautarea fisierelor XML");
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("Eroare la parsarea de XML");
        }

        // rabbitmq
        processRabbitMessages();
    }

    private static void processRabbitMessages() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(propsSvc.getProperty("rabbitmq.host"));
        Connection connection = null;

        try {
            String comenzi_q = propsSvc.getProperty("rabbitmq.send_orders_q");
            connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(comenzi_q, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");

                Comanda com = getComandaObject(message);

                // get produs by id
                Produse p = getProdusById(com.getId_produs());

                Comenzi c = new Comenzi();
                c.setNumeClient(com.getNume_client());
                c.setProdus(p);
                if (p.getStoc() >= com.getCantitate()) {
                    c.setStatusComanda(StatusComanda.ACCEPTAT);
                    updateProduse(p.getIdProdus(), p.getStoc() - com.getCantitate());
                } else {
                    c.setStatusComanda(StatusComanda.STOC_INSUFICIENT);
                }

                saveComandaToDb(c);

                RezultatComanda rc = new RezultatComanda(c.getIdComanda(), c.getStatusComanda());
                rabbitSvc.sendMessage(new Gson().toJson(rc), propsSvc.getProperty("rabbitmq.send_results_q"));
            };
            channel.basicConsume(comenzi_q, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static Comanda getComandaObject(String message) {
        Gson gson = new Gson();
        Comanda com = gson.fromJson(message, Comanda.class);
        return com;
    }

    private static void updateStocuriFromXmlFiles() throws IOException, JAXBException {
        Stream<Path> walk = Files.walk(Path.of(propsSvc.getProperty("xml.src_folder")));

        List<Path> result = walk
                .filter(Files::isRegularFile)   // is a file
                .filter(p -> p.getFileName().toString().endsWith(".xml"))
                .collect(Collectors.toList());

        for (Path path : result) {
            File xmlFile = new File(path.toString());
            JAXBContext jaxbContext = JAXBContext.newInstance(Stocuri.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Stocuri stocuriFromFile = (Stocuri) jaxbUnmarshaller.unmarshal(xmlFile);

            for (Stoc s : stocuriFromFile.getStoc()) {
                updateProduse(s.getId_produs(), s.getStoc());
            }

            Files.move(Paths.get(xmlFile.getAbsolutePath()),
                    Paths.get(xmlFile.getAbsolutePath().replace(propsSvc.getProperty("xml.src_folder"), propsSvc.getProperty("xml.dst_folder"))));
        }
    }

    private static Produse getProdusById(int id) {
        EntityManager em = emSvc.getEm();
        em.clear();
        Query query = em.createQuery("from produse p WHERE p.idProdus = :idProdus", Produse.class);
        query.setParameter("idProdus", id);
        Produse p = (Produse) query.getResultList().stream().findFirst().orElse(null);
        return p;
    }

    private static void updateProduse(int idProdus, int stoc) {
        EntityManager em = emSvc.getEm();
        em.getTransaction().begin();
        Query query = em.createQuery("UPDATE produse p SET p.stoc = :stoc WHERE p.idProdus = :idProdus");
        query.setParameter("stoc", stoc);
        query.setParameter("idProdus", idProdus);
        int rowsUpdated = query.executeUpdate();
        em.getTransaction().commit();
    }

    private static void saveComandaToDb(Comenzi c) {
        EntityManager em = emSvc.getEm();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }
}


