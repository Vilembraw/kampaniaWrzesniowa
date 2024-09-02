package Server;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class ServerApplication {
	private ServerSocket serverSocket;


	public static void main(String[] args) throws IOException {
		SpringApplication.run(ServerApplication.class, args);

	}
	@PostConstruct
	public void run() {
		try {
			serverSocket = new ServerSocket(2135);
			new Thread(this::listen).start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}



	public void listen(){
		try {
			System.out.println("Server started");
			Socket clientSocket = serverSocket.accept();
			System.out.println("Client connected");
			InputStream inputStream = clientSocket.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			String message;
			message = bufferedReader.readLine();
			if(!message.equals("haslo")){
				clientSocket.close();
				System.out.println("dc 1");
			}

			while((message = bufferedReader.readLine()) != null ){
				System.out.println(message);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
//        System.out.println("Client disconnected");

	}



}
