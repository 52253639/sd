package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteFileItemEntryInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractInviteFileItemEntryInfo()
    {
        this("id");
    }
    protected AbstractInviteFileItemEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:标书文件模块分录's 正文property 
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
     * Object: 标书文件模块分录 's 上级目录 property 
     */
    public com.kingdee.eas.fdc.invite.InviteFileItemEntryInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteFileItemEntryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteFileItemEntryInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 标书文件模块分录 's 标书文件 property 
     */
    public com.kingdee.eas.fdc.invite.InviteFileItemInfo getFileItem()
    {
        return (com.kingdee.eas.fdc.invite.InviteFileItemInfo)get("fileItem");
    }
    public void setFileItem(com.kingdee.eas.fdc.invite.InviteFileItemInfo item)
    {
        put("fileItem", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("52AD60D6");
    }
}