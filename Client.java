import java.io.*;
import java.net.*;
import java.util.*;

// Creo una classe Client
class Client {
	public static void main(String[] args)
	{
		// Stabilisco una connessione attraverso host e porta
		try (Socket socket = new Socket("localhost", 2000)) {
			
			// Scrivo al Server
			PrintWriter out = new PrintWriter(
				socket.getOutputStream(), true);

			// Il Server legge
			BufferedReader in
				= new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			Scanner sc = new Scanner(System.in);
			String line = null;

			while (!"exit".equalsIgnoreCase(line)) {
				
				// Lettura da parte dell'utente
				line = sc.nextLine();

				// Invio input dall'utente al server
				out.println(line);
				out.flush();

				System.out.println("Server replied "
								+ in.readLine());
			}
			
			sc.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
