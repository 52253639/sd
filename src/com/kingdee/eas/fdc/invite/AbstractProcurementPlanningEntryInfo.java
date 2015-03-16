package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProcurementPlanningEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProcurementPlanningEntryInfo()
    {
        this("id");
    }
    protected AbstractProcurementPlanningEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:采购策划分录's 合同份数property 
     */
    public java.math.BigDecimal getContractCount()
    {
        return getBigDecimal("contractCount");
    }
    public void setContractCount(java.math.BigDecimal item)
    {
        setBigDecimal("contractCount", item);
    }
    /**
     * Object:采购策划分录's 备注property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object: 采购策划分录 's null property 
     */
    public com.kingdee.eas.fdc.invite.ProcurementPlanningInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.ProcurementPlanningInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.ProcurementPlanningInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 采购策划分录 's 合同架构 property 
     */
    public com.kingdee.eas.fdc.invite.InviteContractFrameInfo getContractFrame()
    {
        return (com.kingdee.eas.fdc.invite.InviteContractFrameInfo)get("contractFrame");
    }
    public void setContractFrame(com.kingdee.eas.fdc.invite.InviteContractFrameInfo item)
    {
        put("contractFrame", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6680B169");
    }
}