import Server.ClientHandler;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static Server.Server.getClients;

public class Client implements Runnable{


    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;


    public Client() throws IOException {
        this.socket = new Socket("localhost",2137);
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void parse(String message){
        try {
            if(message.startsWith("/send")){
                System.out.println("parse test");
                String[] parts = message.split(" ", 3);
                String login = parts[1];
                String filePath = parts[2];

                this.bufferedWriter.write("#file "+login);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();

                sendFile(filePath);

            }
//            else if(message.startsWith("/login")){
//                String[] parts = message.split(" ", 2);
//                String login = parts[1];
//                this.bufferedWriter.write("#login "+ login);
//                this.bufferedWriter.flush();
          else{
                System.out.println("login");
            this.bufferedWriter.write(message);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void sendFile(String filePath) {
        try {
            System.out.println("send file test");
            File file = new File(filePath);
            FileInputStream fileIn = new FileInputStream(file);


            DataOutputStream fileOut = new DataOutputStream(socket.getOutputStream());


            long fileSize = file.length();
            fileOut.writeLong(fileSize);
            fileOut.flush();

            byte[] buffer = new byte[4096];
            int count;
            while ((count = fileIn.read(buffer)) > 0)
                fileOut.write(buffer,0,count);
            fileOut.flush();

            System.out.println("File sent successfully");
//            socket.close();
        } catch (IOException e) {
            System.err.println("Error sending file: " + e.getMessage());
        }
    }



    public void receiveFile(){
        System.out.println("rec");
        File dir = new File("images_received");
        dir.mkdir();
        LocalDate time = LocalDate.now();
        String localTime = LocalTime.now().format(DateTimeFormatter.ofPattern("_HH-mm-ss"));
        File file = new File(String.valueOf("images_received/" + time + localTime + ".png"));

        System.out.println("rec1");
        try (FileOutputStream fileOut = new FileOutputStream(file);
             DataInputStream fileIn = new DataInputStream(socket.getInputStream())) {
            System.out.println("rec2");
            byte[] buffer = new byte[4096];
            int count;
            long fileSize = fileIn.readLong();
            long bytesRead = 0;
            while (bytesRead < fileSize && (count = fileIn.read(buffer)) != -1) {
                fileOut.write(buffer, 0, count);
                bytesRead += count;
            }

            fileOut.flush();

            System.out.println("Received");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




    @Override
    public void run() {
        receiveFile();
    }


    public static void main(String[] args) throws IOException {
        Client client = new Client();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Thread(client).start();
        while(true){
            client.parse(reader.readLine());
        }

    }
}