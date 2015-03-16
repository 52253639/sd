package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyRoomChargeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTenancyRoomChargeEntryInfo()
    {
        this("id");
    }
    protected AbstractTenancyRoomChargeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 租赁房间收费项目分录 's 租赁房间 property 
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
     * Object: 租赁房间收费项目分录 's 收费项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getChargeItem()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("chargeItem");
    }
    public void setChargeItem(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("chargeItem", item);
    }
    /**
     * Object: 租赁房间收费项目分录 's 收费标准 property 
     */
    public com.kingdee.eas.fdc.propertymgmt.ChargeStandardInfo getChargeStandard()
    {
        return (com.kingdee.eas.fdc.propertymgmt.ChargeStandardInfo)get("chargeStandard");
    }
    public void setChargeStandard(com.kingdee.eas.fdc.propertymgmt.ChargeStandardInfo item)
    {
        put("chargeStandard", item);
    }
    /**
     * Object:租赁房间收费项目分录's 计费数量property 
     */
    public java.math.BigDecimal getFeeQuantity()
    {
        return getBigDecimal("feeQuantity");
    }
    public void setFeeQuantity(java.math.BigDecimal item)
    {
        setBigDecimal("feeQuantity", item);
    }
    /**
     * Object:租赁房间收费项目分录's 是否使用收费标准property 
     */
    public boolean isUseStandard()
    {
        return getBoolean("useStandard");
    }
    public void setUseStandard(boolean item)
    {
        setBoolean("useStandard", item);
    }
    /**
     * Object:租赁房间收费项目分录's 费用单价property 
     */
    public java.math.BigDecimal getUnitPrice()
    {
        return getBigDecimal("unitPrice");
    }
    public void setUnitPrice(java.math.BigDecimal item)
    {
        setBigDecimal("unitPrice", item);
    }
    /**
     * Object:租赁房间收费项目分录's 单价周期property 
     */
    public com.kingdee.eas.fdc.propertymgmt.ChargePricePeriod getPricePeriod()
    {
        return com.kingdee.eas.fdc.propertymgmt.ChargePricePeriod.getEnum(getString("pricePeriod"));
    }
    public void setPricePeriod(com.kingdee.eas.fdc.propertymgmt.ChargePricePeriod item)
    {
		if (item != null) {
        setString("pricePeriod", item.getValue());
		}
    }
    /**
     * Object:租赁房间收费项目分录's 价格因素property 
     */
    public com.kingdee.eas.fdc.propertymgmt.ChargePriceFactor getPriceFactor()
    {
        return com.kingdee.eas.fdc.propertymgmt.ChargePriceFactor.getEnum(getString("priceFactor"));
    }
    public void setPriceFactor(com.kingdee.eas.fdc.propertymgmt.ChargePriceFactor item)
    {
		if (item != null) {
        setString("priceFactor", item.getValue());
		}
    }
    /**
     * Object:租赁房间收费项目分录's 收费周期property 
     */
    public com.kingdee.eas.fdc.propertymgmt.ChargePeriod getChargePeriod()
    {
        return com.kingdee.eas.fdc.propertymgmt.ChargePeriod.getEnum(getString("chargePeriod"));
    }
    public void setChargePeriod(com.kingdee.eas.fdc.propertymgmt.ChargePeriod item)
    {
		if (item != null) {
        setString("chargePeriod", item.getValue());
		}
    }
    /**
     * Object:租赁房间收费项目分录's 周期类型property 
     */
    public com.kingdee.eas.fdc.propertymgmt.ChargePeriodType getChargePeriodTp()
    {
        return com.kingdee.eas.fdc.propertymgmt.ChargePeriodType.getEnum(getString("chargePeriodTp"));
    }
    public void setChargePeriodTp(com.kingdee.eas.fdc.propertymgmt.ChargePeriodType item)
    {
		if (item != null) {
        setString("chargePeriodTp", item.getValue());
		}
    }
    /**
     * Object:租赁房间收费项目分录's 缴费周期类型property 
     */
    public com.kingdee.eas.fdc.propertymgmt.ChargeDateType getChargeDateType()
    {
        return com.kingdee.eas.fdc.propertymgmt.ChargeDateType.getEnum(getString("chargeDateType"));
    }
    public void setChargeDateType(com.kingdee.eas.fdc.propertymgmt.ChargeDateType item)
    {
		if (item != null) {
        setString("chargeDateType", item.getValue());
		}
    }
    /**
     * Object:租赁房间收费项目分录's 缴费期限property 
     */
    public int getChargeTimeLimit()
    {
        return getInt("chargeTimeLimit");
    }
    public void setChargeTimeLimit(int item)
    {
        setInt("chargeTimeLimit", item);
    }
    /**
     * Object:租赁房间收费项目分录's 缴费期限单位property 
     */
    public com.kingdee.eas.fdc.propertymgmt.ChargeTimeLimitUnit getChargeDateUnit()
    {
        return com.kingdee.eas.fdc.propertymgmt.ChargeTimeLimitUnit.getEnum(getString("chargeDateUnit"));
    }
    public void setChargeDateUnit(com.kingdee.eas.fdc.propertymgmt.ChargeTimeLimitUnit item)
    {
		if (item != null) {
        setString("chargeDateUnit", item.getValue());
		}
    }
    /**
     * Object:租赁房间收费项目分录's 滞纳金比例property 
     */
    public java.math.BigDecimal getLateFeeRate()
    {
        return getBigDecimal("lateFeeRate");
    }
    public void setLateFeeRate(java.math.BigDecimal item)
    {
        setBigDecimal("lateFeeRate", item);
    }
    /**
     * Object:租赁房间收费项目分录's 滞纳金单位property 
     */
    public com.kingdee.eas.fdc.propertymgmt.LateFeeUnit getLateFeeUnit()
    {
        return com.kingdee.eas.fdc.propertymgmt.LateFeeUnit.getEnum(getString("lateFeeUnit"));
    }
    public void setLateFeeUnit(com.kingdee.eas.fdc.propertymgmt.LateFeeUnit item)
    {
		if (item != null) {
        setString("lateFeeUnit", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F784700C");
    }
}