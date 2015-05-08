package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthReportEntryInfo extends com.kingdee.eas.fdc.schedule.OpReportEntryBaseInfo implements Serializable 
{
    public AbstractProjectMonthReportEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthReportEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目月报告分录 's 月报头 property 
     */
    public com.kingdee.eas.fdc.schedule.ProjectMonthReportInfo getHead()
    {
        return (com.kingdee.eas.fdc.schedule.ProjectMonthReportInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.schedule.ProjectMonthReportInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5285F9A1");
    }
}