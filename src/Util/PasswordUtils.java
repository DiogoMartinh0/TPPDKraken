package Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PasswordUtils {
    private static ArrayList<Character> ProhibitedChars = new ArrayList<>(Arrays.asList('»', '«', '/'));
    private static Random rnd = new Random();

    public static String encriptaPassword(String t){
        int size = t.length();
        int tamanhoBloco = 3;


        ArrayList<Character> stringPartida = new ArrayList<>();
        for(char ch : t.toCharArray()) stringPartida.add(ch);


        int i, j;
        for(i = 0, j = 0; j < size; i += tamanhoBloco, j += tamanhoBloco){
            stringPartida.add(i, ProhibitedChars.get(rnd.nextInt(3)));
            j--;
        }

        StringBuilder sb = new StringBuilder();
        for(Character ch : stringPartida)
            sb.append(ch);

        return sb.toString();
    }

    public static String desencriptaPassword(String t){
        return t.replaceAll("»", "").replaceAll("«", "").replaceAll("/", "");
    }
}
