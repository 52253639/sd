package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteFileMergEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteFileMergEntryInfo()
    {
        this("id");
    }
    protected AbstractInviteFileMergEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 招标文件合成分录 's 标书文件 property 
     */
    public com.kingdee.eas.fdc.invite.InviteFileMergeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteFileMergeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteFileMergeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 招标文件合成分录 's 标书文件 property 
     */
    public com.kingdee.eas.fdc.invite.InviteFileItemInfo getFileItem()
    {
        return (com.kingdee.eas.fdc.invite.InviteFileItemInfo)get("fileItem");
    }
    public void setFileItem(com.kingdee.eas.fdc.invite.InviteFileItemInfo item)
    {
        put("fileItem", item);
    }
    /**
     * Object:招标文件合成分录's 文件类型property 
     */
    public com.kingdee.eas.fdc.invite.InviteFileItemTypeEnum getFileItemType()
    {
        return com.kingdee.eas.fdc.invite.InviteFileItemTypeEnum.getEnum(getString("fileItemType"));
    }
    public void setFileItemType(com.kingdee.eas.fdc.invite.InviteFileItemTypeEnum item)
    {
		if (item != null) {
        setString("fileItemType", item.getValue());
		}
    }
    /**
     * Object:招标文件合成分录's 文件内码property 
     */
    public String getFileID()
    {
        return getString("fileID");
    }
    public void setFileID(String item)
    {
        setString("fileID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("33016A5C");
    }
}