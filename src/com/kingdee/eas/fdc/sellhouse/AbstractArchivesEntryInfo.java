package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractArchivesEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractArchivesEntryInfo()
    {
        this("id");
    }
    protected AbstractArchivesEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 具体档案分录 's 项目档案分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C90B0406");
    }
}