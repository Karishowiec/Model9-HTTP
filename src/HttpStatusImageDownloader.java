import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HttpStatusImageDownloader {

    private HttpStatusChecker checker;

    public HttpStatusImageDownloader() {
        this.checker = new HttpStatusChecker() ;
    }
     public void downloadStatusImages(int code) throws Exception {
        try {
            String imageUrl = checker.getStatusImage(code);
            saveImage(imageUrl, code);
            System.out.println("Image for status code " + code +" download successfully");
        } catch (Exception e) {
            throw new Exception("Failed to download image for status code: " + code, e);
        }
     }

    private void saveImage(String imageUrl, int code) throws IOException {
        try (InputStream in = new BufferedInputStream(new URL(imageUrl).openStream());
             FileOutputStream out = new FileOutputStream(code + ".jpg")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
            }
        }
    }

    public static void main(String[] args) {
        HttpStatusImageDownloader downloader = new HttpStatusImageDownloader();

        try {
            downloader.downloadStatusImages(200);
            downloader.downloadStatusImages(404);
            downloader.downloadStatusImages(10000);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}