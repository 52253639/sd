package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHelpPersonEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractHelpPersonEntryInfo()
    {
        this("id");
    }
    protected AbstractHelpPersonEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 协助人分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 协助人分录 's 协助人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EC59AD86");
    }
}