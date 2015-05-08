package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskWorkResultEntryFileInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTaskWorkResultEntryFileInfo()
    {
        this("id");
    }
    protected AbstractTaskWorkResultEntryFileInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:工作成果分录附件's 文件名property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:工作成果分录附件's 上传日期property 
     */
    public java.util.Date getUploadDate()
    {
        return getDate("uploadDate");
    }
    public void setUploadDate(java.util.Date item)
    {
        setDate("uploadDate", item);
    }
    /**
     * Object:工作成果分录附件's 附件property 
     */
    public byte[] getData()
    {
        return (byte[])get("data");
    }
    public void setData(byte[] item)
    {
        put("data", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4BEA93C5");
    }
}