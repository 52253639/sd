package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketingUnitMemberInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMarketingUnitMemberInfo()
    {
        this("id");
    }
    protected AbstractMarketingUnitMemberInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 营销单元成员 's 营销单元头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 营销单元成员 's 成员 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getMember()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("member");
    }
    public void setMember(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("member", item);
    }
    /**
     * Object:营销单元成员's 是否负责人property 
     */
    public boolean isIsDuty()
    {
        return getBoolean("isDuty");
    }
    public void setIsDuty(boolean item)
    {
        setBoolean("isDuty", item);
    }
    /**
     * Object:营销单元成员's 负责人职称property 
     */
    public String getDutyPersonTitle()
    {
        return getString("dutyPersonTitle");
    }
    public void setDutyPersonTitle(String item)
    {
        setString("dutyPersonTitle", item);
    }
    /**
     * Object:营销单元成员's 是否有修改职员客户的权限property 
     */
    public boolean isIsUpdateMember()
    {
        return getBoolean("isUpdateMember");
    }
    public void setIsUpdateMember(boolean item)
    {
        setBoolean("isUpdateMember", item);
    }
    /**
     * Object:营销单元成员's 是否有业务操作权限property 
     */
    public boolean isIsOperation()
    {
        return getBoolean("isOperation");
    }
    public void setIsOperation(boolean item)
    {
        setBoolean("isOperation", item);
    }
    /**
     * Object:营销单元成员's 是否间接提佣property 
     */
    public boolean isIsSharePercent()
    {
        return getBoolean("isSharePercent");
    }
    public void setIsSharePercent(boolean item)
    {
        setBoolean("isSharePercent", item);
    }
    /**
     * Object:营销单元成员's 间接分单比例property 
     */
    public java.math.BigDecimal getTakePercentage()
    {
        return getBigDecimal("takePercentage");
    }
    public void setTakePercentage(java.math.BigDecimal item)
    {
        setBigDecimal("takePercentage", item);
    }
    /**
     * Object:营销单元成员's 是否有销售职能property 
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
     * Object:营销单元成员's 是否有租恁职能property 
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
     * Object:营销单元成员's 是否有物业职能property 
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
     * Object:营销单元成员's 是否有营销职能property 
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
     * Object:营销单元成员's 是否有工程职能property 
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
     * Object:营销单元成员's 是否有会员职能property 
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
     * Object:营销单元成员's 上岗日期property 
     */
    public java.util.Date getAccessionDate()
    {
        return getDate("accessionDate");
    }
    public void setAccessionDate(java.util.Date item)
    {
        setDate("accessionDate", item);
    }
    /**
     * Object:营销单元成员's 离岗日期property 
     */
    public java.util.Date getDimissionDate()
    {
        return getDate("dimissionDate");
    }
    public void setDimissionDate(java.util.Date item)
    {
        setDate("dimissionDate", item);
    }
    /**
     * Object:营销单元成员's 佣金计算property 
     */
    public String getCommisionCal()
    {
        return getString("commisionCal");
    }
    public void setCommisionCal(String item)
    {
        setString("commisionCal", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("13D50949");
    }
}