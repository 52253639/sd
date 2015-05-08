package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectWeekReportEntryInfo extends com.kingdee.eas.fdc.schedule.OpReportEntryBaseInfo implements Serializable 
{
    public AbstractProjectWeekReportEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectWeekReportEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目周报告分录 's 报告头 property 
     */
    public com.kingdee.eas.fdc.schedule.ProjectWeekReportInfo getHead()
    {
        return (com.kingdee.eas.fdc.schedule.ProjectWeekReportInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.schedule.ProjectWeekReportInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5A44EA07");
    }
}