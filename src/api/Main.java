package api;

import io.indico.api.utils.IndicoException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        /*try {
//            SortImages.sort("./photo_src/foto", "./photo_src/result/");
            SortImages.getTopFiveIndicoMatches("/home/albert/Desktop/JavaFX/foto/kkott.jpg");
        } catch(IOException e) {
            e.printStackTrace();
        } catch(IndicoException e) {
            e.printStackTrace();
        }*/
        SortImages.getTopFiveIndicoMatches("/home/albert/Desktop/JavaFX/foto/kkott.jpg");
    }
}
