package Client;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Client {

    private static Socket socket;

    public Client() throws IOException {
        this.socket = new Socket("localhost",2137);
    }




    public void receiveFile(){
        File dir = new File("images_received");
        dir.mkdir();
        LocalDate time = LocalDate.now();
        String localTime = LocalTime.now().format(DateTimeFormatter.ofPattern("_HH-mm-ss"));
        File file = new File(String.valueOf("images_received/" + time + localTime + ".png"));


        try (FileOutputStream fileOut = new FileOutputStream(file);
             DataInputStream fileIn = new DataInputStream(socket.getInputStream())) {
            byte[] buffer = new byte[4096];
            int count;
            long fileSize = fileIn.readLong();
            long bytesRead = 0;
            while (bytesRead < fileSize && (count = fileIn.read(buffer)) != -1) {
                fileOut.write(buffer, 0, count);
                bytesRead += count;
            }

            fileOut.flush();
            fileOut.close();
//            socket.close();
            System.out.println("Received");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendFile(String path) {
        try {
            File file = new File(path);
            FileInputStream fileIn = new FileInputStream(file);
            DataOutputStream fileOut = new DataOutputStream(socket.getOutputStream());
//wazne meta dane by nie bylo zepsutej petli
            long fileSize = file.length();
            fileOut.writeLong(fileSize);
            fileOut.flush();

            byte[] buffer = new byte[4096];
            int count;
            while ((count = fileIn.read(buffer)) > 0)
                fileOut.write(buffer,0,count);
            fileOut.flush();
            fileIn.close();
            System.out.println("File sent successfully");
//            socket.close();
        } catch (IOException e) {
            System.err.println("Error sending file: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        try {
            Client client = new Client();
//            new Thread(() -> {
//                client.sendFile("random.jpg");
//            }).start();
//
//            new Thread(() -> {
//                client.receiveFile();
//            }).start();

            client.sendFile("random.jpg");
            client.receiveFile();

        } catch (IOException e) {
            System.out.println(e);
        }


    }
}
