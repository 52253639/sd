package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOperationSetupInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractOperationSetupInfo()
    {
        this("id");
    }
    protected AbstractOperationSetupInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:业务参数设置's 简称property 
     */
    public String getShortName()
    {
        return getString("shortName");
    }
    public void setShortName(String item)
    {
        setString("shortName", item);
    }
    /**
     * Object:业务参数设置's 设置值property 
     */
    public String getSetupValue()
    {
        return getString("setupValue");
    }
    public void setSetupValue(String item)
    {
        setString("setupValue", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("23A52167");
    }
}