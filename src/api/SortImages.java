package api;

import io.indico.Indico;
import io.indico.api.results.BatchIndicoResult;
import io.indico.api.results.IndicoResult;
import io.indico.api.utils.IndicoException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class SortImages {
    private static String apiKey = "5e4416ba27b9ea6066e8f688ea35e45c";

    private static List<Map<String, Double>> indicoResults(String from) {
        System.out.println(".......... DOWNLOADING FROM INDICO ..........");
        List<Map<String, Double>> results = null;
        try {
            Indico indico = null;
            indico = new Indico(apiKey);

            File folder = new File(from);
            File[] listOfFiles = folder.listFiles();

            BatchIndicoResult multiple = indico.imageRecognition.predict(listOfFiles);
            results = multiple.getImageRecognition();
        } catch(IndicoException e) {
            System.out.println("Indico API fail...");
            e.printStackTrace();
        } catch(IOException e) {
            System.out.println("IO Exception...");
            e.printStackTrace();
        }
        return results;
    }

    private static Map<String, Double> indicoResult(String url) {
        System.out.println(".......... DOWNLOADING FROM INDICO ..........");
        Map<String, Double> result = null;
        try {
            Indico indico = null;
            indico = new Indico(apiKey);
            IndicoResult multiple = indico.imageRecognition.predict(url);
            result = multiple.getImageRecognition();

        } catch(IndicoException e) {
            System.out.println("Indico API fail...");
            e.printStackTrace();
        } catch(IOException e) {
            System.out.println("IO Exception...");
            e.printStackTrace();
        }
        return result;
    }

    public static void sortImagesByIndico(String from, String to) throws IOException {
        List<Map<String, Double>> results = indicoResults(from);

        int index = 0;

        for (Map<String, Double> res : results) {
            Map.Entry<String, Double> maxEntry = null;

            for (Map.Entry<String, Double> entry : res.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }

            File folder = new File(from);
            File[] listOfFiles = folder.listFiles();

            new File(to + maxEntry.getKey()).mkdirs();
            Files.copy(listOfFiles[index].toPath(), new File(to + maxEntry.getKey() + "/" + listOfFiles[index].getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
            index++;
        }
    }

    public static Map<String, Double> getTopFiveIndicoMatches(String url) {
        Map<String, Double> result = indicoResult(url);
        Map<String, Double> topFive = new HashMap<String, Double>();
        System.out.println(topFive.size());

        for(int i = 0; i < 5 && i < result.size(); i++) {
            Map.Entry<String, Double> maxEntry = null;
            for (Map.Entry<String, Double> entry : result.entrySet()) {
                if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                    maxEntry = entry;
                }
            }
            topFive.put(maxEntry.getKey(), maxEntry.getValue());
            result.remove(maxEntry.getKey());
        }
        for (Map.Entry<String, Double> entry : topFive.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        return topFive;
    }
}
