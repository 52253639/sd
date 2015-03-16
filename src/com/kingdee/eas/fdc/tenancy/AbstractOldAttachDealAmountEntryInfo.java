package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOldAttachDealAmountEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractOldAttachDealAmountEntryInfo()
    {
        this("id");
    }
    protected AbstractOldAttachDealAmountEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:合同配套资源成交租价分录's 起始日期property 
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
     * Object:合同配套资源成交租价分录's 结束日期property 
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
     * Object:合同配套资源成交租价分录's 租金property 
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
     * Object:合同配套资源成交租价分录's 租金类型property 
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
     * Object:合同配套资源成交租价分录's 是否手工填写property 
     */
    public boolean isIsHandwork()
    {
        return getBoolean("isHandwork");
    }
    public void setIsHandwork(boolean item)
    {
        setBoolean("isHandwork", item);
    }
    /**
     * Object:合同配套资源成交租价分录's 租金单价property 
     */
    public java.math.BigDecimal getPriceAmount()
    {
        return getBigDecimal("priceAmount");
    }
    public void setPriceAmount(java.math.BigDecimal item)
    {
        setBigDecimal("priceAmount", item);
    }
    /**
     * Object: 合同配套资源成交租价分录 's 配套资源头 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo getTenancyAtta()
    {
        return (com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo)get("tenancyAtta");
    }
    public void setTenancyAtta(com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo item)
    {
        put("tenancyAtta", item);
    }
    /**
     * Object: 合同配套资源成交租价分录 's 款项类型 property 
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
     * Object: 合同配套资源成交租价分录 's 合同变更头 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyModificationInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyModificationInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.TenancyModificationInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("71EA5CAB");
    }
}