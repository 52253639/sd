package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteBaseFileInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInviteBaseFileInfo()
    {
        this("id");
    }
    protected AbstractInviteBaseFileInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 标准招标文件 's 采购类别 property 
     */
    public com.kingdee.eas.fdc.invite.InviteTypeInfo getInviteType()
    {
        return (com.kingdee.eas.fdc.invite.InviteTypeInfo)get("inviteType");
    }
    public void setInviteType(com.kingdee.eas.fdc.invite.InviteTypeInfo item)
    {
        put("inviteType", item);
    }
    /**
     * Object:标准招标文件's 摘要property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:标准招标文件's 版本号property 
     */
    public float getVerNumber()
    {
        return getFloat("verNumber");
    }
    public void setVerNumber(float item)
    {
        setFloat("verNumber", item);
    }
    /**
     * Object:标准招标文件's 是否最终版本property 
     */
    public boolean isIsLastVer()
    {
        return getBoolean("isLastVer");
    }
    public void setIsLastVer(boolean item)
    {
        setBoolean("isLastVer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("06BA4E3A");
    }
}