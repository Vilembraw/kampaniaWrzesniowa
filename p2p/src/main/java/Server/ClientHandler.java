package Server;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;

import static Server.Server.getClients;

public class ClientHandler implements Runnable {

    public BufferedReader bufferedReader;
    public BufferedWriter bufferedWriter;

    public Server server;
    public Socket socket;

    public Socket getSocket() {
        return socket;
    }

    String login;


    public String getLogin() {
        return login;
    }

    public ClientHandler(Server server, Socket socket){
        try {
            this.socket = socket;
            this.server = server;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        try {
            this.authenticate();
            String message;
            while((message= bufferedReader.readLine()) != null){
                parseMessage(message);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void authenticate() throws IOException {
        this.login = bufferedReader.readLine();

        if (this.login == null || this.login.isEmpty()) {
            throw new IOException("Login failed or was not provided.");
        }
        server.clientLogged(this);
    }

    private void leave() throws IOException {
        socket.close();
        server.clientLeft(this);
    }

    public void parseMessage(String message){
        if (message.startsWith("#file")){
            String[] parts = message.split(" ",2);
            String login = parts[1];

            try {
                File file = receiveFile();
                sendFile(file, login);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public File receiveFile() throws IOException {
        DataInputStream fileIn = new DataInputStream(socket.getInputStream());
        long fileSize = fileIn.readLong();
        System.out.println("test1");
        File dir = new File("Clients/"+this.getLogin());
        dir.mkdirs();

        LocalDate time = LocalDate.now();
        File file = new File(String.valueOf("Clients/"+this.getLogin()+"/" + time + ".jpg"));
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }


    public void sendFile(File file, String login) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            List<ClientHandler> clients = getClients();
            DataOutputStream fileOut = null;
            for(ClientHandler client : clients){
                if(client.getLogin().equals(login)){
                    fileOut = new DataOutputStream(client.getSocket().getOutputStream());
                    break;
                }
            }

            if(fileOut == null){
                return;
            }


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
