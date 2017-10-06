import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
  public static void main(String[] args) {
    try {
      Socket socket = new Socket("18.221.102.182", 38001);
      Runnable listener = () -> {
        try {
          while (true) {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            System.out.println(br.readLine());
          }
        } catch (Exception e) { }
      };

      Runnable speaker = () -> {
        try {
          while (true) {
            Scanner kb = new Scanner(System.in);
            String s = kb.nextLine();
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os, true, "UTF-8");
            out.println(s);
          }
        } catch (Exception e) { }
      };
      
      Thread listenerThread = new Thread(listener);
      Thread speakerThread = new Thread(speaker);
      listenerThread.start();
      speakerThread.start();
      System.out.println("The first line you send will be your username.");
    } catch (Exception e) { }
  }
}