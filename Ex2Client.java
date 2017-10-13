import java.io.*;
import java.net.*;
import java.util.zip.CRC32;
import java.nio.ByteBuffer;

public class Ex2Client {
  public static void main(String[] args) throws Exception {
    try {
      Socket socket = new Socket("18.221.102.182", 38102);
      System.out.println("Connected to server.");

      InputStream is = socket.getInputStream();
      InputStreamReader isr = new InputStreamReader(is, "UTF-8");
      BufferedReader br = new BufferedReader(isr);

      OutputStream os = socket.getOutputStream();
      PrintStream out = new PrintStream(os, true, "UTF-8");

      byte[] bytes = new byte[100];

      System.out.println("Received bytes:");

      for (int i = 0; i < 100; i++) {
        int b1 = br.read();
        int b2 = br.read();
        String byte1 = "";
        String byte2 = "";

        if (b1 > 9) {
          switch(b1) {
            case 10:
              byte1 = "a";
              break;
            case 11:
              byte1 = "b";
              break;
            case 12:
              byte1 = "c";
              break;
            case 13:
              byte1 = "d";
              break;
            case 14:
              byte1 = "e";
              break;
            case 15:
              byte1 = "f";
              break;
          }
        } else {
          byte1 = Integer.toString(b1);
        }

        if (b2 > 9) {
          switch(b2) {
            case 10:
              byte2 = "a";
              break;
            case 11:
              byte2 = "b";
              break;
            case 12:
              byte2 = "c";
              break;
            case 13:
              byte2 = "d";
              break;
            case 14:
              byte2 = "e";
              break;
            case 15:
              byte2 = "f";
              break;
          }
        } else {
          byte2 = Integer.toString(b2);
        }

        String finalByte = byte1 + byte2;
        bytes[i] = (byte) Integer.parseInt(finalByte, 16);
        System.out.print(finalByte);
        if (i != 0 && (i + 1) % 10 == 0)
          System.out.println();
      }

      CRC32 crc32 = new CRC32();
      crc32.update(bytes);
      int crcCode = (int) crc32.getValue();
      System.out.println("Generated CRC32: " + crcCode);
      ByteBuffer output = ByteBuffer.allocate(4);
      output.putInt(crcCode);
      byte[] outArray = output.array();
      out.write(outArray);
      if (is.read() == 1)
        System.out.println("Response good");
      else
        System.out.println("Response bad");
      socket.close();
      System.out.println("Disconnected from server\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}