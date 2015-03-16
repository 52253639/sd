package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMovementPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMovementPlanInfo()
    {
        this("id");
    }
    protected AbstractMovementPlanInfo(String pkField)
    {
        super(pkField);
        put("E4", new com.kingdee.eas.fdc.market.MovementPlanE4Collection());
        put("E2", new com.kingdee.eas.fdc.market.MovementPlanE2Collection());
        put("E3", new com.kingdee.eas.fdc.market.MovementPlanE3Collection());
        put("E5", new com.kingdee.eas.fdc.market.MovementPlanE5Collection());
        put("E6", new com.kingdee.eas.fdc.market.MovementPlanE6Collection());
    }
    /**
     * Object:活动方案's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object: 活动方案 's 计划费用 property 
     */
    public com.kingdee.eas.fdc.market.MovementPlanE2Collection getE2()
    {
        return (com.kingdee.eas.fdc.market.MovementPlanE2Collection)get("E2");
    }
    /**
     * Object: 活动方案 's 计划参与人员 property 
     */
    public com.kingdee.eas.fdc.market.MovementPlanE3Collection getE3()
    {
        return (com.kingdee.eas.fdc.market.MovementPlanE3Collection)get("E3");
    }
    /**
     * Object: 活动方案 's 计划详细步骤 property 
     */
    public com.kingdee.eas.fdc.market.MovementPlanE4Collection getE4()
    {
        return (com.kingdee.eas.fdc.market.MovementPlanE4Collection)get("E4");
    }
    /**
     * Object: 活动方案 's 活动类型 property 
     */
    public com.kingdee.eas.fdc.market.MarketTypeInfo getMmType()
    {
        return (com.kingdee.eas.fdc.market.MarketTypeInfo)get("mmType");
    }
    public void setMmType(com.kingdee.eas.fdc.market.MarketTypeInfo item)
    {
        put("mmType", item);
    }
    /**
     * Object: 活动方案 's 营销项目 property 
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
     * Object:活动方案's 方案类型property 
     */
    public com.kingdee.eas.fdc.market.SchemeTypeEnum getSchemeType()
    {
        return com.kingdee.eas.fdc.market.SchemeTypeEnum.getEnum(getString("schemeType"));
    }
    public void setSchemeType(com.kingdee.eas.fdc.market.SchemeTypeEnum item)
    {
		if (item != null) {
        setString("schemeType", item.getValue());
		}
    }
    /**
     * Object:活动方案's 所属系统property 
     */
    public com.kingdee.eas.fdc.basedata.MoneySysTypeEnum getBelongSystem()
    {
        return com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.getEnum(getString("belongSystem"));
    }
    public void setBelongSystem(com.kingdee.eas.fdc.basedata.MoneySysTypeEnum item)
    {
		if (item != null) {
        setString("belongSystem", item.getValue());
		}
    }
    /**
     * Object:活动方案's 开始日期property 
     */
    public java.util.Date getBeginDate()
    {
        return getDate("beginDate");
    }
    public void setBeginDate(java.util.Date item)
    {
        setDate("beginDate", item);
    }
    /**
     * Object:活动方案's 结束日期property 
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
     * Object:活动方案's 计划总金额property 
     */
    public java.math.BigDecimal getTotalMoney()
    {
        return getBigDecimal("totalMoney");
    }
    public void setTotalMoney(java.math.BigDecimal item)
    {
        setBigDecimal("totalMoney", item);
    }
    /**
     * Object:活动方案's 是否显示详细金额property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("isEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("isEnable", item);
    }
    /**
     * Object:活动方案's 活动主题property 
     */
    public String getMovementTheme()
    {
        return getString("movementTheme");
    }
    public void setMovementTheme(String item)
    {
        setString("movementTheme", item);
    }
    /**
     * Object:活动方案's 折扣说明property 
     */
    public String getDiscountExp()
    {
        return getString("discountExp");
    }
    public void setDiscountExp(String item)
    {
        setString("discountExp", item);
    }
    /**
     * Object: 活动方案 's 预计效果 property 
     */
    public com.kingdee.eas.fdc.market.MovementPlanE5Collection getE5()
    {
        return (com.kingdee.eas.fdc.market.MovementPlanE5Collection)get("E5");
    }
    /**
     * Object: 活动方案 's 媒体管理 property 
     */
    public com.kingdee.eas.fdc.market.MovementPlanE6Collection getE6()
    {
        return (com.kingdee.eas.fdc.market.MovementPlanE6Collection)get("E6");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3A1444C9");
    }
}