package fc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        // created port
        String ip = "placeholder";
        int port = 3000;
        if (args.length > 0) {
            String[] split = args[0].split(":");
            ip = split[0];
            port = Integer.parseInt(split[1]);

        }

        System.out.println("Connecting to server...");

        // new socket to connect to host and specified port
        Socket sock = new Socket(ip, port);

        System.out.println("Connected!");

        Scanner scanner = new Scanner(System.in);

        OutputStream outputS = sock.getOutputStream();
        Writer writer = new OutputStreamWriter(outputS);
        BufferedWriter bWriter = new BufferedWriter(writer);

        InputStream inputS = sock.getInputStream();
        Reader reader = new InputStreamReader(inputS);
        BufferedReader bReader = new BufferedReader(reader);

        while (true) {
            System.out.print("Input: ");
            String theMessage = scanner.nextLine();

            if (theMessage == null || theMessage.trim().isEmpty()) {
                System.out.println("Invalid input, try again.");
                continue; // move onto next if loop if this doesnt apply
            }

            if (theMessage.equalsIgnoreCase("close")) {
                break;
            }

            if (!theMessage.equalsIgnoreCase("get-cookie")) {
                System.out.println("Invalid command. Enter get-cookie or close");
                continue;
            }
            
            // write the message out
            bWriter.write(theMessage);
            bWriter.newLine();
            bWriter.flush();
            outputS.flush();

            String fromServer = bReader.readLine();

            // to remove "cookie-text" from front of the sentence
            if (fromServer != null && fromServer.startsWith("cookie-text")) {
                System.out.println(">>>SERVER: " + fromServer.substring("cookie-text".length(), (fromServer.length()-"get-cookie".length())));
            } else {
                System.out.println("Invalid response received");
            }

       

        }

    }

}
//fromServer.substring("cookie-text".length()