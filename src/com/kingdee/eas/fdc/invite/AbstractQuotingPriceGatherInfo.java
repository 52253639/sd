package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuotingPriceGatherInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractQuotingPriceGatherInfo()
    {
        this("id");
    }
    protected AbstractQuotingPriceGatherInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:经济表评审汇总表's 废标情况property 
     */
    public boolean isStatus()
    {
        return getBoolean("status");
    }
    public void setStatus(boolean item)
    {
        setBoolean("status", item);
    }
    /**
     * Object:经济表评审汇总表's 废标情况说明property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: 经济表评审汇总表 's 关联招标清单 property 
     */
    public com.kingdee.eas.fdc.invite.NewListingInfo getInviteListing()
    {
        return (com.kingdee.eas.fdc.invite.NewListingInfo)get("inviteListing");
    }
    public void setInviteListing(com.kingdee.eas.fdc.invite.NewListingInfo item)
    {
        put("inviteListing", item);
    }
    /**
     * Object:经济表评审汇总表's 建筑面积property 
     */
    public float getBuildArea()
    {
        return getFloat("buildArea");
    }
    public void setBuildArea(float item)
    {
        setFloat("buildArea", item);
    }
    /**
     * Object:经济表评审汇总表's 目标成本property 
     */
    public java.math.BigDecimal getAimCost()
    {
        return getBigDecimal("aimCost");
    }
    public void setAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("aimCost", item);
    }
    /**
     * Object:经济表评审汇总表's 废标人名称property 
     */
    public String getGatherName()
    {
        return getString("gatherName");
    }
    public void setGatherName(String item)
    {
        setString("gatherName", item);
    }
    /**
     * Object:经济表评审汇总表's 面积类型property 
     */
    public String getAreaType()
    {
        return getString("areaType");
    }
    public void setAreaType(String item)
    {
        setString("areaType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("68D5F0CF");
    }
}