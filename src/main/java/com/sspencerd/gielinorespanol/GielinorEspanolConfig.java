package com.sspencerd.gielinorespanol;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("gielinorespanol")
public interface GielinorEspanolConfig extends Config
{
	@ConfigItem(
			keyName = "translateMenuOptions",
			name = "Traducir opciones del menú",
			description = "Traduce acciones como Use, Examine, Walk here, Attack y Talk-to."
	)
	default boolean translateMenuOptions()
	{
		return true;
	}

	@ConfigItem(
			keyName = "translateMenuTargets",
			name = "Traducir objetivos del menú",
			description = "Activa o desactiva la traducción de nombres de objetos, NPCs, items e interfaz."
	)
	default boolean translateMenuTargets()
	{
		return true;
	}

	@ConfigItem(
			keyName = "translateWorldObjects",
			name = "Traducir objetos del mundo",
			description = "Traduce objetos del mapa como puertas, cofres, árboles, rocas y bancos."
	)
	default boolean translateWorldObjects()
	{
		return true;
	}

	@ConfigItem(
			keyName = "translateItemsAndNpcs",
			name = "Traducir nombres de NPCs e items",
			description = "Si está desactivado, los nombres de NPCs e items se mantendrán en inglés para conservar compatibilidad con la wiki de OSRS."
	)
	default boolean translateItemsAndNpcs()
	{
		return true;
	}

	@ConfigItem(
			keyName = "translateWidgets",
			name = "Traducir interfaz y widgets",
			description = "Traduce textos de interfaz como botones, pestañas, oraciones, hechizos y opciones de RuneLite."
	)
	default boolean translateWidgets()
	{
		return true;
	}

	@ConfigItem(
			keyName = "captureMissingTranslations",
			name = "Capturar traducciones faltantes",
			description = "Guarda localmente textos del juego que aún no tienen traducción. No envía información a internet."
	)
	default boolean captureMissingTranslations()
	{
		return false;
	}

	@ConfigItem(
			keyName = "menuInspectorEnabled",
			name = "Inspector técnico de menú",
			description = "Muestra información técnica del menú en la consola. Recomendado solo para desarrollo."
	)
	default boolean menuInspectorEnabled()
	{
		return false;
	}
}