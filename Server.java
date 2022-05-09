import java.io.*;
import java.net.*;

// Creo una classe Server
class Server {
	public static void main(String[] args)
	{
		ServerSocket server = null;

		try {

			// server è in ascolto sulla porta 2000
			server = new ServerSocket(2000);
			server.setReuseAddress(true);

			// Richiesta da parte del Client
			while (true) {
    
				Socket client = server.accept();

				// Client si connette al Server
				System.out.println("New client connected"
								+ client.getInetAddress()
										.getHostAddress());

				// Crep un nuovo Thread
				ClientHandler clientSock
					= new ClientHandler(client);

				// Il Thread gestirà il Client
				new Thread(clientSock).start();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (server != null) {
				try {
					server.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static class ClientHandler implements Runnable {
		private final Socket clientSocket;

		// Costruttore
		public ClientHandler(Socket socket)
		{
			this.clientSocket = socket;
		}

		public void run()
		{
			PrintWriter out = null;
			BufferedReader in = null;
			try {

				out = new PrintWriter(
					clientSocket.getOutputStream(), true);

				in = new BufferedReader(
					new InputStreamReader(
						clientSocket.getInputStream()));

				String line;
				while ((line = in.readLine()) != null) {

					// Messaggio ricevuto
					System.out.printf(
						" Sent from the client: %s\n",
						line);
					out.println(line);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
						clientSocket.close();
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
