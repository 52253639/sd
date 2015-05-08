package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthReportInfo extends com.kingdee.eas.fdc.schedule.OpReportBaseInfo implements Serializable 
{
    public AbstractProjectMonthReportInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthReportInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryCollection());
    }
    /**
     * Object: 项目月报告 's 月报分录 property 
     */
    public com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("58784951");
    }
}