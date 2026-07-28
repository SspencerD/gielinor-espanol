package com.sspencerd.gielinorespanol;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface GielinorEspanolConfig extends Config
{
	@ConfigItem(
		keyName = "translateMenuOptions",
		name = "Traducir opciones del menú",
		description = "Traduce acciones como Use,Examine,Walk here, Cancel entre otras"
	)
	default boolean translateMenuOptions()
	{
		return true;
	}

	@ConfigItem(
			keyName = "translateMenuTargets",
			name = "Traducir objetivos del menú",
			description = "Traduce objetivos como Bank chest, Bank, Door , Banker, etc."
	)
	default boolean translateMenuTargets()
	{
		return true;
	}
	@ConfigItem(
			keyName = "menuInspectorEnabled",
			name = "Inspector de menú",
			description = "Muestra información técnica de las entradas del menú en la consola"
	)
	default boolean menuInspectorEnabled()
	{
		return false;
	}
	@ConfigItem(
			keyName = "captureMissingTranslations",
			name = "(analitica) Capturar traducciones faltantes",
			description = "Guarda opciones y objetivos del menú sin traducción en un archivo JSON local"
	)
	default boolean captureMissingTranslations()
	{
		return true;
	}

	@ConfigItem(
			keyName = "translateHoverTooltip",
			name = "Traducir tooltip hover",
			description = "Muestra un tooltip traducido al pasar el mouse sobre acciones u objetos"
	)
	default boolean enableTranslateItemsAndNpcs()
	{
		return true;
	}
	@ConfigItem(
			keyName = "translateItemsAndNpcs",
			name = "Traducir los nombres de los npc e items",
			description = "En esté caso si no prefieres traducir los items y npcs puedes dejarlo apagado"
	)
	default boolean translateHoverTooltip()
	{
		return true;
	}
}
