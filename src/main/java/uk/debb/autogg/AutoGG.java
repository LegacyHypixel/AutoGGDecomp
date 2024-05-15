package uk.debb.autogg;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class AutoGG implements ModInitializer {
    public static AutoGGConfig config = new AutoGGConfig(true, true, true, true);
    public Gson data = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
    Path configPath = Paths.get("config/autogg.json");

    public void saveData() {
        try {
            if (this.configPath.toFile().exists()) {
                config = this.data.fromJson(new String(Files.readAllBytes(this.configPath)), AutoGGConfig.class);
            } else {
                Files.write(this.configPath, Collections.singleton(this.data.toJson(config)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onInitialize() {
        this.saveData();
    }
}
