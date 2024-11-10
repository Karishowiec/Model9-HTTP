import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpStatusChecker {
   public String getStatusImage(int code) throws Exception {
       String url = "https://http.cat/" + code + ".jpg";

       try {
           HttpURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
           connection.setRequestMethod("HEAD");
           int responseCode = connection.getResponseCode();

           if (responseCode == HttpURLConnection.HTTP_OK) {
               return  url;
           } else {
               throw new Exception("Image not found for status code: " + code);
           }
       } catch (IOException e) {
           throw new Exception("Failed to connect to the server", e);
       }
    }

    public static void main(String[] args) {
       HttpStatusChecker checker = new HttpStatusChecker();

       try {
           System.out.println(checker.getStatusImage(200));
           System.out.println(checker.getStatusImage(404));
           System.out.println(checker.getStatusImage(10000));
       } catch (Exception e) {
           System.err.println(e.getMessage());
       }
    }
}
