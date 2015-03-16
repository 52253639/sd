package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyRentBillInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractTenancyRentBillInfo()
    {
        this("id");
    }
    protected AbstractTenancyRentBillInfo(String pkField)
    {
        super(pkField);
        put("buildingEntrys", new com.kingdee.eas.fdc.tenancy.BuildigRentEntrysCollection());
        put("roomEntrys", new com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysCollection());
    }
    /**
     * Object: 定租单 's 定租楼栋分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.BuildigRentEntrysCollection getBuildingEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.BuildigRentEntrysCollection)get("buildingEntrys");
    }
    /**
     * Object:定租单's 是否执行property 
     */
    public boolean isIsExecuted()
    {
        return getBoolean("isExecuted");
    }
    public void setIsExecuted(boolean item)
    {
        setBoolean("isExecuted", item);
    }
    /**
     * Object:定租单's 是否失效property 
     */
    public boolean isIsInvalid()
    {
        return getBoolean("isInvalid");
    }
    public void setIsInvalid(boolean item)
    {
        setBoolean("isInvalid", item);
    }
    /**
     * Object:定租单's 楼栋名称property 
     */
    public String getBuildingNames()
    {
        return getString("buildingNames");
    }
    public void setBuildingNames(String item)
    {
        setString("buildingNames", item);
    }
    /**
     * Object: 定租单 's 销售项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:定租单's 执行时间property 
     */
    public java.sql.Timestamp getExecutedTime()
    {
        return getTimestamp("executedTime");
    }
    public void setExecutedTime(java.sql.Timestamp item)
    {
        setTimestamp("executedTime", item);
    }
    /**
     * Object: 定租单 's 房间分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysCollection getRoomEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysCollection)get("roomEntrys");
    }
    /**
     * Object:定租单's 定价类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.TenancyPriceTypeEnum getPriceBillType()
    {
        return com.kingdee.eas.fdc.sellhouse.TenancyPriceTypeEnum.getEnum(getString("priceBillType"));
    }
    public void setPriceBillType(com.kingdee.eas.fdc.sellhouse.TenancyPriceTypeEnum item)
    {
		if (item != null) {
        setString("priceBillType", item.getValue());
		}
    }
    /**
     * Object: 定租单 's 月天数 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenRentBillDaysOfMonthInfo getDaysOfMonth()
    {
        return (com.kingdee.eas.fdc.tenancy.TenRentBillDaysOfMonthInfo)get("daysOfMonth");
    }
    public void setDaysOfMonth(com.kingdee.eas.fdc.tenancy.TenRentBillDaysOfMonthInfo item)
    {
        put("daysOfMonth", item);
    }
    /**
     * Object:定租单's 日单价property 
     */
    public java.math.BigDecimal getDayPrice()
    {
        return getBigDecimal("dayPrice");
    }
    public void setDayPrice(java.math.BigDecimal item)
    {
        setBigDecimal("dayPrice", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D27EE537");
    }
}