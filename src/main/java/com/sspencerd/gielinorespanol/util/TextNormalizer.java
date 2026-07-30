package com.sspencerd.gielinorespanol.util;

import javax.inject.Singleton;

@Singleton
public class TextNormalizer {

    public String removeColorTags(String text)
    {
        if (text == null || text.isBlank())
        {
            return text;
        }

        return text
                .replaceAll("<[^>]+>", "")
                .replace('\u00A0', ' ')
                .replaceAll("\\s+", " ")
                .trim();
    }

    public String replacePreservingOriginalFormat(String originalText, String cleanText, String translatedText)
    {
        if (originalText == null || cleanText == null || translatedText == null)
        {
            return originalText;
        }

        String directReplace = originalText.replace(cleanText, translatedText);

        if (!directReplace.equals(originalText))
        {
            return directReplace;
        }

        return translatedText;
    }
}
