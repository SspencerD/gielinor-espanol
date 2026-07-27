package com.sspencerd.gielinorespanol.translation;

import com.sspencerd.gielinorespanol.util.TextNormalizer;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class TranslationService
{
    private final TextNormalizer textNormalizer;

    private final Map<String, String> menuOptionTranslations;
    private final Map<String, String> menuTargetTranslations;
    private final Map<String, String> objectTranslations;
    private final Map<String, String> npcTranslations;
    private final Map<String, String> itemTranslations;
    private final Map<String, String> widgetTranslations;

    @Inject
    public TranslationService(
            TextNormalizer textNormalizer,
            TranslationRepository translationRepository
    )
    {
        this.textNormalizer = textNormalizer;

        this.menuOptionTranslations = translationRepository.loadTranslations(
                "/translations/es/menu/options.json"
        );
        this.menuTargetTranslations = translationRepository.loadTranslations(
                "/translations/es/menu/targets.json"
        );
        this.objectTranslations = translationRepository.loadTranslations(
                "/translations/es/objects/objects.json"
        );
        this.npcTranslations = translationRepository.loadTranslations(
                "/translations/es/npcs/npcs.json"
        );
        this.itemTranslations = translationRepository.loadTranslations(
                "/translations/es/items/items.json"
        );
        this.widgetTranslations = translationRepository.loadTranslations(
                "/translations/es/widgets/widgets.json"
        );
    }

    public String translateMenuOption(String option)
    {
        if (option == null || option.isBlank())
        {
            return option;
        }

        return menuOptionTranslations.getOrDefault(option, option);
    }

    public String translateMenuTarget(MenuEntry entry)
    {
        if (entry == null)
        {
            return null;
        }

        String target = entry.getTarget();

        if (target == null || target.isBlank())
        {
            return target;
        }

        String cleanTarget = textNormalizer.removeColorTags(target);
        String translatedTarget = findTargetTranslation(entry, cleanTarget);

        if (translatedTarget == null)
        {
            return target;
        }

        return textNormalizer.replacePreservingOriginalFormat(
                target,
                cleanTarget,
                translatedTarget
        );
    }

    public boolean hasMenuOptionTranslation(String option)
    {
        if (option == null || option.isBlank())
        {
            return true;
        }

        return menuOptionTranslations.containsKey(option);
    }

    public boolean hasMenuTargetTranslation(MenuEntry entry)
    {
        if (entry == null)
        {
            return true;
        }

        String target = entry.getTarget();

        if (target == null || target.isBlank())
        {
            return true;
        }

        String cleanTarget = textNormalizer.removeColorTags(target);

        return findTargetTranslation(entry, cleanTarget) != null;
    }

    public String translateMenuEntry(String option, MenuEntry entry)
    {
        String translatedOption = translateMenuOption(option);
        String translatedTarget = translateMenuTarget(entry);

        if (translatedTarget == null || translatedTarget.isBlank())
        {
            return translatedOption;
        }

        return translatedOption + " " + translatedTarget;
    }

    private String findTargetTranslation(MenuEntry entry, String cleanTarget)
    {
        if (cleanTarget == null || cleanTarget.isBlank())
        {
            return null;
        }

        Map<String, String> specificTranslations = getTargetDictionary(entry);

        String translatedTarget = specificTranslations.get(cleanTarget);

        if (translatedTarget != null)
        {
            return translatedTarget;
        }

        return menuTargetTranslations.get(cleanTarget);
    }

    private Map<String, String> getTargetDictionary(MenuEntry entry)
    {
        MenuAction type = entry.getType();

        if (type == null)
        {
            return menuTargetTranslations;
        }

        switch (type)
        {
            case NPC_FIRST_OPTION:
            case NPC_SECOND_OPTION:
            case NPC_THIRD_OPTION:
            case NPC_FOURTH_OPTION:
            case NPC_FIFTH_OPTION:
            case EXAMINE_NPC:
                return npcTranslations;

            case GAME_OBJECT_FIRST_OPTION:
            case GAME_OBJECT_SECOND_OPTION:
            case GAME_OBJECT_THIRD_OPTION:
            case GAME_OBJECT_FOURTH_OPTION:
            case GAME_OBJECT_FIFTH_OPTION:
            case EXAMINE_OBJECT:
                return objectTranslations;

            case GROUND_ITEM_FIRST_OPTION:
            case GROUND_ITEM_SECOND_OPTION:
            case GROUND_ITEM_THIRD_OPTION:
            case GROUND_ITEM_FOURTH_OPTION:
            case GROUND_ITEM_FIFTH_OPTION:
            case EXAMINE_ITEM_GROUND:
                return itemTranslations;

            case CC_OP:
            case CC_OP_LOW_PRIORITY:
            case WIDGET_TARGET:
                return widgetTranslations;

            default:
                return menuTargetTranslations;
        }
    }
}