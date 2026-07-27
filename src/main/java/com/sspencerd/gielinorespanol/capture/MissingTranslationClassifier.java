package com.sspencerd.gielinorespanol.capture;

import com.sspencerd.gielinorespanol.model.MissingTranslationCategory;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;

import javax.inject.Singleton;



@Singleton
public class MissingTranslationClassifier
{

    public MissingTranslationCategory classify(String source, MenuEntry entry)
    {
        if("menuOption".equals(source))
        {
            return MissingTranslationCategory.MENU_OPTION;
        }
        if(entry == null || entry.getType() == null)
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
                return MissingTranslationCategory.WIDGET;

            default:
                return MissingTranslationCategory.UNKNOWN;
        }

    }

}

