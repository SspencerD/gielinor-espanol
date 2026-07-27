package com.sspencerd.gielinorespanol.capture;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sspencerd.gielinorespanol.util.TextNormalizer;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLite;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@Singleton
public class MissingTranslationCollector
{
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private static final Path MISSING_DIRECTORY = RuneLite.RUNELITE_DIR
            .toPath()
            .resolve("gielinor-espanol");

    private static final Path MISSING_FILE = MISSING_DIRECTORY
            .resolve("missing-menu-translations.json");

    private final TextNormalizer textNormalizer;

    private final Set<String> missingMenuOptions = new LinkedHashSet<>();
    private final Set<String> missingMenuTargets = new LinkedHashSet<>();

    @Inject
    public MissingTranslationCollector(TextNormalizer textNormalizer)
    {
        this.textNormalizer = textNormalizer;
        loadExistingMissingTranslations();

        log.info("Missing translations will be saved at: {}", MISSING_FILE);
    }

    public synchronized void collectMenuOption(String option)
    {
        if (option == null || option.isBlank())
        {
            return;
        }

        if (missingMenuOptions.add(option))
        {
            save();
        }
    }

    public synchronized void collectMenuTarget(String target)
    {
        if (target == null || target.isBlank())
        {
            return;
        }

        String cleanTarget = textNormalizer.removeColorTags(target);

        if (cleanTarget == null || cleanTarget.isBlank())
        {
            return;
        }

        if (missingMenuTargets.add(cleanTarget))
        {
            save();
        }
    }

    private void loadExistingMissingTranslations()
    {
        if (!Files.exists(MISSING_FILE))
        {
            return;
        }

        try (Reader reader = Files.newBufferedReader(MISSING_FILE, StandardCharsets.UTF_8))
        {
            MissingTranslationsData data = GSON.fromJson(reader, MissingTranslationsData.class);

            if (data == null)
            {
                return;
            }

            if (data.menuOptions != null)
            {
                missingMenuOptions.addAll(data.menuOptions);
            }

            if (data.menuTargets != null)
            {
                missingMenuTargets.addAll(data.menuTargets);
            }
        }
        catch (Exception exception)
        {
            log.error("Failed to load missing translations file", exception);
        }
    }

    private void save()
    {
        try
        {
            Files.createDirectories(MISSING_DIRECTORY);

            MissingTranslationsData data = new MissingTranslationsData();
            data.menuOptions = missingMenuOptions;
            data.menuTargets = missingMenuTargets;

            try (Writer writer = Files.newBufferedWriter(MISSING_FILE, StandardCharsets.UTF_8))
            {
                GSON.toJson(data, writer);
            }
        }
        catch (Exception exception)
        {
            log.error("Failed to save missing translations file", exception);
        }
    }

    private static class MissingTranslationsData
    {
        private Set<String> menuOptions = new LinkedHashSet<>();
        private Set<String> menuTargets = new LinkedHashSet<>();
    }
}