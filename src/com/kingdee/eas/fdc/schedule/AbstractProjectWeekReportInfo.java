package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectWeekReportInfo extends com.kingdee.eas.fdc.schedule.OpReportBaseInfo implements Serializable 
{
    public AbstractProjectWeekReportInfo()
    {
        this("id");
    }
    protected AbstractProjectWeekReportInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryCollection());
    }
    /**
     * Object: 项目周报告 's 报告分录 property 
     */
    public com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("23D2312B");
    }
}