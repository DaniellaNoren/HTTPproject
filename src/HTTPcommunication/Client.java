
package HTTPcommunication;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread{


    private Socket clientSocket;
    private PrintWriter out;
    private BufferedOutputStream outByte;
    private BufferedReader in;
    private boolean running = true;

    public Client(Socket socket, PrintWriter outChar, BufferedOutputStream out, BufferedReader in) throws IOException {
        this.clientSocket = socket;
        this.out = outChar;
        this.outByte = out;
        this.in = in;
    }

    public void run() {
        //boolean keep alive = false; Om Connection: keep-alive i HTTPRequestet ska kanske detta loopas tills Connection inte är keep-alive
        //if(HTTPRequest.getConnection() == "keep-alive") Boolean run = true;
        //while(run){ }

        // 1. Få HTTP-Requestet, parse-a det och kolla på Metod, Content-Type, om den har en body etc.
        // 2. Spara de kriterierna (kanske i ett HTTPRequest-objekt?)
        // 3. Skapa en respons som är baserad på HTTPREquest-objektet som nyss skapades

                try {
                    System.out.println(getRequest());
                    sendResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            try {
                out.close();
                in.close();
                outByte.close();
                clientSocket.close();
                System.out.println("socket closed");
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public String getRequest() throws IOException {
        StringBuilder req = new StringBuilder();
        String line = "bo";
        while((!(line = in.readLine()).isEmpty()) && in.ready()){
            req.append(line).append("\n");
        }

        return req.toString();
    }
    public void sendResponse(){
        out.write("HTTP/1.1 200 \r\n"); // Version & status code
        out.write("Content-Type: text/html\r\n"); // The type of data
        out.write("Connection: close\r\n"); // Will close stream
        out.write("\r\n"); // End of headers
        out.write("<!DOCTYPE html><html><form method='post'><input name='wow' type='text'/><input name='damn' type='text'/><input type='submit'/></form>");
        out.flush();
    }


}
