package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectImageEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectImageEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectImageEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:项目形象进度分录's 文件property 
     */
    public byte[] getFile()
    {
        return (byte[])get("file");
    }
    public void setFile(byte[] item)
    {
        put("file", item);
    }
    /**
     * Object:项目形象进度分录's 大小property 
     */
    public String getSize()
    {
        return getString("size");
    }
    public void setSize(String item)
    {
        setString("size", item);
    }
    /**
     * Object:项目形象进度分录's nullproperty 
     */
    public int getSizeInByte()
    {
        return getInt("sizeInByte");
    }
    public void setSizeInByte(int item)
    {
        setInt("sizeInByte", item);
    }
    /**
     * Object:项目形象进度分录's 文件名property 
     */
    public String getFileName()
    {
        return getString("fileName");
    }
    public void setFileName(String item)
    {
        setString("fileName", item);
    }
    /**
     * Object:项目形象进度分录's 图片名称property 
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
     * Object:项目形象进度分录's 施工状态property 
     */
    public com.kingdee.eas.fdc.schedule.ProjectStatusEnum getType()
    {
        return com.kingdee.eas.fdc.schedule.ProjectStatusEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.schedule.ProjectStatusEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object: 项目形象进度分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.schedule.ProjectImageInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.ProjectImageInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.ProjectImageInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:项目形象进度分录's 缩略图property 
     */
    public byte[] getSmallFile()
    {
        return (byte[])get("smallFile");
    }
    public void setSmallFile(byte[] item)
    {
        put("smallFile", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("157C26BA");
    }
}