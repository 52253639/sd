package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuotingPriceSetInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractQuotingPriceSetInfo()
    {
        this("id");
    }
    protected AbstractQuotingPriceSetInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:报价集合's 值property 
     */
    public byte[] getValue()
    {
        return (byte[])get("value");
    }
    public void setValue(byte[] item)
    {
        put("value", item);
    }
    /**
     * Object: 报价集合 's 清单 property 
     */
    public com.kingdee.eas.fdc.invite.NewListingInfo getInviteListing()
    {
        return (com.kingdee.eas.fdc.invite.NewListingInfo)get("inviteListing");
    }
    public void setInviteListing(com.kingdee.eas.fdc.invite.NewListingInfo item)
    {
        put("inviteListing", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("27BD3A0E");
    }
}