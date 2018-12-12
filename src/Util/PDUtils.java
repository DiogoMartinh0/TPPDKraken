package Util;

public class PDUtils {
    public static void printLineSync(String s) {
        synchronized (System.out) {
            System.out.println(s);
        }
    }
}
