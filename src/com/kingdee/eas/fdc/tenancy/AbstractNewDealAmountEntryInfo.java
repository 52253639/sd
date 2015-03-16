package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewDealAmountEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractNewDealAmountEntryInfo()
    {
        this("id");
    }
    protected AbstractNewDealAmountEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:成交价格分录's 起始日期property 
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
     * Object:成交价格分录's 结束日期property 
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
     * Object:成交价格分录's 租金property 
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
     * Object:成交价格分录's 租金类型property 
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
     * Object:成交价格分录's 是否手工填写property 
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
     * Object:成交价格分录's 租金单价property 
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
     * Object: 成交价格分录 's 租赁房间头 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo getTenancyRoom()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo)get("tenancyRoom");
    }
    public void setTenancyRoom(com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo item)
    {
        put("tenancyRoom", item);
    }
    /**
     * Object: 成交价格分录 's 款项类型 property 
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
     * Object: 成交价格分录 's 租赁房间头 property 
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
        return new BOSObjectType("736CC297");
    }
}