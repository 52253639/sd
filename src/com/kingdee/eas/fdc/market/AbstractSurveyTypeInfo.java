package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSurveyTypeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractSurveyTypeInfo()
    {
        this("id");
    }
    protected AbstractSurveyTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:调查问卷类型's 简称property 
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
        return new BOSObjectType("2E418245");
    }
}