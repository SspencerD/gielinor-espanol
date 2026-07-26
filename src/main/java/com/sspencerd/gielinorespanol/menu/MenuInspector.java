package com.sspencerd.gielinorespanol.menu;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.MenuEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Slf4j
@Singleton
public class MenuInspector {
    private static final Logger log = LoggerFactory.getLogger(MenuInspector.class);

    public void inspect(MenuEntry entry){
        log.info(
                "\n========== MENU ENTRY ==========\n" +
                        "Option     : {}\n" +
                        "Target     : {}\n" +
                        "Identifier : {}\n" +
                        "Type       : {}\n" +
                        "Param0     : {}\n" +
                        "Param1     : {}\n" +
                        "================================",
                entry.getOption(),
                entry.getTarget(),
                entry.getIdentifier(),
                entry.getType(),
                entry.getParam0(),
                entry.getParam1()
        );
    }
}
