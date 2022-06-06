import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
   public static void main(String[] args) throws IOException {
      System.out.println("Password -> " + getHash("Password"));
      System.out.println("password -> " + getHash("password"));

      System.out.println();

      simplestServerExample();
   }

   /**
    * Simplest server example using GET method
    *
    * @throws IOException
    */
   public static void simplestServerExample() throws IOException {
      HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
      server.createContext("/back", new SimplestServerHttpHandler());
      server.start();
      System.out.println(System.lineSeparator() + "\033[1;32mServer started at:\tlocalhost:8001\u001B[0m");

      /*
      ThreadPoolExecutor threadPoolExecutor = null;
      server.setExecutor(threadPoolExecutor);
      threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
       */
   }

   /**
    * Gets hash of string using SHA-256
    *
    * @param string to be hashed
    *
    * @return hashed string value
    */
   public static String getHash(String string) {
      MessageDigest md = null;
      try {
         md = MessageDigest.getInstance("SHA-256");
      } catch (NoSuchAlgorithmException e) {
         throw new RuntimeException(e);
      }
      byte[] digest = md.digest(string.getBytes());

      return new BigInteger(1, digest).toString(16);
   }
}