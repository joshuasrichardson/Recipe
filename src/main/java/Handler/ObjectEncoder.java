package Handler;

import com.google.gson.Gson;
import Request.*;

import java.io.*;

/**
 * a class to convert Json string requests from the web to a Request object.
 */
public class ObjectEncoder {

  private Gson gson = new Gson();

  public String addAuthTokenToJson(String authToken, String json) throws IllegalArgumentException {
    StringBuilder stringBuilder = new StringBuilder(json);
    if (json.length() > 1 && json.charAt(0) == '{') {
      stringBuilder.insert(json.indexOf("{") + 2, "\"authToken\":\"" + authToken + "\",\n");
    }
    else throw new IllegalArgumentException("You cannot add an authorization token to an empty request.");
    return stringBuilder.toString();
  }

  public Object toObject(String jsonString, Object object) throws HandlerException {
    if (object instanceof RegisterRequest) return gson.fromJson(jsonString, RegisterRequest.class);
    if (object instanceof IngredientRequest) return gson.fromJson(jsonString, IngredientRequest.class);
    throw new HandlerException();
  }

  /**
   * reads a string from an InputStream.
   * @param inputStream the InputStream to read.
   * @return the string version of the input
   * @throws IOException
   */
  public String readString(InputStream inputStream) throws IOException {
    StringBuilder sb = new StringBuilder();
    InputStreamReader sr = new InputStreamReader(inputStream);
    char[] buf = new char[1024];
    int len;
    while ((len = sr.read(buf)) > 0) {
      sb.append(buf, 0, len);
    }
    return sb.toString();
  }

  /**
   * writes a string to an output stream
   * @param str
   * @param os
   * @throws IOException
   */
  public void writeString(String str, OutputStream os) throws IOException {
    OutputStreamWriter sw = new OutputStreamWriter(os);
    sw.write(str);
    sw.flush();
  }
}
