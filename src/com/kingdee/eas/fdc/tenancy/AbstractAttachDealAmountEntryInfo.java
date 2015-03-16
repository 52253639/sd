package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAttachDealAmountEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractAttachDealAmountEntryInfo()
    {
        this("id");
    }
    protected AbstractAttachDealAmountEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 配套资源成交租价分录 's 租赁配套资源头 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo getTenancyAttach()
    {
        return (com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo)get("tenancyAttach");
    }
    public void setTenancyAttach(com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo item)
    {
        put("tenancyAttach", item);
    }
    /**
     * Object:配套资源成交租价分录's 起始日期property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:配套资源成交租价分录's 结束日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:配套资源成交租价分录's 金额property 
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
     * Object:配套资源成交租价分录's 租金类型property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("rentType"));
    }
    public void setRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("rentType", item.getValue());
		}
    }
    /**
     * Object: 配套资源成交租价分录 's 款项类型 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object:配套资源成交租价分录's 是否手工填写property 
     */
    public boolean isIsHandwork()
    {
        return getBoolean("isHandwork");
    }
    public void setIsHandwork(boolean item)
    {
        setBoolean("isHandwork", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("44C627E0");
    }
}