package main.mattern;

import java.io.IOException;
import java.util.Arrays;

public class MatternNode2Impl {
    public static void main(String[] args) {
        try {
            Node node2 = new Node("node2", 5001, Arrays.asList("node1", "node2", "node3"));
            node2.startMessaging(); // Start sending messages
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
