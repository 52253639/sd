package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractStandardTaskGuideInfo extends com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo implements Serializable 
{
    public AbstractStandardTaskGuideInfo()
    {
        this("id");
    }
    protected AbstractStandardTaskGuideInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.schedule.StandardTaskGuideEntryCollection());
    }
    /**
     * Object: 标准任务指引 's 分录 property 
     */
    public com.kingdee.eas.fdc.schedule.StandardTaskGuideEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.StandardTaskGuideEntryCollection)get("entrys");
    }
    /**
     * Object:标准任务指引's 任务类别property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum getTaskType()
    {
        return com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum.getEnum(getString("taskType"));
    }
    public void setTaskType(com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum item)
    {
		if (item != null) {
        setString("taskType", item.getValue());
		}
    }
    /**
     * Object:标准任务指引's 标准工期property 
     */
    public java.math.BigDecimal getStandardDuration()
    {
        return getBigDecimal("standardDuration");
    }
    public void setStandardDuration(java.math.BigDecimal item)
    {
        setBigDecimal("standardDuration", item);
    }
    /**
     * Object:标准任务指引's 协同文档IDproperty 
     */
    public String getCpDocID()
    {
        return getString("cpDocID");
    }
    public void setCpDocID(String item)
    {
        setString("cpDocID", item);
    }
    /**
     * Object: 标准任务指引 's parent property 
     */
    public com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0E9C4124");
    }
}