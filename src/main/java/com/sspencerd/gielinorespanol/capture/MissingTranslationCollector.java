package com.sspencerd.gielinorespanol.capture;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sspencerd.gielinorespanol.model.MissingMenuEntry;
import com.sspencerd.gielinorespanol.model.MissingTranslationCategory;
import com.sspencerd.gielinorespanol.util.TextNormalizer;
import com.sspencerd.gielinorespanol.model.CombatLevelTarget;
import com.sspencerd.gielinorespanol.util.CombatLevelTargetNormalizer;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.MenuEntry;
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
    private final CombatLevelTargetNormalizer combatLevelTargetNormalizer;

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
    private final MissingTranslationClassifier missingTranslationClassifier;

    private final Set<String> missingMenuOptions = new LinkedHashSet<>();
    private final Set<String> missingObjects = new LinkedHashSet<>();
    private final Set<String> missingNpcs = new LinkedHashSet<>();
    private final Set<String> missingItems = new LinkedHashSet<>();
    private final Set<String> missingWidgets = new LinkedHashSet<>();
    private final Set<String> missingUnknown = new LinkedHashSet<>();

    private final Set<String> missingMenuEntryKeys = new LinkedHashSet<>();
    private final Set<MissingMenuEntry> missingMenuEntries = new LinkedHashSet<>();

    @Inject
    public MissingTranslationCollector(
            TextNormalizer textNormalizer,
            MissingTranslationClassifier missingTranslationClassifier,
            CombatLevelTargetNormalizer combatLevelTargetNormalizer
    )
    {
        this.textNormalizer = textNormalizer;
        this.missingTranslationClassifier = missingTranslationClassifier;
        this.combatLevelTargetNormalizer = combatLevelTargetNormalizer;
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

    public synchronized void collectMenuTarget(String source, String target,MenuEntry entry)
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
        CombatLevelTarget combatLevelTarget = combatLevelTargetNormalizer.parse(cleanTarget);

        if(combatLevelTarget.hasCombatLevel()){
            cleanTarget = combatLevelTarget.getName();
        }

        MissingTranslationCategory category =  missingTranslationClassifier.classify(source,entry);

        if (addMissingTargetToCategory(category,cleanTarget))
        {
            save();
        }
    }

    public synchronized  void collectMenuEntry(
            String source,
            String option,
            String target,
            MenuEntry entry
    )
    {
        if(entry == null)
            {
            return;
            }

        String cleanTarget = textNormalizer.removeColorTags(target);

        if(cleanTarget == null)
        {
            cleanTarget = "";
        }

        CombatLevelTarget combatLevelTarget = combatLevelTargetNormalizer.parse(cleanTarget);

        if(combatLevelTarget.hasCombatLevel()){
            cleanTarget = combatLevelTarget.getName();
        }
        MissingTranslationCategory category =  missingTranslationClassifier.classify(source,entry);

        String key = source
                + "|"
                + entry.getOption()
                + "|"
                + cleanTarget
                + "|"
                + entry.getType()
                + "|"
                + entry.getIdentifier();

        if(!missingMenuEntryKeys.add(key))
        {
            return;
        }

        missingMenuEntries.add(new MissingMenuEntry(
                source,
                category.name(),
                option,
                cleanTarget,
                entry.getType() != null ? entry.getType().name() : "",
                entry.getIdentifier(),
                entry.getParam0(),
                entry.getParam1()
        ));

        save();
    }

    private boolean addMissingTargetToCategory(
            MissingTranslationCategory category,
            String cleanTarget
    )
    {
        switch (category)
        {
            case OBJECT:
                return missingObjects.add(cleanTarget);
            case NPC:
                return missingNpcs.add(cleanTarget);
            case ITEM:
                return missingItems.add(cleanTarget);
            case WIDGET:
                return missingWidgets.add(cleanTarget);
            case UNKNOWN:
            default:
                return missingUnknown.add(cleanTarget);
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
            if(data.objects != null){
                missingObjects.addAll(data.objects);
            }

            if(data.npcs != null)
            {
                missingNpcs.addAll(data.npcs);
            }

            if(data.items != null)
            {
                missingItems.addAll(data.items);
            }
            if(data.widgets != null)
            {
                missingWidgets.addAll(data.widgets);
            }

            if(data.unknown != null)
            {
                missingUnknown.addAll(data.unknown);
            }
            if (data.menuEntries != null)
            {
                for (MissingMenuEntry menuEntry : data.menuEntries)
                {
                    missingMenuEntries.add(menuEntry);

                    String key = menuEntry.getSource()
                            + "|"
                            + menuEntry.getCategory()
                            + "|"
                            + menuEntry.getOption()
                            + "|"
                            + menuEntry.getTarget()
                            + "|"
                            + menuEntry.getType()
                            + "|"
                            + menuEntry.getIdentifier();

                    missingMenuEntryKeys.add(key);
                }
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
            data.objects = missingObjects;
            data.npcs = missingNpcs;
            data.items = missingItems;
            data.widgets = missingWidgets;
            data.unknown = missingUnknown;
            data.menuEntries = missingMenuEntries;

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
        private Set<String> objects = new LinkedHashSet<>();
        private Set<String> npcs = new LinkedHashSet<>();
        private Set<String> items = new LinkedHashSet<>();
        private Set<String> widgets = new LinkedHashSet<>();
        private Set<String> unknown = new LinkedHashSet<>();
        private Set<MissingMenuEntry> menuEntries = new LinkedHashSet<>();
    }
}