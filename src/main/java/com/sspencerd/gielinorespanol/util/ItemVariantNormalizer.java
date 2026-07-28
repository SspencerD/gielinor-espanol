package com.sspencerd.gielinorespanol.util;

import javax.inject.Singleton;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class ItemVariantNormalizer {

    private static final Pattern ITEM_VARIANT_PATTERN = Pattern.compile(
            "^(.*?)(\\s*)(\\((?:\\d+|p|p\\+|p\\+\\+|e|u)\\))$",
            Pattern.CASE_INSENSITIVE
    );

    public boolean hasVariant(String itemName)
    {
        return itemName != null && ITEM_VARIANT_PATTERN.matcher(itemName.trim()).matches();
    }

    public String getBaseName(String itemName)
    {
        if (itemName == null || itemName.isBlank())
        {
            return itemName;
        }

        Matcher matcher = ITEM_VARIANT_PATTERN.matcher(itemName.trim());

        if (!matcher.matches())
        {
            return itemName.trim();
        }

        return matcher.group(1).trim();
    }

    public String getVariantSuffix(String itemName)
    {
        if (itemName == null || itemName.isBlank())
        {
            return "";
        }

        Matcher matcher = ITEM_VARIANT_PATTERN.matcher(itemName.trim());

        if (!matcher.matches())
        {
            return "";
        }

        String spacing = matcher.group(2);
        String suffix = matcher.group(3);

        return spacing + suffix;
    }

    public String buildTranslatedVariant(String translatedBaseName, String originalItemName)
    {
        return translatedBaseName + getVariantSuffix(originalItemName);
    }
}
