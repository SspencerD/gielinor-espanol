package com.sspencerd.gielinorespanol.capture;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Singleton

public class MissingTranslationCollector {
    private final Set<String> missingMenuOptions = new HashSet<>();
    private final Set<String> missingMenuTargets = new HashSet<>();


    public void collectMenuOption(String option)
    {
        if(option == null || option.isBlank()){
            return;
        }
        if(missingMenuOptions.add(option))
        {
            log.info("[Missing menu option {}",option);
        }
    }

    public void collectMenuTarget(String target)
    {
        if(target == null || target.isBlank())
        {
            return;
        }

        if(missingMenuTargets.add(target))
        {
            log.info("[Missing menu target] {}",target);
        }
    }
}
