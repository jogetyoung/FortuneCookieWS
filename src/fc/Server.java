package fc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException; //(throws input and output exceptions)
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    public static void main(String[] args) throws IOException, EOFException, SocketException {

        // creating server port
        int port = 3000;

        if (args.length > 0) {
            // for the terminal to read entry at args[0], the port number, as an integer
            port = Integer.parseInt(args[0]);
        }

        String file = args[1];

        Cookie cookieRandomizer = new Cookie(file);

        // creating a server specific port
        ServerSocket server = new ServerSocket(port);

        // while loop so that server does not terminate
        while (true) {
            // wait for incoming connection from Client side
            System.out.println("Waiting for connection...");

            // server needs to accept client connection requests
            // allows server to accept connection from client server
            Socket sock = server.accept();

            // after it is connected
            System.out.println("Got a new connection");

            // getting input stream (what the server receives, which is commands from client
            // server)
            InputStream inputS = sock.getInputStream();
            // to read in text files
            Reader reader = new InputStreamReader(inputS);
            // to be able to read in the file more efficiently
            BufferedReader bReader = new BufferedReader(reader);

            // getting output stream which is read back to client side
            OutputStream outputS = sock.getOutputStream();
            // write out back to client server
            Writer writer = new OutputStreamWriter(outputS);
            // write more efficiently
            BufferedWriter bWriter = new BufferedWriter(writer);

            // allows server to read input from client side
            String fromClient = bReader.readLine();
            System.out.println(">>>CLIENT: " + fromClient);

            while (fromClient != null) {
                // if server receives cmd "get-cookie" from client, this happens
                if (fromClient.equalsIgnoreCase("get-cookie")) {
                    String randomCookie = cookieRandomizer.getCookie();
                    String responseToClient = "cookie-text " + randomCookie;
                    bWriter.write(responseToClient);
                    bWriter.flush();

                    // to close the connection between server and client
                } else if (fromClient.equalsIgnoreCase("close")) {
                    System.out.println("Client disconnected");
                    break;
                } else {
                    // if input is anything other than get-cookie or close
                    System.out.println("Unknown command received");
                }

                // write result back to client
                bWriter.write(fromClient);
                bWriter.newLine();
                bWriter.flush();
                outputS.flush();
            }

        }

    }
}
