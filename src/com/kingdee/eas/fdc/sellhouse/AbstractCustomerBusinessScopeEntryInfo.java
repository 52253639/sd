package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCustomerBusinessScopeEntryInfo extends AbstractObjectValue implements Serializable 
{
    public AbstractCustomerBusinessScopeEntryInfo()
    {
        this("id");
    }
    protected AbstractCustomerBusinessScopeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:业务范围f7多选分录's nullproperty 
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
     * Object: 业务范围f7多选分录 's  property 
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
     * Object: 业务范围f7多选分录 's  property 
     */
    public com.kingdee.eas.fdc.tenancy.BusinessScopeInfo getBusinessScope()
    {
        return (com.kingdee.eas.fdc.tenancy.BusinessScopeInfo)get("businessScope");
    }
    public void setBusinessScope(com.kingdee.eas.fdc.tenancy.BusinessScopeInfo item)
    {
        put("businessScope", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F94C15F7");
    }
}