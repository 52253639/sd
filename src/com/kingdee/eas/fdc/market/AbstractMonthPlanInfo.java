package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonthPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMonthPlanInfo()
    {
        this("id");
    }
    protected AbstractMonthPlanInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.market.MonthPlanEntryCollection());
        put("proReference", new com.kingdee.eas.fdc.market.ProReferenceCollection());
    }
    /**
     * Object: 销售月度计划 's 销售项目 property 
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
     * Object:销售月度计划's 计划年property 
     */
    public int getPlanYear()
    {
        return getInt("planYear");
    }
    public void setPlanYear(int item)
    {
        setInt("planYear", item);
    }
    /**
     * Object:销售月度计划's 计划月property 
     */
    public int getPlanMonth()
    {
        return getInt("planMonth");
    }
    public void setPlanMonth(int item)
    {
        setInt("planMonth", item);
    }
    /**
     * Object:销售月度计划's 版本号property 
     */
    public String getVersoin()
    {
        return getString("versoin");
    }
    public void setVersoin(String item)
    {
        setString("versoin", item);
    }
    /**
     * Object:销售月度计划's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object: 销售月度计划 's 项目情况参考 property 
     */
    public com.kingdee.eas.fdc.market.ProReferenceCollection getProReference()
    {
        return (com.kingdee.eas.fdc.market.ProReferenceCollection)get("proReference");
    }
    /**
     * Object: 销售月度计划 's 分录 property 
     */
    public com.kingdee.eas.fdc.market.MonthPlanEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.market.MonthPlanEntryCollection)get("entrys");
    }
    /**
     * Object:销售月度计划's 起始年度property 
     */
    public int getStartYear()
    {
        return getInt("startYear");
    }
    public void setStartYear(int item)
    {
        setInt("startYear", item);
    }
    /**
     * Object:销售月度计划's 周期property 
     */
    public int getCycle()
    {
        return getInt("cycle");
    }
    public void setCycle(int item)
    {
        setInt("cycle", item);
    }
    /**
     * Object:销售月度计划's 版本类型property 
     */
    public com.kingdee.eas.fdc.market.VersionTypeEnum getVersionType()
    {
        return com.kingdee.eas.fdc.market.VersionTypeEnum.getEnum(getString("versionType"));
    }
    public void setVersionType(com.kingdee.eas.fdc.market.VersionTypeEnum item)
    {
		if (item != null) {
        setString("versionType", item.getValue());
		}
    }
    /**
     * Object:销售月度计划's 预估销售面积property 
     */
    public java.math.BigDecimal getPreArea()
    {
        return getBigDecimal("preArea");
    }
    public void setPreArea(java.math.BigDecimal item)
    {
        setBigDecimal("preArea", item);
    }
    /**
     * Object:销售月度计划's 预估销售套数property 
     */
    public java.math.BigDecimal getPreCount()
    {
        return getBigDecimal("preCount");
    }
    public void setPreCount(java.math.BigDecimal item)
    {
        setBigDecimal("preCount", item);
    }
    /**
     * Object:销售月度计划's 预估销售均价property 
     */
    public java.math.BigDecimal getPrePrice()
    {
        return getBigDecimal("prePrice");
    }
    public void setPrePrice(java.math.BigDecimal item)
    {
        setBigDecimal("prePrice", item);
    }
    /**
     * Object:销售月度计划's 预估销售金额property 
     */
    public java.math.BigDecimal getPreAmount()
    {
        return getBigDecimal("preAmount");
    }
    public void setPreAmount(java.math.BigDecimal item)
    {
        setBigDecimal("preAmount", item);
    }
    /**
     * Object:销售月度计划's 预估回款金额property 
     */
    public java.math.BigDecimal getPreRecover()
    {
        return getBigDecimal("preRecover");
    }
    public void setPreRecover(java.math.BigDecimal item)
    {
        setBigDecimal("preRecover", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A4CA7718");
    }
}