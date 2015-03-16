package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteFormInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractInviteFormInfo()
    {
        this("id");
    }
    protected AbstractInviteFormInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:²É¹º·½Ê½'s property 
     */
    public com.kingdee.eas.fdc.invite.InviteFormEnum getType()
    {
        return com.kingdee.eas.fdc.invite.InviteFormEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.invite.InviteFormEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5E91E991");
    }
}