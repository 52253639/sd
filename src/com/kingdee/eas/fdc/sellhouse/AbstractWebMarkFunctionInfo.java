package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWebMarkFunctionInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractWebMarkFunctionInfo()
    {
        this("id");
    }
    protected AbstractWebMarkFunctionInfo(String pkField)
    {
        super(pkField);
        put("processEntrys", new com.kingdee.eas.fdc.sellhouse.WebMarkProcessCollection());
    }
    /**
     * Object:网签功能's nullproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    /**
     * Object:网签功能's 功能名称property 
     */
    public String getFunctionName()
    {
        return getFunctionName((Locale)null);
    }
    public void setFunctionName(String item)
    {
		setFunctionName(item,(Locale)null);
    }
    public String getFunctionName(Locale local)
    {
        return TypeConversionUtils.objToString(get("functionName", local));
    }
    public void setFunctionName(String item, Locale local)
    {
        put("functionName", item, local);
    }
    /**
     * Object: 网签功能 's 方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.WebMarkSchemaInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.WebMarkSchemaInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.WebMarkSchemaInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 网签功能 's 步骤分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.WebMarkProcessCollection getProcessEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.WebMarkProcessCollection)get("processEntrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DDA92AFE");
    }
}