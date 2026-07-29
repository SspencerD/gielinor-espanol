package com.sspencerd.gielinorespanol;

import com.sspencerd.gielinorespanol.capture.MissingTranslationCollector;
import com.sspencerd.gielinorespanol.menu.MenuInspector;
import com.sspencerd.gielinorespanol.translation.TranslationService;
import com.sspencerd.gielinorespanol.widget.WidgetTextTranslator;

import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOpened;
import net.runelite.client.eventbus.Subscribe;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.events.MenuEntryAdded;

@Slf4j
@PluginDescriptor(
	name = "Gielinor Español",
        description = "Traducción de ORS al español",
        tags = {"spanish","español","traduccion","translation"}
)
public class GielinorEspanolPlugin extends Plugin
{

    @Inject
    private MenuInspector menuInspector;

	@Inject
	private Client client;

	@Inject
	private GielinorEspanolConfig config;

	@Inject
	private TranslationService translationService;

	@Inject
	private MissingTranslationCollector missingTranslationCollector;

	@Inject
	private WidgetTextTranslator widgetTextTranslator;

//	@Inject
//	private OverlayManager overlayManager;
//
//	@Inject
//	private MenuTooltipOverlay menuTooltipOverlay;

	private void translateMenuEntry(MenuEntry entry)
	{
		if (entry == null)
		{
			return;
		}

		String originalOption = entry.getOption();
		String originalTarget = entry.getTarget();

		boolean shouldTranslateTarget = config.translateMenuTargets();

		if (shouldTranslateTarget && translationService.isItemOrNpcTarget(entry) && !config.translateItemsAndNpcs())
		{
			shouldTranslateTarget = false;
		}

		if (shouldTranslateTarget && translationService.isObjectTarget(entry) && !config.translateWorldObjects())
		{
			shouldTranslateTarget = false;
		}

		if (shouldTranslateTarget && translationService.isWidgetTarget(entry) && !config.translateWidgets())
		{
			shouldTranslateTarget = false;
		}

		if (config.menuInspectorEnabled())
		{
			menuInspector.inspect(entry);
		}

		if (
				config.captureMissingTranslations() &&
						!translationService.hasMenuOptionTranslation(originalOption)
		)
		{
			missingTranslationCollector.collectMenuOption(originalOption);
			missingTranslationCollector.collectMenuEntry(
					"menuOption",
					originalOption,
					originalTarget,
					entry
			);
		}

		if (
				config.captureMissingTranslations() &&
						shouldTranslateTarget &&
						!translationService.hasMenuTargetTranslation(entry)
		)
		{
			missingTranslationCollector.collectMenuTarget(
					"menuTarget",
					originalTarget,
					entry
			);

			missingTranslationCollector.collectMenuEntry(
					"menuTarget",
					originalOption,
					originalTarget,
					entry
			);
		}

		if (config.translateMenuOptions())
		{
			String translatedOption = translationService.translateMenuOption(originalOption);

			if (!originalOption.equals(translatedOption))
			{
				entry.setOption(translatedOption);
			}
		}

		if (shouldTranslateTarget)
		{
			String translatedTarget = translationService.translateMenuTarget(entry);

			if (!originalTarget.equals(translatedTarget))
			{
				entry.setTarget(translatedTarget);
			}
		}
	}

	@Override
	protected void startUp() throws Exception
	{
		log.debug("Example started!");
		// overlayManager.add(menuTooltipOverlay);
	}
	@Override
	protected void shutDown() throws Exception
	{
		log.debug("Example stopped!");
		// overlayManager.remove(menuTooltipOverlay);
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		MenuEntry entry = event.getMenuEntry();

		translateMenuEntry(entry);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Gelinor en español está activado " + config.translateMenuOptions(), null);
		}
	}

    @Subscribe
    public void onMenuOpened(MenuOpened event)
    {
		if(!config.menuInspectorEnabled())
		{
			return;
		}
		for (MenuEntry entry : event.getMenuEntries())
		{
			menuInspector.inspect(entry);
		}
    }

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if(!config.translateWidgets())
		{
			return;
		}

		widgetTextTranslator.translateVisibleWidgets();
	}

	@Provides
    GielinorEspanolConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(GielinorEspanolConfig.class);
	}
}
