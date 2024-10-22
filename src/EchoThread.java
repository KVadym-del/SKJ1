import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class EchoThread extends Thread {
    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        System.out.println("Client connected from " + socket.getInetAddress());
        String line;
        while (true) {
            try {
                line = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    System.out.println("Client disconnected from " + socket.getInetAddress());
                    return;
                } else if (line.equalsIgnoreCase("GET / HTTP/1.1")) {
                    out.writeBytes("HTTP/1.1 200 OK\n\r");
                    out.writeBytes("Content-Type: text/html\n\r");
                    out.writeBytes("\n\r");
                    out.writeBytes("<html><head><title>Test</title></head><body><h1>Test</h1></body></html>\n\r");
                    out.flush();
                    System.out.println("Sent: " + line);
                    socket.close();
                    return;
                }
                else {
                    out.writeBytes(line + "\n\r");
                    out.flush();
                    System.out.println("Sent: " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    protected Socket socket;
}