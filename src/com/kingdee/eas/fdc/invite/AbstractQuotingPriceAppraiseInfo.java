package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuotingPriceAppraiseInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractQuotingPriceAppraiseInfo()
    {
        this("id");
    }
    protected AbstractQuotingPriceAppraiseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:报价评审's 招标清单property 
     */
    public com.kingdee.bos.util.BOSUuid getInviteListing()
    {
        return getBOSUuid("inviteListing");
    }
    public void setInviteListing(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("inviteListing", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2F0B083F");
    }
}