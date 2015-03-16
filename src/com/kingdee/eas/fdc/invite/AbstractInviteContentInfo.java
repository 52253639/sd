package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteContentInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractInviteContentInfo()
    {
        this("id");
    }
    protected AbstractInviteContentInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:招标文件正文's 正文property 
     */
    public byte[] getContentFile()
    {
        return (byte[])get("contentFile");
    }
    public void setContentFile(byte[] item)
    {
        put("contentFile", item);
    }
    /**
     * Object:招标文件正文's 文件类型property 
     */
    public String getFileType()
    {
        return getString("fileType");
    }
    public void setFileType(String item)
    {
        setString("fileType", item);
    }
    /**
     * Object:招标文件正文's 文件名称property 
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
     * Object:招标文件正文's 所属对象IDproperty 
     */
    public String getParent()
    {
        return getString("parent");
    }
    public void setParent(String item)
    {
        setString("parent", item);
    }
    /**
     * Object:招标文件正文's 版本号property 
     */
    public java.math.BigDecimal getVersion()
    {
        return getBigDecimal("version");
    }
    public void setVersion(java.math.BigDecimal item)
    {
        setBigDecimal("version", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9715088C");
    }
}