package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvoiceBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInvoiceBillEntryInfo()
    {
        this("id");
    }
    protected AbstractInvoiceBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 票据管理 's 票据管理 property 
     */
    public com.kingdee.eas.fdc.tenancy.InvoiceBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.InvoiceBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.InvoiceBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object:票据管理's 票据金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:票据管理's 收款明细IDproperty 
     */
    public String getRevListId()
    {
        return getString("revListId");
    }
    public void setRevListId(String item)
    {
        setString("revListId", item);
    }
    /**
     * Object:票据管理's 备注property 
     */
    public String getReMark()
    {
        return getString("reMark");
    }
    public void setReMark(String item)
    {
        setString("reMark", item);
    }
    /**
     * Object:票据管理's 收款明细类型property 
     */
    public com.kingdee.eas.fdc.basecrm.RevListTypeEnum getRevListType()
    {
        return com.kingdee.eas.fdc.basecrm.RevListTypeEnum.getEnum(getString("revListType"));
    }
    public void setRevListType(com.kingdee.eas.fdc.basecrm.RevListTypeEnum item)
    {
		if (item != null) {
        setString("revListType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B6AABFA7");
    }
}