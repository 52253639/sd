package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketManageInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractMarketManageInfo()
    {
        this("id");
    }
    protected AbstractMarketManageInfo(String pkField)
    {
        super(pkField);
        put("Effect", new com.kingdee.eas.fdc.market.MarketManageEffectCollection());
        put("Charge", new com.kingdee.eas.fdc.market.MarketManageChargeCollection());
        put("entrys", new com.kingdee.eas.fdc.market.MarketManageEntryCollection());
        put("Customer", new com.kingdee.eas.fdc.market.MarketManageCustomerCollection());
        put("Media", new com.kingdee.eas.fdc.market.MarketManageMediaCollection());
    }
    /**
     * Object: 营销活动 's 分录 property 
     */
    public com.kingdee.eas.fdc.market.MarketManageEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.market.MarketManageEntryCollection)get("entrys");
    }
    /**
     * Object:营销活动's 是否生成凭证property 
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
     * Object:营销活动's 活动方案名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object: 营销活动 's 关联活动方案 property 
     */
    public com.kingdee.eas.fdc.market.MovementPlanInfo getMarketPlan()
    {
        return (com.kingdee.eas.fdc.market.MovementPlanInfo)get("marketPlan");
    }
    public void setMarketPlan(com.kingdee.eas.fdc.market.MovementPlanInfo item)
    {
        put("marketPlan", item);
    }
    /**
     * Object:营销活动's 组织名称property 
     */
    public String getOrgName()
    {
        return getString("orgName");
    }
    public void setOrgName(String item)
    {
        setString("orgName", item);
    }
    /**
     * Object: 营销活动 's 营销项目 property 
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
     * Object:营销活动's 方案类型property 
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
     * Object:营销活动's 开始日期property 
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
     * Object:营销活动's 结束日期property 
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
     * Object:营销活动's 所属系统property 
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
     * Object:营销活动's 活动主题property 
     */
    public String getMovemntTheme()
    {
        return getString("movemntTheme");
    }
    public void setMovemntTheme(String item)
    {
        setString("movemntTheme", item);
    }
    /**
     * Object: 营销活动 's 活动类型 property 
     */
    public com.kingdee.eas.fdc.market.MarketTypeInfo getMarkettype()
    {
        return (com.kingdee.eas.fdc.market.MarketTypeInfo)get("markettype");
    }
    public void setMarkettype(com.kingdee.eas.fdc.market.MarketTypeInfo item)
    {
        put("markettype", item);
    }
    /**
     * Object:营销活动's 计划总费用property 
     */
    public java.math.BigDecimal getPlanTotalMoney()
    {
        return getBigDecimal("planTotalMoney");
    }
    public void setPlanTotalMoney(java.math.BigDecimal item)
    {
        setBigDecimal("planTotalMoney", item);
    }
    /**
     * Object:营销活动's 实际总费用property 
     */
    public java.math.BigDecimal getFactTotalMoney()
    {
        return getBigDecimal("factTotalMoney");
    }
    public void setFactTotalMoney(java.math.BigDecimal item)
    {
        setBigDecimal("factTotalMoney", item);
    }
    /**
     * Object:营销活动's 实际付款额property 
     */
    public java.math.BigDecimal getFactPayment()
    {
        return getBigDecimal("factPayment");
    }
    public void setFactPayment(java.math.BigDecimal item)
    {
        setBigDecimal("factPayment", item);
    }
    /**
     * Object: 营销活动 's 计划详细步骤 property 
     */
    public com.kingdee.eas.fdc.market.MarketManageChargeCollection getCharge()
    {
        return (com.kingdee.eas.fdc.market.MarketManageChargeCollection)get("Charge");
    }
    /**
     * Object: 营销活动 's 计划参与人员 property 
     */
    public com.kingdee.eas.fdc.market.MarketManageCustomerCollection getCustomer()
    {
        return (com.kingdee.eas.fdc.market.MarketManageCustomerCollection)get("Customer");
    }
    /**
     * Object: 营销活动 's 实际效果 property 
     */
    public com.kingdee.eas.fdc.market.MarketManageEffectCollection getEffect()
    {
        return (com.kingdee.eas.fdc.market.MarketManageEffectCollection)get("Effect");
    }
    /**
     * Object:营销活动's 合同号property 
     */
    public String getContractNumber()
    {
        return getString("contractNumber");
    }
    public void setContractNumber(String item)
    {
        setString("contractNumber", item);
    }
    /**
     * Object: 营销活动 's 媒体管理 property 
     */
    public com.kingdee.eas.fdc.market.MarketManageMediaCollection getMedia()
    {
        return (com.kingdee.eas.fdc.market.MarketManageMediaCollection)get("Media");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5F2D3512");
    }
}