package com.sspencerd.gielinorespanol.menu;

import com.sspencerd.gielinorespanol.GielinorEspanolConfig;
import com.sspencerd.gielinorespanol.translation.TranslationService;
import net.runelite.api.Client;
import net.runelite.api.MenuEntry;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.Dimension;
import java.awt.Graphics2D;

@Singleton
public class MenuTooltipOverlay extends Overlay
{
    private final Client client;
    private final TooltipManager tooltipManager;
    private final TranslationService translationService;
    private final GielinorEspanolConfig config;

    @Inject
    public MenuTooltipOverlay(
            Client client,
            TooltipManager tooltipManager,
            TranslationService translationService,
            GielinorEspanolConfig config
    )
    {
        this.client = client;
        this.tooltipManager = tooltipManager;
        this.translationService = translationService;
        this.config = config;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if(!config.translateHoverTooltip()){
            return null;
        }
        MenuEntry[] menuEntries = client.getMenuEntries();

        if (menuEntries == null || menuEntries.length == 0)
        {
            return null;
        }

        MenuEntry entry = menuEntries[menuEntries.length - 1];

        if (entry == null)
        {
            return null;
        }

        String option = entry.getOption();
        String target = entry.getTarget();

        String translatedTooltip = translationService.translateMenuEntry(option, target);

        if (translatedTooltip == null || translatedTooltip.isBlank())
        {
            return null;
        }

        if (translatedTooltip.equals(option) || translatedTooltip.equals(option + " " + target))
        {
            return null;
        }

        tooltipManager.add(new Tooltip(translatedTooltip));

        return null;
    }
}