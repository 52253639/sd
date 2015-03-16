package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTemplateFileCategoryInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractTemplateFileCategoryInfo()
    {
        this("id");
    }
    protected AbstractTemplateFileCategoryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:模板文件分录's 内容property 
     */
    public String getContext()
    {
        return getString("context");
    }
    public void setContext(String item)
    {
        setString("context", item);
    }
    /**
     * Object: 模板文件分录 's 上级目录 property 
     */
    public com.kingdee.eas.fdc.invite.TemplateFileCategoryInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.TemplateFileCategoryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.TemplateFileCategoryInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 模板文件分录 's 模板文件 property 
     */
    public com.kingdee.eas.fdc.invite.TemplateFileInfo getTempFile()
    {
        return (com.kingdee.eas.fdc.invite.TemplateFileInfo)get("tempFile");
    }
    public void setTempFile(com.kingdee.eas.fdc.invite.TemplateFileInfo item)
    {
        put("tempFile", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5D839D58");
    }
}