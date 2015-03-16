package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPageHeadInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPageHeadInfo()
    {
        this("id");
    }
    protected AbstractPageHeadInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B06EE433");
    }
}