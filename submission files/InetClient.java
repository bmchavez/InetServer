import java.io.*;   // Get the Input Output libraries
import java.net.*;  // Get the Java netowrking libraries

public class InetClient {
  public static void main (String args[]) {
    String serverName;
    if (args.length < 1) serverName = "localhost";
    else serverName = args[0];

    System.out.println
      ("Benjamin Chavez's Inet Client, 1.0.\n");
    System.out.println("Using server: " + serverName + ", Port: 1565");
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    try {
      String name;
      do {
        System.out.print
          ("Enter a hostname or an IP address, (quit) to end: ");
        System.out.flush ();
        name = in.readLine ();
        if (name.indexOf("quit") < 0);
          getRemoteAddress(name, serverName);
      } while (name.indexOf("quit") < 0); // i.e. quitsomething.com**
      System.out.println ("Cancelled by user request.");
    } catch (IOException x) {x.printStackTrace ();}
  }

  // Formatting the IP Address
  static String toText (byte ip[]) {
    // Make portable for 128 bit format
    StringBuffer result = new StringBuffer ();
    for (int i = 0; i < ip.length; ++ i) {
      if (i > 0) result.append (".");
      result.append (0xff & ip[i]);
    }
    return result.toString ();
  }

  static void getRemoteAddress (String name, String serverName){
    Socket sock;
    BufferedReader fromServer;
    PrintStream toServer;
    String textFromServer;

    try {
      /* Oepn a connection to a server port. Notes on choosing a port number in server file */
      sock = new Socket(serverName, 1565);

      // Create filter I/O streams for the socket:
      fromServer =
        new BufferedReader(new InputStreamReader(sock.getInputStream()));
      toServer = new PrintStream(sock.getOutputStream());

      // Send machine name or IP address to server:
      toServer.println(name); toServer.flush();

      // Read two or three lines of response from the server,
      // and block while synchronously waiting:
      for (int i = 1; i <=3; i++) {
        textFromServer = fromServer.readLine();
        if (textFromServer != null) System.out.println(textFromServer);
      }
      sock.close();
    } catch (IOException x) {
      System.out.println ("Socket error.");
      x.printStackTrace ();
    }
  }
}
