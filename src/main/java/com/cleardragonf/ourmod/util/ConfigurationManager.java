package com.cleardragonf.ourmod.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigurationManager {
    private static final File CONFIG_FILE = new File("config/ourmod-config.json");
    private static JsonObject configJson;
    private static final Gson GSON = new Gson();

    public static void init() {
        try {
            if (CONFIG_FILE.exists()) {
                loadConfig();
            } else {
                saveDefaultConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadConfig() throws IOException {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            configJson = JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    private static void saveDefaultConfig() throws IOException {
        configJson = new JsonObject();
        configJson.addProperty("CurrentEvent", "default");
        saveConfig();
    }

    private static void saveConfig() throws IOException {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(configJson, writer);
        }
    }

    public static String getCurrentEvent() {
        return configJson.get("CurrentEvent").getAsString();
    }

    public static void setCurrentEvent(String event) throws IOException {
        configJson.addProperty("CurrentEvent", event);
        saveConfig();
    }
}
