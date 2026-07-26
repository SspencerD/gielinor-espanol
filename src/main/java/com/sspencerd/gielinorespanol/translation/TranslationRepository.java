package com.sspencerd.gielinorespanol.translation;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Singleton
public class TranslationRepository
{
    private static final Gson GSON = new Gson();
    private static final Type MAP_TYPE = new TypeToken<Map<String, String>>() {}.getType();

    public Map<String, String> loadTranslations(String resourcePath)
    {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);

        if (inputStream == null)
        {
            log.warn("Translation resource not found: {}", resourcePath);
            return Collections.emptyMap();
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        {
            Map<String, String> translations = GSON.fromJson(reader, MAP_TYPE);
            if(translations == null){
                log.warn("Translation resource is empty: {}", resourcePath);
                return Collections.emptyMap();
            }
            log.info("Loaded {} translations from {}", translations.size(), resourcePath);
            return translations;
        }
        catch (Exception exception)
        {
            log.error("Failed to load translation resource: {}", resourcePath, exception);
            return Collections.emptyMap();
        }
    }
}