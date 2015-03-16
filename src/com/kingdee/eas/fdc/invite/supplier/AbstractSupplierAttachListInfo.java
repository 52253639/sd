package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierAttachListInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractSupplierAttachListInfo()
    {
        this("id");
    }
    protected AbstractSupplierAttachListInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:供应商档案附件清单's nullproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    /**
     * Object: 供应商档案附件清单 's 档案分类 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo getSupplierFileType()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo)get("supplierFileType");
    }
    public void setSupplierFileType(com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo item)
    {
        put("supplierFileType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("56B86BA7");
    }
}