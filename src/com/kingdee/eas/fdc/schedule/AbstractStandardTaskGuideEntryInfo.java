package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractStandardTaskGuideEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractStandardTaskGuideEntryInfo()
    {
        this("id");
    }
    protected AbstractStandardTaskGuideEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 标准任务指引分录 's 标准任务指引 property 
     */
    public com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:标准任务指引分录's 文档目录property 
     */
    public String getDocPath()
    {
        return getString("docPath");
    }
    public void setDocPath(String item)
    {
        setString("docPath", item);
    }
    /**
     * Object:标准任务指引分录's 文档名称property 
     */
    public String getDocName()
    {
        return getString("docName");
    }
    public void setDocName(String item)
    {
        setString("docName", item);
    }
    /**
     * Object:标准任务指引分录's 是否上传到协同property 
     */
    public boolean isIsCPFile()
    {
        return getBoolean("isCPFile");
    }
    public void setIsCPFile(boolean item)
    {
        setBoolean("isCPFile", item);
    }
    /**
     * Object:标准任务指引分录's 文档的IDproperty 
     */
    public String getDocID()
    {
        return getString("docID");
    }
    public void setDocID(String item)
    {
        setString("docID", item);
    }
    /**
     * Object:标准任务指引分录's 指示类型property 
     */
    public com.kingdee.eas.fdc.schedule.GuideTypeEnum getGuideType()
    {
        return com.kingdee.eas.fdc.schedule.GuideTypeEnum.getEnum(getString("guideType"));
    }
    public void setGuideType(com.kingdee.eas.fdc.schedule.GuideTypeEnum item)
    {
		if (item != null) {
        setString("guideType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DF52EDAE");
    }
}