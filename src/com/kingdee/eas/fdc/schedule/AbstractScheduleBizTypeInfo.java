package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleBizTypeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractScheduleBizTypeInfo()
    {
        this("id");
    }
    protected AbstractScheduleBizTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:业务类型设置's 系统预设property 
     */
    public boolean isIsSysDef()
    {
        return getBoolean("isSysDef");
    }
    public void setIsSysDef(boolean item)
    {
        setBoolean("isSysDef", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("62C5E9C0");
    }
}