package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCustomerWorkAreaEntryInfo extends AbstractObjectValue implements Serializable 
{
    public AbstractCustomerWorkAreaEntryInfo()
    {
        this("id");
    }
    protected AbstractCustomerWorkAreaEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:工作区域f7多选分录's nullproperty 
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
     * Object: 工作区域f7多选分录 's  property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 工作区域f7多选分录 's  property 
     */
    public com.kingdee.eas.fdc.sellhouse.WorkAreaInfo getWorkArea()
    {
        return (com.kingdee.eas.fdc.sellhouse.WorkAreaInfo)get("workArea");
    }
    public void setWorkArea(com.kingdee.eas.fdc.sellhouse.WorkAreaInfo item)
    {
        put("workArea", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5FB83C9B");
    }
}