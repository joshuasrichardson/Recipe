package Handler;

import Request.*;

import com.google.gson.Gson;

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

  /**
   * checks to see if a json string can convert to an object.
   * @param jsonString the json to convert.
   * @param objectType object to convert the json to.
   * @return whether the string can convert.
   */
  public boolean jsonMatchesObject(String jsonString, Class objectType) {
    if (objectType.equals(RegisterRequest.class)) {
      return (jsonString.contains("\"username\":") &&
              jsonString.contains("\"password\":") &&
              jsonString.contains("\"email\":") &&
              jsonString.contains("\"firstName\":") &&
              jsonString.contains("\"lastName\":"));
    }
    if (objectType.equals(LoginRequest.class)) {
      return (jsonString.contains("\"username\":") &&
              jsonString.contains("\"password\":"));
    }
    return false;
  }

  /**
   * changes a json string request into an object of its type.
   * @param jsonString the string to process.
   * @param objectType the object to change the string to.
   * @return the new request object.
   */
  public Object toObject(String jsonString, Class objectType) {
    return gson.fromJson(jsonString, objectType);
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
