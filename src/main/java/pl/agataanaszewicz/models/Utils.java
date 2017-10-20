package pl.agataanaszewicz.models;

import javafx.scene.control.Alert;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static void createSimpleDialog(String name, String header, String message){
        Alert alert  = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(name);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();
    }

    public static String shaHash(String message){
        try {
            MessageDigest sha2 = MessageDigest.getInstance("SHA-256");
            byte[] bytesOfMessage = message.getBytes();
            byte[] byteOfCryptoMessage = sha2.digest(bytesOfMessage);

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < byteOfCryptoMessage.length; i++) {
                stringBuilder.append(Integer.toHexString(0xFF & byteOfCryptoMessage[i]));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
