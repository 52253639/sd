package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProcurementPlanningAttEntryInfo extends com.kingdee.eas.fdc.invite.BaseInviteEntryInfo implements Serializable 
{
    public AbstractProcurementPlanningAttEntryInfo()
    {
        this("id");
    }
    protected AbstractProcurementPlanningAttEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 采购策划附件分录 's 招标策划 property 
     */
    public com.kingdee.eas.fdc.invite.ProcurementPlanningInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.ProcurementPlanningInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.ProcurementPlanningInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F588B9FA");
    }
}