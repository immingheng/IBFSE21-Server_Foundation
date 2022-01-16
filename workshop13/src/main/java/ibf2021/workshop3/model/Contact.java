package ibf2021.workshop3.model;

import java.io.Serializable;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class Contact implements Serializable {
    private String HexID;
    private String name;
    private String email;
    private int phone_number;

    // Constructor
    public Contact() {
        this.HexID = HexIDFormatting();
    }

    public Contact(String name, String email, int phone_number, String HexID) {
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.HexID = HexID;
    }

    private synchronized String HexIDFormatting() {
        /*
         * String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
         * // Want HexID to be 8 digit alphanumeric abcd1234
         * for (int i = 0; i < 4; i++) {
         * char alpha = alphabets.charAt((int) Math.round((Math.random() *
         * alphabets.length())));
         * HexID = HexID + alpha;
         * }
         * for (int j = 0; j < 4; j++) {
         * Random randNum = new Random();
         * int ranValue = randNum.nextInt(10);
         * HexID = HexID + ranValue;
         * }
         * System.out.println(HexID);
         */
        SecureRandom sr = new SecureRandom();
        int num = sr.nextInt(1 * 100000000);
        String formattedHex = String.format("%08x", num);
        return formattedHex;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public void setHexID(String HexID) {
        this.HexID = HexID;
    }

    // Getters
    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public int getPhone_number() {
        return this.phone_number;
    }

    public String getHexID() {
        return this.HexID;
    }

}
