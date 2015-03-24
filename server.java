import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Server
{
  public Server() {
    ThreadPool this.pool = new ThreadPool(5, 10);

      String line = "This order was placed for QT3000! OK?";
      String pattern = "(POST|GET) ([^ ]*) (HTTP.*)";

      // Create a Pattern object
      Pattern this.r = Pattern.compile(pattern);

  }

  public static void main(String[] args) throws Exception {
    // création de la socket
    int port = 8080;
    ServerSocket serverSocket = new ServerSocket(port);
    System.err.println("Serveur lancé sur le port : " + port);

    // repeatedly wait for connections, and process
    while (true) {
        // on reste bloqué sur l'attente d'une demande client
        Socket clientSocket = serverSocket.accept();
        System.err.println("Nouveau client connecté");

        this.pool.execute(new IndexHandler());

        // on ouvre un flux de converation

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        // chaque fois qu'une donnée est lue sur le réseau on la renvoi sur
        // le flux d'écriture.
        // la donnée lue est donc retournée exactement au même client.
        String s;
        int i = 0;
        Boolean exitflag = false;
        while ((s = in.readLine()) != null) {
            if (i == 0) {
              Matcher m = this.r.matcher(s);
              if (m.find( )) {
                String method = m.group(0);
                String url = m.group(1);
                String httpv = m.group(2);
              } else {
                System.out.println("501 Bad request");
                out.write("HTTP/1.1 501 Bad request\r\n\n");
                out.close();
                in.close();
                clientSocket.close();
                exitflag = true;
              }
            }
            System.out.println(s);
            if (s.isEmpty()) {
                break;
            }
        }

        if (exitflag) {
          continue;
        }

        if (url == "/") {
          this.pool.execute(new IndexHandler(method, url, httpv));
        } else if (url == "/home_old") {
          this.pool.execute(new HomeOldHandler(method, url, httpv));
        } else if (url == "/secret") {
          this.pool.execute(new SecretHandler(method, url, httpv));
        } else {
          // error
        }

        // on ferme les flux.
        System.err.println("Connexion avec le client terminée");
        out.close();
        in.close();
        clientSocket.close();
    }
  }
}