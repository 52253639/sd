package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierAttachListInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractMarketSupplierAttachListInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierAttachListInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 档案附件清单 's 档案分类 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierFileTypInfo getSupplierFileType()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierFileTypInfo)get("supplierFileType");
    }
    public void setSupplierFileType(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierFileTypInfo item)
    {
        put("supplierFileType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B513C278");
    }
}