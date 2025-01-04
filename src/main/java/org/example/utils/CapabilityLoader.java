package org.example.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileReader;
import java.io.IOException;

public class CapabilityLoader {

    public static DesiredCapabilities getCapabilities(String deviceName, String jsonFilePath) throws IOException {
        // Load the JSON file
        FileReader reader = new FileReader(jsonFilePath);
        JsonObject devices = JsonParser.parseReader(reader).getAsJsonObject();

        // Get the specific device's capabilities
        JsonObject deviceCapabilities = devices.getAsJsonObject(deviceName);

        // Create DesiredCapabilities object
        DesiredCapabilities capabilities = new DesiredCapabilities();
        deviceCapabilities.entrySet().forEach(entry ->
                capabilities.setCapability(entry.getKey(), entry.getValue().getAsString())
        );

        return capabilities;
    }
}
