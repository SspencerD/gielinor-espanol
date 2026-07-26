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
}
