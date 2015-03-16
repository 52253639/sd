package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCCostChangeSettEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCCostChangeSettEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCCostChangeSettEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:报表中间表变更结算分录's 变更类型IDproperty 
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
     * Object:报表中间表变更结算分录's 未结算金额property 
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
     * Object:报表中间表变更结算分录's 已结算金额property 
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
     * Object:报表中间表变更结算分录's FDCCost头property 
     */
    public com.kingdee.bos.util.BOSUuid getParentID()
    {
        return getBOSUuid("parentID");
    }
    public void setParentID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("parentID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("58EBC455");
    }
}