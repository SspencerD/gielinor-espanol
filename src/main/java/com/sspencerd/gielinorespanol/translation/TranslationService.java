package com.sspencerd.gielinorespanol.translation;

import java.util.Map;
import javax.inject.Singleton;

@Singleton
public class TranslationService
{
    private static final Map<String,String> MENU_OPTIONS_TRANSLATIONS = Map.ofEntries(
            Map.entry("Walk here", "Caminar aquí"),
            Map.entry("Cancel", "Cancelar"),
            Map.entry("Talk-to", "Hablar con"),
            Map.entry("Examine", "Examinar"),
            Map.entry("Use", "Usar"),
            Map.entry("Attack", "Atacar"),
            Map.entry("Collect", "Recolectar"),
            Map.entry("Bank", "Banco"),
            Map.entry("Open", "Abrir"),
            Map.entry("Close", "Cerrar"),
            Map.entry("Search", "Buscar"),
            Map.entry("Take", "Tomar"),
            Map.entry("Drop", "Soltar")
    );

    private static final Map<String, String> MENU_TARGET_TRANSLATIONS = Map.ofEntries(
            Map.entry("Bank Chest","Cofre del banco"),
            Map.entry("Chest","Cofre"),
            Map.entry("Bank booth","Mostrador bancario"),
            Map.entry("Banker","Banquero"),
            Map.entry("Door","Puerta"),
            Map.entry("Ladder","Escalera"),
            Map.entry("Stairs","Escaleras")
    );


    public String translateMenuOption(String option)
    {
        if (option == null || option.isBlank())
        {
            return option;
        }

        return MENU_OPTIONS_TRANSLATIONS.getOrDefault(option,option);
    }

    public String translateMenuTarget(String target)
    {
        if (target == null || target.isBlank())
        {
            return target;
        }

        String cleanTarget = removeColorTags(target);
        String translatedTarget = MENU_TARGET_TRANSLATIONS.get(cleanTarget);

        if (translatedTarget == null)
        {
            return target;
        }

        return target.replace(cleanTarget, translatedTarget);
    }

    private String removeColorTags(String text)
    {
        return text
                .replaceAll("<col=[0-9a-fA-F]+>","")
                .replace("</col>","");
    }

}
