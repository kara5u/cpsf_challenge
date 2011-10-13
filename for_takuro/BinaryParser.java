/**
 * BinaryParser.java
 *
 * @auther Yutaka Karatsu 'karasu@ht.sfc.keio.ac.jp'
 * @date 2011.10.09
 *
 * encode a binary string to a string format value
 */

import java.util.ArrayList;
import java.lang.StringBuffer;
import java.nio.ByteBuffer;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException; 
import javax.crypto.NoSuchPaddingException;

public class BinaryParser { 
    
    /** binary string of challenge 1 */
    public String binaryString = "010010000110010101101100011011000110111100101100010101110110111101110010011011000110010000100001";

    /** binary string encrypted by AES */
    public String aesString = 
        "0001010111100111010111110110001100111010011010101011010101100011101100101110101110011001110001000101000111101001100111001010001111110100100010001100100111100000000111011100010100001011110000000100011100010100011111100010111111110000101110001101001010111001";

    /** AES key */
    public String aesKey = "thisistestkey123";


    private byte[] transformToBytes(char[] binaryArray) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(binaryArray.length/8);
        for (int i = 0; (i+8) <= binaryArray.length; i+=8) {
            int d = 1;
            int r = 0;
            for (int j = i+7; j >= i; --j) {
                char bit = binaryArray[j];
                if (bit == '1') {
                    r += d;
                } else {
                }
                d *= 2;
            }
            byteBuffer.put((byte)r);
        }
        return byteBuffer.array(); 
    }

    private byte[] decodeAES(byte[] code, byte[] keycode) throws NoSuchAlgorithmException, InvalidKeyException, 
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
        byte[] decodedResult = null;
        Key key = new SecretKeySpec(keycode, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        decodedResult = cipher.doFinal(code);
        return decodedResult;
    }
    
    public String transformToString(String binary) {
        // i dont use Integer.parseInt()!!
        StringBuffer stringBuffer = new StringBuffer();
        char[] array = binary.toCharArray();
        byte[] byteArray = transformToBytes(array);
        for (int i = 0; i < byteArray.length; ++i) {
            stringBuffer.append((char)byteArray[i]); // Implicit cast is so guilty...
        }
        return stringBuffer.toString();
    }
    
    public String decryptAESString(String code, String keyString) throws NoSuchAlgorithmException, InvalidKeyException, 
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
        byte[] keycode = keyString.getBytes();
        byte[] src = transformToBytes(code.toCharArray());
        byte[] decodedCode = decodeAES(src, keycode);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < decodedCode.length; ++i) {
            stringBuffer.append((char)decodedCode[i]); // Implicit cast is so guilty...
        }
        return stringBuffer.toString();
    }


    public static void main(String args[]) {
        BinaryParser binaryParser = new BinaryParser();
        String transformResult = binaryParser.transformToString(binaryParser.binaryString);
        System.out.println(transformResult);
        try {
            String decryptResult = binaryParser.decryptAESString(binaryParser.aesString, binaryParser.aesKey);
            System.out.println(decryptResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
