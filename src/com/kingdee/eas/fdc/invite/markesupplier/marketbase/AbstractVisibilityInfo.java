package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractVisibilityInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractVisibilityInfo()
    {
        this("id");
    }
    protected AbstractVisibilityInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:知名度's 启用property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8E5B3F7F");
    }
}