package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAchievementDocEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractAchievementDocEntryInfo()
    {
        this("id");
    }
    protected AbstractAchievementDocEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:成果文档分录's 文档路径property 
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
     * Object:成果文档分录's 文档名称property 
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
     * Object:成果文档分录's 文档IDproperty 
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
     * Object:成果文档分录's 文档类型property 
     */
    public String getDocType()
    {
        return getString("docType");
    }
    public void setDocType(String item)
    {
        setString("docType", item);
    }
    /**
     * Object: 成果文档分录 's 成果管理 property 
     */
    public com.kingdee.eas.fdc.schedule.AchievementManagerInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.AchievementManagerInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.AchievementManagerInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5A8292D3");
    }
}