package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSplAuditIndexGroupInfo extends com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo implements Serializable 
{
    public AbstractFDCSplAuditIndexGroupInfo()
    {
        this("id");
    }
    protected AbstractFDCSplAuditIndexGroupInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ∆¿…ÛŒ¨∂» 's …œº∂±‡¬Î property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EB452F60");
    }
}