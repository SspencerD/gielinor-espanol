package com.sspencerd.gielinorespanol.util;

import javax.inject.Singleton;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class MenuOptionVariantNormalizer {

    private static final Pattern DYNAMIC_OPTION_PATTERN = Pattern.compile(
            "^(Withdraw|Deposit|Buy|Sell)-(.+)$",
            Pattern.CASE_INSENSITIVE
    );

    public boolean hasDynamicOption(String option)
    {
        return option != null && DYNAMIC_OPTION_PATTERN.matcher(option.trim()).matches();
    }

    public String translateDynamicOption(String option)
    {
        if(option == null || option.isBlank())
        {
            return option;
        }

        Matcher matcher = DYNAMIC_OPTION_PATTERN.matcher(option.trim());

    if(!matcher.matches())
    {
        return option;
    }

    String action = matcher.group(1);
    String suffix = matcher.group(2);

    return translateAction(action) + "-" + suffix;

    }


    private  String translateAction(String action)
    {
        switch(action.toLowerCase())
        {
            case "withdraw":
                return "Retirar";
            case "deposit":
                return "Depositar";
            case "buy":
                return "Comprar";
            case "sell":
                return "Vender";
            default:
                return action;
        }
    }

}
