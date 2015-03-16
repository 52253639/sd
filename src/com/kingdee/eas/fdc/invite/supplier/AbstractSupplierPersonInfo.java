package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierPersonInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractSupplierPersonInfo()
    {
        this("id");
    }
    protected AbstractSupplierPersonInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 职员构成 's 档案分类 property 
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
        return new BOSObjectType("B75AD079");
    }
}