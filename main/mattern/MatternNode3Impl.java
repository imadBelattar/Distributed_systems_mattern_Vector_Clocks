package main.mattern;

import java.io.IOException;
import java.util.Arrays;

public class MatternNode3Impl {
    public static void main(String[] args) {
        try {
            Node node3 = new Node("node3", 5002, Arrays.asList("node1", "node2", "node3"));
            node3.startMessaging(); // Start sending messages
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
