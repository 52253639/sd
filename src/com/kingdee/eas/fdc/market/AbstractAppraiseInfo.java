package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAppraiseInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractAppraiseInfo()
    {
        this("id");
    }
    protected AbstractAppraiseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:»î¶¯ÆÀ¼Û's ¼ò³Æproperty 
     */
    public String getShortName()
    {
        return getString("shortName");
    }
    public void setShortName(String item)
    {
        setString("shortName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("77B9DF3C");
    }
}