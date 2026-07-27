package com.sspencerd.gielinorespanol.util;

import com.sspencerd.gielinorespanol.model.CombatLevelTarget;

import javax.inject.Singleton;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class CombatLevelTargetNormalizer
{
    private static final Pattern COMBAT_LEVEL_PATTERN = Pattern.compile(
            "^(.*?)\\s*\\(\\s*level\\s*-\\s*(\\d+)\\s*\\)$",
            Pattern.CASE_INSENSITIVE
    );

    public CombatLevelTarget parse(String target)
    {
        if (target == null || target.isBlank())
        {
            return new CombatLevelTarget(target, "", false);
        }

        String normalizedTarget = normalizeSpaces(target);

        Matcher matcher = COMBAT_LEVEL_PATTERN.matcher(normalizedTarget);

        if (!matcher.matches())
        {
            return new CombatLevelTarget(normalizedTarget, "", false);
        }

        String name = normalizeSpaces(matcher.group(1));
        String level = matcher.group(2).trim();

        return new CombatLevelTarget(name, level, true);
    }

    public String buildTranslatedTarget(String translatedName, String level)
    {
        return translatedName + " (nivel-" + level + ")";
    }

    private String normalizeSpaces(String text)
    {
        return text
                .replace('\u00A0', ' ')
                .replaceAll("\\s+", " ")
                .trim();
    }
}