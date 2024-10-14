package main.mattern;

import java.io.IOException;
import java.util.Arrays;

public class MatternNode1Impl {
    public static void main(String[] args) {
        try {
            Node node1 = new Node("node1", 5000, Arrays.asList("node1", "node2", "node3"));
            node1.startMessaging(); // Start sending messages
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

