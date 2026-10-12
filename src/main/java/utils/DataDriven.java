package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;

public class DataDriven {
    public static JsonObject jsonReader(String filePath) {
        try {
            File file = new File(filePath);
            FileReader reader = new FileReader(file.getAbsolutePath());
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception e) {
            System.out.println("Failed to read JSON file at path: " + filePath);
            e.printStackTrace();
            return null;
        }
    }
}
