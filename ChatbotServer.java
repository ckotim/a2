package a2;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Allow the Chatbot to be accessible over the network.  <br />
 * This class only handles one client at a time.  Multiple instances of ChatbotServer 
 * will be run on different ports with a port-based load balancer to handle multiple clients.
 * 
 * @author Christian Meyer
 */
public class ChatbotServer {
	
	/**
	 * The instance of the {@link Chatbot}.
	 */
	private Chatbot chatbot;

	/**
	 * The instance of the {@link ServerSocket}.
	 */
	private ServerSocket serversocket;

	/**
	 * Constructor for ChatbotServer.
	 * 
	 * @param chatbot The chatbot to use.
	 * @param serversocket The pre-configured ServerSocket to use.
	 */
	public ChatbotServer(Chatbot chatbot, ServerSocket serversocket) {
		this.chatbot = chatbot;
		this.serversocket = serversocket;
	}
	
	/**
	 * Start the Chatbot server.  Does not return.
	 */
	public void startServer() {
		while(true) handleOneClient();
	}

	/**
	 * Handle interaction with a single client.  See assignment description.
	 */
	public void handleOneClient() {
		try	{
            // String to hold response
            String response;

			// connect to client and get socket
			Socket socket = serversocket.accept();

			// get in/output streams
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

			// try to send chatbot client message
            try {
                response = chatbot.getResponse(input.readLine());
            } catch (AIException aie) {
                response = "Got AIException: " + aie.getMessage();
            }
		} catch (IOException e) {
		    // if error from socket code, print stack trace and return to startServer
            e.printStackTrace();
            return;
		}
	}
}
