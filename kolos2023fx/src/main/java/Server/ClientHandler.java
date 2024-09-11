package Server;

import javafx.scene.input.DataFormat;

import javax.imageio.ImageIO;
import javax.imageio.spi.ImageReaderSpi;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static Server.ClientHandler.Persistence.init;

public class ClientHandler{

    ServerApp server;
    Socket socket;



    public ClientHandler(ServerApp server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }
//    public void handleClient() {
//        new Thread(() -> {
//            try (DataInputStream fileIn = new DataInputStream(socket.getInputStream());
//                 DataOutputStream fileOut = new DataOutputStream(socket.getOutputStream())) {
//                receiveFile(fileIn);
//
//            } catch (IOException e) {
//                System.err.println("Error handling client: " + e.getMessage());
//            }
//        }).start();
//    }

    public void receiveFile() throws IOException {
        DataInputStream fileIn = new DataInputStream(socket.getInputStream());
        long fileSize = fileIn.readLong();
        System.out.println("test1");
        File dir = new File("images");
        dir.mkdir();
        LocalDate time = LocalDate.now();
        File file = new File(String.valueOf("images/" + time + ".jpg"));
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            byte[] buffer = new byte[4096];
            long bytesRead = 0;
            int count;

            while (bytesRead < fileSize && (count = fileIn.read(buffer)) != -1) {
                fileOut.write(buffer, 0, count);
                bytesRead += count;
                System.out.println("test2");
            }

            fileOut.flush();
            fileOut.close();
            System.out.println("Received");

            boxblur(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void boxblur(File file){
        try {
            long startTime = System.currentTimeMillis();;
            int radius = ServerController.getValue();
//            System.out.println(radius);

            FileInputStream fis = new FileInputStream(file);
            BufferedImage image = ImageIO.read(fis);
            BufferedImage result = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
            if (image == null) {
                System.out.println("Error: Unable to read image. ImageIO returned null.");
                return;
            }


            int threadsCount = Runtime.getRuntime().availableProcessors();
            Thread[] threads;
            threads = new Thread[threadsCount];
            int chunk = image.getHeight()/threadsCount;
            for(int i = 0; i < threadsCount; i++){
                int begin = i*chunk;
                int end;
                if(i == threadsCount - 1){
                    end = image.getHeight();
                }else {
                    end = (i+1)*chunk;
                }
                threads[i] = new Thread(new boxblurrWorker(begin, end, result, image, radius));
                threads[i].start();
            }

            for(int i = 0; i < threadsCount; i++){
                threads[i].join();
            }

            long endTime = System.currentTimeMillis();

            long resultTime = (endTime-startTime);
//            System.out.println(resultTime);
            File dir1 = new File("blurred");
            dir1.mkdir();
            String localTime = LocalTime.now().format(DateTimeFormatter.ofPattern("_HH-mm-ss"));
            File resultFile = new File("blurred/"+LocalDate.now()+localTime+".png");
            ImageIO.write(result, "png",resultFile);
            System.out.println("Blurred Saved");
            databaseWork(resultFile.getPath(),radius,resultTime);
            System.out.println("Db done");
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }


    private void databaseWork(String path,int size, long delay){
        try {
            String pathDb = "images.db";
            Connection connection = DriverManager.getConnection("jdbc:sqlite:"+pathDb);
            init();
            String insertSQL = "INSERT INTO account(path, size, delay) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            statement.setString(1,path);
            statement.setInt(2,size);
            statement.setInt(3, (int) delay);

            statement.executeUpdate();
            sendFile(path);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static class Persistence {
        public static void init() {
            try {
                String createSQLTable = "CREATE TABLE IF NOT EXISTS account( " +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "path TEXT NOT NULL," +
                        "size INTEGER NOT NULL," +
                        "delay INTEGER NOT NULL)";
                String path = "images.db";
                Connection connection = DriverManager.getConnection("jdbc:sqlite:" + path);
                PreparedStatement statement = connection.prepareStatement(createSQLTable);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public void sendFile(String path) {
        try {
            System.out.println(path);
            System.out.println("test");
            File file = new File(path);
            FileInputStream fileIn = new FileInputStream(file);
            DataOutputStream fileOut = new DataOutputStream(socket.getOutputStream());
//to samo meta dane
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
}


