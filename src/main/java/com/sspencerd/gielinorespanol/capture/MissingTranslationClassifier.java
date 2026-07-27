package com.sspencerd.gielinorespanol.capture;

import com.sspencerd.gielinorespanol.model.MissingTranslationCategory;
import com.sspencerd.gielinorespanol.util.WidgetIdUtil;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;

import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
public class MissingTranslationClassifier
{
    private static final int BANK_GROUP_ID = 12;
    private static final int INVENTORY_GROUP_ID = 149;
    private static final int DEPOSIT_BOX_GROUP_ID = 192;
    private static final int SHOP_GROUP_ID = 300;
    private static final int EQUIPMENT_GROUP_ID = 387;

    private final WidgetIdUtil widgetIdUtil;

    @Inject
    public MissingTranslationClassifier(WidgetIdUtil widgetIdUtil)
    {
        this.widgetIdUtil = widgetIdUtil;
    }

    public MissingTranslationCategory classify(String source, MenuEntry entry)
    {
        if ("menuOption".equals(source))
        {
            return MissingTranslationCategory.MENU_OPTION;
        }

        if (entry == null || entry.getType() == null)
        {
            return MissingTranslationCategory.UNKNOWN;
        }

        MenuAction type = entry.getType();

        switch (type)
        {
            case NPC_FIRST_OPTION:
            case NPC_SECOND_OPTION:
            case NPC_THIRD_OPTION:
            case NPC_FOURTH_OPTION:
            case NPC_FIFTH_OPTION:
            case EXAMINE_NPC:
                return MissingTranslationCategory.NPC;

            case GAME_OBJECT_FIRST_OPTION:
            case GAME_OBJECT_SECOND_OPTION:
            case GAME_OBJECT_THIRD_OPTION:
            case GAME_OBJECT_FOURTH_OPTION:
            case GAME_OBJECT_FIFTH_OPTION:
            case EXAMINE_OBJECT:
                return MissingTranslationCategory.OBJECT;

            case GROUND_ITEM_FIRST_OPTION:
            case GROUND_ITEM_SECOND_OPTION:
            case GROUND_ITEM_THIRD_OPTION:
            case GROUND_ITEM_FOURTH_OPTION:
            case GROUND_ITEM_FIFTH_OPTION:
            case EXAMINE_ITEM_GROUND:
                return MissingTranslationCategory.ITEM;

            case CC_OP:
            case CC_OP_LOW_PRIORITY:
            case WIDGET_TARGET:
                return classifyWidgetEntry(entry);

            default:
                return MissingTranslationCategory.UNKNOWN;
        }
    }

    private MissingTranslationCategory classifyWidgetEntry(MenuEntry entry)
    {
        int groupId = widgetIdUtil.getGroupId(entry.getParam1());
        log.info(
                "Widget classify -> target={}, option={}, type={}, param1={}, groupId={}",
                entry.getTarget(),
                entry.getOption(),
                entry.getType(),
                entry.getParam1(),
                groupId
        );

        switch (groupId)
        {
            case BANK_GROUP_ID:
            case INVENTORY_GROUP_ID:
            case DEPOSIT_BOX_GROUP_ID:
            case SHOP_GROUP_ID:
            case EQUIPMENT_GROUP_ID:
                return MissingTranslationCategory.ITEM;

            default:
                return MissingTranslationCategory.WIDGET;
        }
    }
}