package URLAccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReadTextFromURL {

  public static List<String> read(URL url) {

    List<String> words = new ArrayList<>();

    try {
      // read text returned by server
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

      String line;
      while ((line = in.readLine()) != null) {
        if (line.contains("Assignment Graded")) {
          words.add(line);
        }
      }
      in.close();

    }
    catch (MalformedURLException e) {
      System.out.println("Malformed URL: " + e.getMessage());
    }
    catch (IOException e) {
      System.out.println("I/O Error: " + e.getMessage());
    }

    return words;
  }

}
