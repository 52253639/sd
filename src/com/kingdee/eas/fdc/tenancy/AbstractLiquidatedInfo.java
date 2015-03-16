package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractLiquidatedInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractLiquidatedInfo()
    {
        this("id");
    }
    protected AbstractLiquidatedInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 违约金计算方案设置 's 租售项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: 违约金计算方案设置 's 款项名称 property 
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
     * Object:违约金计算方案设置's 生效日期property 
     */
    public java.util.Date getEffectDate()
    {
        return getDate("effectDate");
    }
    public void setEffectDate(java.util.Date item)
    {
        setDate("effectDate", item);
    }
    /**
     * Object:违约金计算方案设置's 发生状态property 
     */
    public com.kingdee.eas.fdc.tenancy.OccurreStateEnum getOccurred()
    {
        return com.kingdee.eas.fdc.tenancy.OccurreStateEnum.getEnum(getString("occurred"));
    }
    public void setOccurred(com.kingdee.eas.fdc.tenancy.OccurreStateEnum item)
    {
		if (item != null) {
        setString("occurred", item.getValue());
		}
    }
    /**
     * Object:违约金计算方案设置's 违约金计算标准property 
     */
    public int getStandard()
    {
        return getInt("standard");
    }
    public void setStandard(int item)
    {
        setInt("standard", item);
    }
    /**
     * Object:违约金计算方案设置's 违约金计算标准日期property 
     */
    public com.kingdee.eas.fdc.tenancy.DateEnum getStandardDate()
    {
        return com.kingdee.eas.fdc.tenancy.DateEnum.getEnum(getString("standardDate"));
    }
    public void setStandardDate(com.kingdee.eas.fdc.tenancy.DateEnum item)
    {
		if (item != null) {
        setString("standardDate", item.getValue());
		}
    }
    /**
     * Object:违约金计算方案设置's 违约金比例property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object:违约金计算方案设置's 违约金比例日期property 
     */
    public com.kingdee.eas.fdc.tenancy.DateEnum getRateDate()
    {
        return com.kingdee.eas.fdc.tenancy.DateEnum.getEnum(getString("rateDate"));
    }
    public void setRateDate(com.kingdee.eas.fdc.tenancy.DateEnum item)
    {
		if (item != null) {
        setString("rateDate", item.getValue());
		}
    }
    /**
     * Object:违约金计算方案设置's 违约金减免property 
     */
    public int getRelief()
    {
        return getInt("relief");
    }
    public void setRelief(int item)
    {
        setInt("relief", item);
    }
    /**
     * Object:违约金计算方案设置's 违约金减免日期property 
     */
    public com.kingdee.eas.fdc.tenancy.DateEnum getReliefDate()
    {
        return com.kingdee.eas.fdc.tenancy.DateEnum.getEnum(getString("reliefDate"));
    }
    public void setReliefDate(com.kingdee.eas.fdc.tenancy.DateEnum item)
    {
		if (item != null) {
        setString("reliefDate", item.getValue());
		}
    }
    /**
     * Object:违约金计算方案设置's 违约金保留位property 
     */
    public com.kingdee.eas.fdc.tenancy.MoneyEnum getBit()
    {
        return com.kingdee.eas.fdc.tenancy.MoneyEnum.getEnum(getString("bit"));
    }
    public void setBit(com.kingdee.eas.fdc.tenancy.MoneyEnum item)
    {
		if (item != null) {
        setString("bit", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A0C675B7");
    }
}