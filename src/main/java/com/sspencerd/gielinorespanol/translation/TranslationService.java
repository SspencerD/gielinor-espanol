package com.sspencerd.gielinorespanol.translation;

import com.sspencerd.gielinorespanol.util.TextNormalizer;

import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TranslationService
{
    private final TextNormalizer textNormalizer;
    private final Map<String, String> menuOptionTranslations;
    private final Map<String, String> menuTargetTranslations;

    @Inject
    public TranslationService(
            TextNormalizer textNormalizer,
            TranslationRepository translationRepository
    ){
        this.textNormalizer = textNormalizer;
        this.menuOptionTranslations = translationRepository.loadTranslations(
                "/translations/es/menu/options.json"
        );
        this.menuTargetTranslations = translationRepository.loadTranslations(
                "/translations/es/menu/targets.json"
        );
    }


    public String translateMenuOption(String option)
    {
        if (option == null || option.isBlank())
        {
            return option;
        }

        return menuOptionTranslations.getOrDefault(option,option);
    }

    public String translateMenuTarget(String target)
    {
        if (target == null || target.isBlank())
        {
            return target;
        }

        String cleanTarget = textNormalizer.removeColorTags(target);
        String translatedTarget = menuTargetTranslations.get(cleanTarget);

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

}
