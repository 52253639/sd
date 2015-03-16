package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSplAuditIndexInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractFDCSplAuditIndexInfo()
    {
        this("id");
    }
    protected AbstractFDCSplAuditIndexInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评审指标 's 评审维度 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupInfo getIndexGroup()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupInfo)get("indexGroup");
    }
    public void setIndexGroup(com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexGroupInfo item)
    {
        put("indexGroup", item);
    }
    /**
     * Object:评审指标's 3分标准property 
     */
    public String getFullMarkStandard()
    {
        return getString("fullMarkStandard");
    }
    public void setFullMarkStandard(String item)
    {
        setString("fullMarkStandard", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("44D17A5F");
    }
}