package com.sspencerd.gielinorespanol.translation;

import com.sspencerd.gielinorespanol.model.CombatLevelTarget;
import com.sspencerd.gielinorespanol.util.CombatLevelTargetNormalizer;
import com.sspencerd.gielinorespanol.util.ItemVariantNormalizer;
import com.sspencerd.gielinorespanol.util.TextNormalizer;
import com.sspencerd.gielinorespanol.util.WidgetIdUtil;
import com.sspencerd.gielinorespanol.util.MenuOptionVariantNormalizer;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class TranslationService
{
    private static final int BANK_GROUP_ID = 12;
    private static final int INVENTORY_GROUP_ID = 149;
    private static final int DEPOSIT_BOX_GROUP_ID = 192;
    private static final int SHOP_GROUP_ID = 300;
    private static final int EQUIPMENT_GROUP_ID = 387;

    private final TextNormalizer textNormalizer;
    private final CombatLevelTargetNormalizer combatLevelTargetNormalizer;
    private final ItemVariantNormalizer itemVariantNormalizer;
    private final WidgetIdUtil widgetIdUtil;
    private final MenuOptionVariantNormalizer menuOptionVariantNormalizer;

    private final Map<String, String> menuOptionTranslations;
    private final Map<String, String> menuTargetTranslations;
    private final Map<String, String> objectTranslations;
    private final Map<String, String> npcTranslations;
    private final Map<String, String> itemTranslations;
    private final Map<String, String> widgetTranslations;

    @Inject
    public TranslationService(
            TextNormalizer textNormalizer,
            TranslationRepository translationRepository,
            CombatLevelTargetNormalizer combatLevelTargetNormalizer,
            WidgetIdUtil widgetIdUtil,
            ItemVariantNormalizer itemVariantNormalizer,
            MenuOptionVariantNormalizer menuOptionVariantNormalizer
    )
    {
        this.textNormalizer = textNormalizer;
        this.combatLevelTargetNormalizer = combatLevelTargetNormalizer;
        this.widgetIdUtil = widgetIdUtil;
        this.itemVariantNormalizer = itemVariantNormalizer;
        this.menuOptionVariantNormalizer = menuOptionVariantNormalizer;

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

        if(menuOptionVariantNormalizer.hasDynamicOption(option))
        {
            return menuOptionVariantNormalizer.translateDynamicOption(option);
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

        CombatLevelTarget combatLevelTarget = combatLevelTargetNormalizer.parse(cleanTarget);

        if (combatLevelTarget.hasCombatLevel())
        {
            String translatedCombatTarget = translateCombatLevelTarget(
                    entry,
                    target,
                    combatLevelTarget
            );

            if (translatedCombatTarget != null)
            {
                return translatedCombatTarget;
            }

            return target;
        }

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

        if(menuOptionVariantNormalizer.hasDynamicOption(option))
        {
            return true;
        }

        return menuOptionTranslations.containsKey(option);
    }

    public boolean isItemOrNpcTarget(MenuEntry entry){
        if(entry == null)
        {
            return false;
        }

        Map<String , String> targetDictionary = getTargetDictionary(entry);
        return targetDictionary == itemTranslations || targetDictionary == npcTranslations;
    }

    public boolean isObjectTarget(MenuEntry entry){
        if(entry == null)
        {
            return false;
        }
        Map<String , String> targetDictionary = getTargetDictionary(entry);

        return targetDictionary == objectTranslations;
    }

    public boolean isWidgetTarget(MenuEntry entry){
        if(entry == null)
        {
            return false;
        }
        Map<String , String> targetDictionary = getTargetDictionary(entry);
        return targetDictionary == widgetTranslations;
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

        CombatLevelTarget combatLevelTarget = combatLevelTargetNormalizer.parse(cleanTarget);

        if (combatLevelTarget.hasCombatLevel())
        {
            String translatedName = specificTranslations.get(combatLevelTarget.getName());

            if (translatedName == null)
            {
                translatedName = menuTargetTranslations.get(combatLevelTarget.getName());
            }

            if (translatedName == null)
            {
                return null;
            }

            return combatLevelTargetNormalizer.buildTranslatedTarget(
                    translatedName,
                    combatLevelTarget.getLevel()
            );
        }

        String translatedTarget = specificTranslations.get(cleanTarget);

        if (translatedTarget != null)
        {
            return translatedTarget;
        }

        if (specificTranslations == itemTranslations)
        {
            String translatedVariantTarget = findItemVariantTranslation(cleanTarget);

            if (translatedVariantTarget != null)
            {
                return translatedVariantTarget;
            }
        }

        return menuTargetTranslations.get(cleanTarget);
    }

    private String translateCombatLevelTarget(
            MenuEntry entry,
            String originalTarget,
            CombatLevelTarget combatLevelTarget
    )
    {
        Map<String, String> specificTranslations = getTargetDictionary(entry);

        String translatedName = specificTranslations.get(combatLevelTarget.getName());

        if (translatedName == null)
        {
            translatedName = menuTargetTranslations.get(combatLevelTarget.getName());
        }

        if (translatedName == null)
        {
            return null;
        }

        return originalTarget
                .replace(combatLevelTarget.getName(), translatedName)
                .replace("level-", "nivel-")
                .replace("Level-", "Nivel-");
    }

    private String findItemVariantTranslation(String cleanTarget)
    {
        if (!itemVariantNormalizer.hasVariant(cleanTarget))
        {
            return null;
        }

        String baseName = itemVariantNormalizer.getBaseName(cleanTarget);
        String translatedBaseName = itemTranslations.get(baseName);

        if (translatedBaseName == null)
        {
            return null;
        }

        return itemVariantNormalizer.buildTranslatedVariant(
                translatedBaseName,
                cleanTarget
        );
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
                return isItemWidget(entry) ? itemTranslations : widgetTranslations;

            default:
                return menuTargetTranslations;
        }
    }

    private boolean isItemWidget(MenuEntry entry)
    {
        int groupId = widgetIdUtil.getGroupId(entry.getParam1());

        switch (groupId)
        {
            case BANK_GROUP_ID:
            case INVENTORY_GROUP_ID:
            case DEPOSIT_BOX_GROUP_ID:
            case SHOP_GROUP_ID:
            case EQUIPMENT_GROUP_ID:
                return true;

            default:
                return false;
        }
    }
}