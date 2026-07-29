package com.sspencerd.gielinorespanol.widget;

import com.sspencerd.gielinorespanol.translation.TranslationService;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;

import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
public class WidgetTextTranslator {

    private final Client client;
    private final TranslationService translationService;

    @Inject
    public WidgetTextTranslator(Client client, TranslationService translationService) {
        this.client = client;
        this.translationService = translationService;
    }

    public void translateVisibleWidgets()
    {
        Widget[] roots = client.getWidgetRoots();

        if(roots == null)
        {
            return;
        }
       for (Widget root : roots){
           translateWidget(root);
       }
    }

    private void translateWidget(Widget widget)
    {
        if(widget == null || widget.isHidden())
        {
            return;
        }

        translateWidgetText(widget);

        Widget[] children = widget.getChildren();

        if(children == null)
        {
            return;
        }

        for(Widget child : children)
        {
            translateWidget(child);
        }
    }

    private void translateWidgetText(Widget widget)
    {
        String text = widget.getText();
        if(text == null || text.isBlank())
        {
            return;
        }
       String translatedText = translationService.translateWidgetText(text);

        if(!text.equals(translatedText))
        {
            widget.setText(translatedText);
        }
    }
}
