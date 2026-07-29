package com.sspencerd.gielinorespanol.translation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    private final Gson gson = new Gson();

    public Map<String, String> loadTranslations(String resourcePath)
    {

        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath))
        {
            if (inputStream == null)
            {
                log.warn("Translation resource not found: {}", resourcePath);
                return Collections.emptyMap();
            }

            Type type = new TypeToken<Map<String, String>>() {}.getType();

            Map<String, String> translations = gson.fromJson(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8),
                    type
            );

            if (translations == null)
            {
                log.warn("Translation resource is empty: {}", resourcePath);
                return Collections.emptyMap();
            }

            log.info("Loaded {} translations from {}", translations.size(), resourcePath);
            return translations;
        }
        catch (Exception ex)
        {
            System.out.println("FAILED TO LOAD: " + resourcePath);
            ex.printStackTrace();
            return Collections.emptyMap();
        }
    }
}