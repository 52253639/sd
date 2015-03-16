package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvitePurchaseModeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractInvitePurchaseModeInfo()
    {
        this("id");
    }
    protected AbstractInvitePurchaseModeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:²É¹ºÄ£Ê½'s property 
     */
    public com.kingdee.eas.fdc.invite.InvitePurchaseModeEnum getType()
    {
        return com.kingdee.eas.fdc.invite.InvitePurchaseModeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.invite.InvitePurchaseModeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("89A40A11");
    }
}