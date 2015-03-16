package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvitePreSplitEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInvitePreSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractInvitePreSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 招标预拆分分录 's 招标预拆分 property 
     */
    public com.kingdee.eas.fdc.invite.InvitePreSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InvitePreSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InvitePreSplitInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 招标预拆分分录 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: 招标预拆分分录 's 成本科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object:招标预拆分分录's 预拆分金额property 
     */
    public java.math.BigDecimal getPreSplitAmount()
    {
        return getBigDecimal("preSplitAmount");
    }
    public void setPreSplitAmount(java.math.BigDecimal item)
    {
        setBigDecimal("preSplitAmount", item);
    }
    /**
     * Object:招标预拆分分录's 目标成本property 
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
     * Object:招标预拆分分录's 已发生成本property 
     */
    public java.math.BigDecimal getOccuredCost()
    {
        return getBigDecimal("occuredCost");
    }
    public void setOccuredCost(java.math.BigDecimal item)
    {
        setBigDecimal("occuredCost", item);
    }
    /**
     * Object:招标预拆分分录's 已经预拆分成本property 
     */
    public java.math.BigDecimal getSplitedCost()
    {
        return getBigDecimal("splitedCost");
    }
    public void setSplitedCost(java.math.BigDecimal item)
    {
        setBigDecimal("splitedCost", item);
    }
    /**
     * Object:招标预拆分分录's 动态成本property 
     */
    public java.math.BigDecimal getDynamicCost()
    {
        return getBigDecimal("dynamicCost");
    }
    public void setDynamicCost(java.math.BigDecimal item)
    {
        setBigDecimal("dynamicCost", item);
    }
    /**
     * Object:招标预拆分分录's 差额property 
     */
    public java.math.BigDecimal getSubstractAmount()
    {
        return getBigDecimal("substractAmount");
    }
    public void setSubstractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("substractAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E08DF64E");
    }
}