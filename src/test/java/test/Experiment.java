package test;

import URLAccess.ReadTextFromURL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Experiment {
  List<String> words;
  URL url;

  @Test
  void checkFirstLine() {
    try {
      url = new URL("file:///C:/Users/joshu/MyFavoriteStuff.html");
      words = ReadTextFromURL.read(url);
      assertEquals("\t<h1>This is a website about my favorite stuff</h1>", words.get(7));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void checkHasPhrase() {
    try {
      url = new URL("https://mail.google.com/mail/u/0/#inbox/FMfcgxwLsSdBDXtJzdRgWhSkFNCpkVbb");
      words = ReadTextFromURL.read(url);
      assertNotNull(words);
      System.out.println(words.toString());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

}
