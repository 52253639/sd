package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteChangeFormEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteChangeFormEntryInfo()
    {
        this("id");
    }
    protected AbstractInviteChangeFormEntryInfo(String pkField)
    {
        super(pkField);
        put("suppliers", new com.kingdee.eas.fdc.invite.InviteChangeFormSupplierCollection());
    }
    /**
     * Object: 改变采购方式申请单分录 's 招标立项 property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectInfo getInviteProject()
    {
        return (com.kingdee.eas.fdc.invite.InviteProjectInfo)get("inviteProject");
    }
    public void setInviteProject(com.kingdee.eas.fdc.invite.InviteProjectInfo item)
    {
        put("inviteProject", item);
    }
    /**
     * Object: 改变采购方式申请单分录 's 改变采购方式申请单 property 
     */
    public com.kingdee.eas.fdc.invite.InviteChangeFormInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteChangeFormInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteChangeFormInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 改变采购方式申请单分录 's 应采用的采购方式 property 
     */
    public com.kingdee.eas.fdc.invite.InviteFormInfo getShouldInviteForm()
    {
        return (com.kingdee.eas.fdc.invite.InviteFormInfo)get("shouldInviteForm");
    }
    public void setShouldInviteForm(com.kingdee.eas.fdc.invite.InviteFormInfo item)
    {
        put("shouldInviteForm", item);
    }
    /**
     * Object: 改变采购方式申请单分录 's 现在使用的采购方式 property 
     */
    public com.kingdee.eas.fdc.invite.InviteFormInfo getNowInviteFrom()
    {
        return (com.kingdee.eas.fdc.invite.InviteFormInfo)get("nowInviteFrom");
    }
    public void setNowInviteFrom(com.kingdee.eas.fdc.invite.InviteFormInfo item)
    {
        put("nowInviteFrom", item);
    }
    /**
     * Object: 改变采购方式申请单分录 's 拟投标供应商 property 
     */
    public com.kingdee.eas.fdc.invite.InviteChangeFormSupplierCollection getSuppliers()
    {
        return (com.kingdee.eas.fdc.invite.InviteChangeFormSupplierCollection)get("suppliers");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B929A951");
    }
}