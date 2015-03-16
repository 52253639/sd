package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractValuePlanDetialEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractValuePlanDetialEntryInfo()
    {
        this("id");
    }
    protected AbstractValuePlanDetialEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 销售计划明细分录 's 头 property 
     */
    public com.kingdee.eas.fdc.market.ValuePlanEntryInfo getEntryHead()
    {
        return (com.kingdee.eas.fdc.market.ValuePlanEntryInfo)get("entryHead");
    }
    public void setEntryHead(com.kingdee.eas.fdc.market.ValuePlanEntryInfo item)
    {
        put("entryHead", item);
    }
    /**
     * Object:销售计划明细分录's 年份property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:销售计划明细分录's 季度property 
     */
    public int getQuarter()
    {
        return getInt("quarter");
    }
    public void setQuarter(int item)
    {
        setInt("quarter", item);
    }
    /**
     * Object:销售计划明细分录's 月份property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object:销售计划明细分录's 类型property 
     */
    public com.kingdee.eas.fdc.market.ValuePlanDetialTypeEnum getType()
    {
        return com.kingdee.eas.fdc.market.ValuePlanDetialTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.market.ValuePlanDetialTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:销售计划明细分录's 装修标准property 
     */
    public com.kingdee.eas.fdc.market.DecorateEnum getDecorate()
    {
        return com.kingdee.eas.fdc.market.DecorateEnum.getEnum(getString("decorate"));
    }
    public void setDecorate(com.kingdee.eas.fdc.market.DecorateEnum item)
    {
		if (item != null) {
        setString("decorate", item.getValue());
		}
    }
    /**
     * Object:销售计划明细分录's 套数property 
     */
    public int getAccount()
    {
        return getInt("account");
    }
    public void setAccount(int item)
    {
        setInt("account", item);
    }
    /**
     * Object:销售计划明细分录's 面积property 
     */
    public java.math.BigDecimal getArea()
    {
        return getBigDecimal("area");
    }
    public void setArea(java.math.BigDecimal item)
    {
        setBigDecimal("area", item);
    }
    /**
     * Object:销售计划明细分录's 均价property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:销售计划明细分录's 金额property 
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
     * Object:销售计划明细分录's 套数property 
     */
    public int getCaccount()
    {
        return getInt("caccount");
    }
    public void setCaccount(int item)
    {
        setInt("caccount", item);
    }
    /**
     * Object:销售计划明细分录's 面积property 
     */
    public java.math.BigDecimal getCarea()
    {
        return getBigDecimal("carea");
    }
    public void setCarea(java.math.BigDecimal item)
    {
        setBigDecimal("carea", item);
    }
    /**
     * Object:销售计划明细分录's 均价property 
     */
    public java.math.BigDecimal getCprice()
    {
        return getBigDecimal("cprice");
    }
    public void setCprice(java.math.BigDecimal item)
    {
        setBigDecimal("cprice", item);
    }
    /**
     * Object:销售计划明细分录's 金额property 
     */
    public java.math.BigDecimal getCamount()
    {
        return getBigDecimal("camount");
    }
    public void setCamount(java.math.BigDecimal item)
    {
        setBigDecimal("camount", item);
    }
    /**
     * Object:销售计划明细分录's 套数property 
     */
    public int getXaccount()
    {
        return getInt("xaccount");
    }
    public void setXaccount(int item)
    {
        setInt("xaccount", item);
    }
    /**
     * Object:销售计划明细分录's 面积property 
     */
    public java.math.BigDecimal getXarea()
    {
        return getBigDecimal("xarea");
    }
    public void setXarea(java.math.BigDecimal item)
    {
        setBigDecimal("xarea", item);
    }
    /**
     * Object:销售计划明细分录's 均价property 
     */
    public java.math.BigDecimal getXprice()
    {
        return getBigDecimal("xprice");
    }
    public void setXprice(java.math.BigDecimal item)
    {
        setBigDecimal("xprice", item);
    }
    /**
     * Object:销售计划明细分录's 金额property 
     */
    public java.math.BigDecimal getXamount()
    {
        return getBigDecimal("xamount");
    }
    public void setXamount(java.math.BigDecimal item)
    {
        setBigDecimal("xamount", item);
    }
    /**
     * Object:销售计划明细分录's 套数property 
     */
    public int getSaccount()
    {
        return getInt("saccount");
    }
    public void setSaccount(int item)
    {
        setInt("saccount", item);
    }
    /**
     * Object:销售计划明细分录's 面积property 
     */
    public java.math.BigDecimal getSarea()
    {
        return getBigDecimal("sarea");
    }
    public void setSarea(java.math.BigDecimal item)
    {
        setBigDecimal("sarea", item);
    }
    /**
     * Object:销售计划明细分录's 均价property 
     */
    public java.math.BigDecimal getSprice()
    {
        return getBigDecimal("sprice");
    }
    public void setSprice(java.math.BigDecimal item)
    {
        setBigDecimal("sprice", item);
    }
    /**
     * Object:销售计划明细分录's 金额property 
     */
    public java.math.BigDecimal getSamount()
    {
        return getBigDecimal("samount");
    }
    public void setSamount(java.math.BigDecimal item)
    {
        setBigDecimal("samount", item);
    }
    /**
     * Object:销售计划明细分录's 回款property 
     */
    public java.math.BigDecimal getBackAmount()
    {
        return getBigDecimal("backAmount");
    }
    public void setBackAmount(java.math.BigDecimal item)
    {
        setBigDecimal("backAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2D4B1A48");
    }
}