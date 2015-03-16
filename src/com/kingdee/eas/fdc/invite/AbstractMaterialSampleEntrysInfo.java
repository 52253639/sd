package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMaterialSampleEntrysInfo extends com.kingdee.eas.fdc.invite.BaseInviteEntryInfo implements Serializable 
{
    public AbstractMaterialSampleEntrysInfo()
    {
        this("id");
    }
    protected AbstractMaterialSampleEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 材料审批及确认分录 's null property 
     */
    public com.kingdee.eas.fdc.invite.MaterialSampleInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.MaterialSampleInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.MaterialSampleInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CB97C076");
    }
}