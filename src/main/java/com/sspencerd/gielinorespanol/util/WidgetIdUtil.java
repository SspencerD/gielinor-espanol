package com.sspencerd.gielinorespanol.util;

import javax.inject.Singleton;

@Singleton
public class WidgetIdUtil
{
    public int getGroupId(int packedWidgetId)
    {
        return packedWidgetId >>> 16;
    }

    public int getChildId(int packedWidgetId)
    {
        return packedWidgetId & 0xFFFF;
    }
}