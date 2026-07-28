package com.sspencerd.gielinorespanol.model;

import lombok.Getter;

public class MissingMenuEntry {

    @Getter
    private String source;
    private String category;
    private String option;
    private String target;
    private String type;
    private int identifier;
    private int param0;
    private int param1;

    public MissingMenuEntry(
            String source,
            String category,
            String option,
            String target,
            String type,
            int identifier,
            int param0,
            int param1
    )
    {
        this.source = source;
        this.category = category;
        this.option = option;
        this.target = target;
        this.type = type;
        this.identifier = identifier;
        this.param0 = param0;
        this.param1 = param1;
    }

    public String getCategory() {
        return category;
    }
    public String getOption() {
        return option;
    }
    public String getTarget() {
        return target;
    }
    public String getType() {
        return type;
    }
    public int getIdentifier() {
        return identifier;
    }
    public int getParam0() {
        return param0;
    }
    public int getParam1() {
        return param1;
    }
}
