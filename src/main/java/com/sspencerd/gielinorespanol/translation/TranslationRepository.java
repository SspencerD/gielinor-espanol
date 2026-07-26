package com.sspencerd.gielinorespanol.translation;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import javax.inject.Singleton;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;


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
            return Collections.emptyMap();
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        {
            Map<String, String> translations = GSON.fromJson(reader, MAP_TYPE);
            return translations != null ? translations : Collections.emptyMap();
        }
        catch (Exception exception)
        {
            return Collections.emptyMap();
        }
    }
}