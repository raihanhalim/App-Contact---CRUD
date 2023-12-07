package org.example;

import com.mongodb.client.*;
import org.bson.Document;
import java.util.Scanner;

public class Main {
    private static final String DATABASE_NAME = "Contact";
    private static final String COLLECTION_NAME = "col";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MongoClient mongoClient = MongoClients.create("mongodb+srv://rehanhalim56:contactray@contact.ulda5np.mongodb.net/?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        while (true) {
            System.out.println("-------------------------");
            System.out.println("      Menu  Contact      ");
            System.out.println("-------------------------");
            System.out.println("[1] Add Contact");
            System.out.println("[2] Read Contact");
            System.out.println("[3] Update Contact");
            System.out.println("[4] Delete Contact");
            System.out.println("[5] Exit");
            System.out.println("-------------------------");
            System.out.print("[>] Choice Menu [1-5] : ");
            int choice = scanner.nextInt();
            System.out.println("-------------------------");
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addContact(scanner, collection);
                    break;
                case 2:
                    readContact(collection);
                    break;
                case 3:
                    updateContact(scanner, collection);
                    break;
                case 4:
                    deleteContact(scanner, collection);
                    break;
                case 5:
                    System.out.println("Success Exit To App Contact");
                    System.out.println("-------------------------");
                    mongoClient.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Option Is Not Available");
            }
        }
    }

    private static void addContact(Scanner scanner, MongoCollection<Document> collection) {
        System.out.println("-------------------------");
        System.out.println("       Add Contact       ");
        System.out.println("-------------------------");
        System.out.print("Nama          : ");
        String nama = scanner.nextLine();
        System.out.print("Email         : ");
        String email = scanner.nextLine();
        System.out.print("No. Telepon   : ");
        String noTelepon = scanner.nextLine();

        Document document = new Document("Nama", nama).append("Email", email).append("No. Telepon", noTelepon);
        collection.insertOne(document);
        System.out.println("-------------------------");
        System.out.println("Contact Added Successfully");
        System.out.println("-------------------------");
    }

    private static void readContact(MongoCollection<Document> collection) {
        FindIterable<Document> documents = collection.find();

        System.out.println("-------------------------");
        System.out.println("      List  Contact      ");
        System.out.println("-------------------------");

        for (Document document : documents) {
            System.out.println("Nama        : " + document.getString("Nama"));
            System.out.println("Email       : " + document.getString("Email"));
            System.out.println("No. Telepon : " + document.getString("No. Telepon"));
            System.out.println("-------------------------");
        }
    }

    private static void updateContact(Scanner scanner, MongoCollection<Document> collection) {
        System.out.println("-------------------------");
        System.out.println("     Update  Contact     ");
        System.out.println("-------------------------");
        System.out.print("Nama Contact : ");
        String targetNama = scanner.nextLine();
        System.out.println("-------------------------");

        System.out.print("New Nama          : ");
        String newNama = scanner.nextLine();
        System.out.print("New Email         : ");
        String newEmail = scanner.nextLine();
        System.out.print("New No. Telepon   : ");
        String newNoTelepon = scanner.nextLine();

        Document filter = new Document("Nama", targetNama);
        Document update = new Document("$set", new Document("Nama", newNama).append("Email", newEmail).append("No. Telepon", newNoTelepon));
        collection.updateOne(filter, update);
        System.out.println("-------------------------");
        System.out.println("Contact Updated Successfully");
    }

    private static void deleteContact(Scanner scanner, MongoCollection<Document> collection) {
        System.out.println("-------------------------");
        System.out.println("     Delete  Contact     ");
        System.out.println("-------------------------");
        System.out.print("Nama Contact : ");
        String targetNama = scanner.nextLine();

        Document filter = new Document("Nama", targetNama);
        collection.deleteOne(filter);
        System.out.println("-------------------------");
        System.out.println("Contact Deleted Successfully");
    }
}