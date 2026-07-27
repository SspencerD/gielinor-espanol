package com.sspencerd.gielinorespanol.model;

public class MissingMenuEntry {

    private String source;
    private String option;
    private String target;
    private String type;
    private int identifier;
    private int param0;
    private int param1;

    public MissingMenuEntry(
            String source,
            String option,
            String target,
            String type,
            int identifier,
            int param0,
            int param1
    )
    {
        this.source = source;
        this.option = option;
        this.target = target;
        this.type = type;
        this.identifier = identifier;
        this.param0 = param0;
        this.param1 = param1;
    }
    public String getSource() {
        return source;
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
    public int getIndentifier() {
        return identifier;
    }
    public int getParam0() {
        return param0;
    }
    public int getParam1() {
        return param1;
    }
}
