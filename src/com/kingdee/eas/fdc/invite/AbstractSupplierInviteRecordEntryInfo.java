package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierInviteRecordEntryInfo extends com.kingdee.eas.fdc.invite.BaseInviteEntryInfo implements Serializable 
{
    public AbstractSupplierInviteRecordEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierInviteRecordEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 投标记录分录 's 投标记录 property 
     */
    public com.kingdee.eas.fdc.invite.SupplierInviteRecordInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.SupplierInviteRecordInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.SupplierInviteRecordInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:投标记录分录's 分标段报价property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("53CDC4C8");
    }
}