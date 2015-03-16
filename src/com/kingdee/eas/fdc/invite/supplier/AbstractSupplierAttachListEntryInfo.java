package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierAttachListEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupplierAttachListEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierAttachListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:档案附件清单分录's 类别编码property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:档案附件清单分录's 类别名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object: 档案附件清单分录 's 供应商 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("head", item);
    }
    /**
     * Object:档案附件清单分录's 描述property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D3CBDD0B");
    }
}