package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketingUnitInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractMarketingUnitInfo()
    {
        this("id");
    }
    protected AbstractMarketingUnitInfo(String pkField)
    {
        super(pkField);
        put("member", new com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection());
        put("sellProject", new com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectCollection());
    }
    /**
     * Object: 营销单元 's 父结点 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 营销单元 's 组织单元 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object: 营销单元 's 项目分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectCollection getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectCollection)get("sellProject");
    }
    /**
     * Object: 营销单元 's 成员分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection getMember()
    {
        return (com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection)get("member");
    }
    /**
     * Object:营销单元's 是否销售职能property 
     */
    public boolean isIsSellFunction()
    {
        return getBoolean("isSellFunction");
    }
    public void setIsSellFunction(boolean item)
    {
        setBoolean("isSellFunction", item);
    }
    /**
     * Object:营销单元's 是否租恁职能property 
     */
    public boolean isIsTenancyFunction()
    {
        return getBoolean("isTenancyFunction");
    }
    public void setIsTenancyFunction(boolean item)
    {
        setBoolean("isTenancyFunction", item);
    }
    /**
     * Object:营销单元's 是否物业职能property 
     */
    public boolean isIsWuYeFunction()
    {
        return getBoolean("isWuYeFunction");
    }
    public void setIsWuYeFunction(boolean item)
    {
        setBoolean("isWuYeFunction", item);
    }
    /**
     * Object:营销单元's 是否有营销职能property 
     */
    public boolean isIsMarketFunction()
    {
        return getBoolean("isMarketFunction");
    }
    public void setIsMarketFunction(boolean item)
    {
        setBoolean("isMarketFunction", item);
    }
    /**
     * Object:营销单元's 是否有工程职能property 
     */
    public boolean isIsProjectFunction()
    {
        return getBoolean("isProjectFunction");
    }
    public void setIsProjectFunction(boolean item)
    {
        setBoolean("isProjectFunction", item);
    }
    /**
     * Object:营销单元's 是否有会员职能property 
     */
    public boolean isIsInsideFunction()
    {
        return getBoolean("isInsideFunction");
    }
    public void setIsInsideFunction(boolean item)
    {
        setBoolean("isInsideFunction", item);
    }
    /**
     * Object:营销单元's 启用property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B6B669CF");
    }
}