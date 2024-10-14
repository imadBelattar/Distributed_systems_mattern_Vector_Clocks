package main.mattern;

import java.io.*;
import java.net.*;
import java.util.*;

class Node {
    private final String label;
    private final LinkedHashMap<String, Integer> vectorClock;
    private final ServerSocket serverSocket;
    private final List<String> otherNodes;
    private final Random random = new Random();

    public Node(String label, int port, List<String> allNodes) throws IOException {
        this.label = label;
        this.vectorClock = new LinkedHashMap<>();
        for (String node : allNodes) {
            vectorClock.put(node, 0);
        }
        this.serverSocket = new ServerSocket(port);
        this.otherNodes = new ArrayList<>(allNodes);
        this.otherNodes.remove(label);
        listen();
    }

    public String getLabel() {
        return label;
    }

    public Map<String, Integer> getVectorClock() {
        return vectorClock;
    }

    public void sendMessage(String targetNode, int targetPort) throws IOException {
        // Increment this node's clock before sending the message
        vectorClock.put(label, vectorClock.get(label) + 1);

        try (Socket socket = new Socket(targetNode, targetPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(vectorClock);
            System.out.println(label + " sent message to " + targetNode);
        } catch (IOException e) {
            System.err.println("Failed to send message to " + targetNode + ":" + targetPort + ". Error: " + e.getMessage());
        }
    }


    public void receiveMessage(Map<String, Integer> receivedVectorClock) {
        // Update this node's vector clock based on the received clock
        for (Map.Entry<String, Integer> entry : receivedVectorClock.entrySet()) {
            String nodeLabel = entry.getKey();
            int receivedCount = entry.getValue();
            vectorClock.put(nodeLabel, Math.max(vectorClock.get(nodeLabel), receivedCount));
        }
        // Increment this node's clock after receiving the message
        vectorClock.put(label, vectorClock.get(label) + 1);
    }

    private void listen() {
        // Start a thread that listens for incoming messages
        new Thread(() -> {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String line = in.readLine();
                    if (line != null) {
                        Map<String, Integer> receivedVectorClock = parseVectorClock(line);
                        receiveMessage(receivedVectorClock);
                        System.out.println(label + " received message and updated vector clock: " + vectorClock);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Map<String, Integer> parseVectorClock(String vectorClockString) {
        // Parse the received vector clock string into a LinkedHashMap to maintain order
        LinkedHashMap<String, Integer> clock = new LinkedHashMap<>();
        String[] entries = vectorClockString.replace("{", "").replace("}", "").split(", ");
        for (String entry : entries) {
            String[] keyValue = entry.split("=");
            clock.put(keyValue[0], Integer.parseInt(keyValue[1]));
        }
        return clock;
    }

    public void startMessaging() {
        new Thread(() -> {
            while (true) {
                try {

                    String targetNode = otherNodes.get(random.nextInt(otherNodes.size()));
                    int targetPort = 5000 + Integer.parseInt(targetNode.replace("node", "")) - 1;
                    System.out.println("targetNode: " + targetNode + ", targetPort: " + targetPort);
                    sendMessage(targetNode, targetPort);
                    System.out.println(label + " vector clock: " + vectorClock);

                    Thread.sleep(4000);

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
