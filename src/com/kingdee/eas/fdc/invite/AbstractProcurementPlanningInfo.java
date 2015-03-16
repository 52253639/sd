package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProcurementPlanningInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProcurementPlanningInfo()
    {
        this("id");
    }
    protected AbstractProcurementPlanningInfo(String pkField)
    {
        super(pkField);
        put("attEntry", new com.kingdee.eas.fdc.invite.ProcurementPlanningAttEntryCollection());
        put("entry", new com.kingdee.eas.fdc.invite.ProcurementPlanningEntryCollection());
    }
    /**
     * Object: 采购策划 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 采购策划 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.ProcurementPlanningEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.ProcurementPlanningEntryCollection)get("entry");
    }
    /**
     * Object: 采购策划 's 附件分录 property 
     */
    public com.kingdee.eas.fdc.invite.ProcurementPlanningAttEntryCollection getAttEntry()
    {
        return (com.kingdee.eas.fdc.invite.ProcurementPlanningAttEntryCollection)get("attEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("898BD489");
    }
}