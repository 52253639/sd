package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynCostSnapShotSettEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDynCostSnapShotSettEntryInfo()
    {
        this("id");
    }
    protected AbstractDynCostSnapShotSettEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:动态成本快照结算分录's 变更类型IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getChangeTypeId()
    {
        return getBOSUuid("changeTypeId");
    }
    public void setChangeTypeId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("changeTypeId", item);
    }
    /**
     * Object:动态成本快照结算分录's 未结算金额property 
     */
    public java.math.BigDecimal getUnSettleAmt()
    {
        return getBigDecimal("unSettleAmt");
    }
    public void setUnSettleAmt(java.math.BigDecimal item)
    {
        setBigDecimal("unSettleAmt", item);
    }
    /**
     * Object:动态成本快照结算分录's 已结算金额property 
     */
    public java.math.BigDecimal getSettleAmt()
    {
        return getBigDecimal("settleAmt");
    }
    public void setSettleAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settleAmt", item);
    }
    /**
     * Object: 动态成本快照结算分录 's 动态成本快照 property 
     */
    public com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7A9818CD");
    }
}