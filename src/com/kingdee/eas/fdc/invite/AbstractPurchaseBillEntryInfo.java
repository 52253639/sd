package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseBillEntryInfo extends com.kingdee.eas.fdc.invite.BOMEntryInfo implements Serializable 
{
    public AbstractPurchaseBillEntryInfo()
    {
        this("id");
    }
    protected AbstractPurchaseBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 采购申请单分录 's 单头 property 
     */
    public com.kingdee.eas.fdc.invite.PurchaseBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.PurchaseBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.PurchaseBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:采购申请单分录's 来源单号property 
     */
    public String getSourceBillNum()
    {
        return getString("sourceBillNum");
    }
    public void setSourceBillNum(String item)
    {
        setString("sourceBillNum", item);
    }
    /**
     * Object:采购申请单分录's 标段property 
     */
    public com.kingdee.eas.fdc.invite.SectionEnum getSection()
    {
        return com.kingdee.eas.fdc.invite.SectionEnum.getEnum(getInt("section"));
    }
    public void setSection(com.kingdee.eas.fdc.invite.SectionEnum item)
    {
		if (item != null) {
        setInt("section", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B72F3606");
    }
}