package com.sspencerd.gielinorespanol.util;

import javax.inject.Singleton;

@Singleton
public class TextNormalizer {

    public String removeColorTags(String text)
    {
        if(text == null || text.isBlank()){
            return text;
        }
        return text
                .replaceAll("<col=[0-9a-fA-F]+>", "")
                .replace("</col>", "");
    }

    public String replacePreservingOriginalFormat(String originalText, String cleanText, String translatedText)
    {
        if(originalText == null || cleanText == null || translatedText == null )
        {
            return originalText;
        }

        return originalText.replace(cleanText,translatedText);
    }
}
