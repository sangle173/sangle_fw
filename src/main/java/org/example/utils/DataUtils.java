package org.example.utils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataUtils {

    private static final String DATA_FILE_PATH = "src/main/resources/data/data.json"; // Update this path
    private static final Random random = new Random();

    private static Map<String, List<String>> data;

    // Static block to load JSON data
    static {
        try (FileReader reader = new FileReader(DATA_FILE_PATH)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
            data = gson.fromJson(reader, type);
        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            throw new RuntimeException("Failed to load data from JSON file: " + e.getMessage(), e);
        }
    }

    /**
     * Get a random movie name.
     *
     * @return A random movie name.
     */
    public static String getRandomMovie() {
        return getRandomFromList("movies");
    }

    /**
     * Get a random TV show name.
     *
     * @return A random TV show name.
     */
    public static String getRandomTvShow() {
        return getRandomFromList("tv_shows");
    }

    /**
     * Get a random actor name.
     *
     * @return A random actor name.
     */
    public static String getRandomActor() {
        return getRandomFromList("actors");
    }

    // Helper method to get a random item from a list
    private static String getRandomFromList(String category) {
        List<String> list = data.get(category);
        if (list == null || list.isEmpty()) {
            throw new IllegalStateException("Category '" + category + "' is empty or not loaded properly.");
        }
        return list.get(random.nextInt(list.size()));
    }

    /**
     * Get all settings as a list.
     *
     * @return List of all settings.
     */
    public static List<String> getAllSettings() {
        return data.get("settings");
    }

    /**
     * Get a setting by name.
     *
     * @param name The name of the setting.
     * @return The setting if found, or null if not found.
     */
    public static String getSettingByName(String name) {
        List<String> settings = data.get("settings");
        if (settings != null && settings.contains(name)) {
            return name;
        }
        return null;
    }

    /**
     * Get a random setting.
     *
     * @return A random setting.
     */
    public static String getRandomSetting() {
        return getRandomFromList("settings");
    }
}

