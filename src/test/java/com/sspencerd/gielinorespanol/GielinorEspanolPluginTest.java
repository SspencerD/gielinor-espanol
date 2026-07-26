package com.sspencerd.gielinorespanol;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class GielinorEspanolPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(GielinorEspanolPlugin.class);
		RuneLite.main(args);
	}
}