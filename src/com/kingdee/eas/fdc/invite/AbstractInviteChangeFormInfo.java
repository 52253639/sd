package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteChangeFormInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInviteChangeFormInfo()
    {
        this("id");
    }
    protected AbstractInviteChangeFormInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InviteChangeFormEntryCollection());
    }
    /**
     * Object:改变采购方式申请单's 改变原因property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    /**
     * Object:改变采购方式申请单's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object: 改变采购方式申请单 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.InviteChangeFormEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InviteChangeFormEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("09D18BA1");
    }
}