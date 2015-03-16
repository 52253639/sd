package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierPersonInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractMarketSupplierPersonInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierPersonInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 职员构成 's 档案分类 property 
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
        return new BOSObjectType("464C37CA");
    }
}