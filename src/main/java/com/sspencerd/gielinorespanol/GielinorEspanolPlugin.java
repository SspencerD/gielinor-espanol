package com.sspencerd.gielinorespanol;

import com.sspencerd.gielinorespanol.capture.MissingTranslationCollector;
import com.sspencerd.gielinorespanol.menu.MenuInspector;
import com.sspencerd.gielinorespanol.translation.TranslationService;
import net.runelite.api.*;
import net.runelite.api.events.MenuOpened;
import net.runelite.client.eventbus.Subscribe;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

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

	@Override
	protected void startUp() throws Exception
	{
		log.debug("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.debug("Example stopped!");
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
		for (MenuEntry entry : event.getMenuEntries())
		{
			if (config.menuInspectorEnabled())
			{
				menuInspector.inspect(entry);
			}

			if (config.translateMenuOptions())
			{
				String originalOption = entry.getOption();

				if(!translationService.hasMenuOptionTranslation(originalOption))
				{
					missingTranslationCollector.collectMenuOption(originalOption);
				}

				String translatedOption = translationService.translateMenuOption(originalOption);

				if (!originalOption.equals(translatedOption))
				{
					entry.setOption(translatedOption);
				}
			}

			if (config.translateMenuTargets())
			{
				String originalTarget = entry.getTarget();

				if(!translationService.hasMenuOptionTranslation(originalTarget))
				{
					missingTranslationCollector.collectMenuTarget(originalTarget);
				}

				String translatedTarget = translationService.translateMenuTarget(originalTarget);

				if (!originalTarget.equals(translatedTarget))
				{
					entry.setTarget(translatedTarget);
				}
			}
		}
    }

	@Provides
    GielinorEspanolConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(GielinorEspanolConfig.class);
	}
}
