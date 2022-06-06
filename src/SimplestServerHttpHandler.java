import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class SimplestServerHttpHandler implements HttpHandler {
   static int requestCounter = 0;
   @Override
   public void handle(HttpExchange httpExchange) throws IOException {
      String[] requestParamValue = null;
      if ("GET".equals(httpExchange.getRequestMethod())) {
         requestParamValue = getRequestParams(httpExchange);
      }
      //todo POST request

      returnResponse(httpExchange, requestParamValue);
   }

   /**
    * gets params from browser address line
    * @param httpExchange
    * @return
    */
   private String[] getRequestParams(HttpExchange httpExchange) {
      String parameters = httpExchange.getRequestURI().toString().split("\\?")[1];
      String[] params = parameters.split("&");

/*      for (String param : params) {
         System.out.println(param.split("=")[0] + " -> " + param.split("=")[1]);
      }*/
      return params;
   }

   /**
    * returns response to browser
    * @param httpExchange
    * @param requestParamValue
    * @throws IOException
    */
   private void returnResponse(HttpExchange httpExchange, String[] requestParamValue) throws IOException {
      requestCounter++;
      System.out.println("Request received: " + requestCounter);
      OutputStream outputStream = httpExchange.getResponseBody();

      // build test response of all parameters
      StringBuilder response = new StringBuilder("{");
      for (String parameter : requestParamValue) {
         response.append("\n\t").append(parameter.split("=")[0] + " = " + parameter.split("=")[1]).append(",");
      }
      response.deleteCharAt(response.length() - 1);
      response.append("\n}");


      httpExchange.sendResponseHeaders(0, response.length());
      outputStream.write(response.toString().getBytes());
      outputStream.flush();
      outputStream.close();
   }
}