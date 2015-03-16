package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFeesWarrantInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFeesWarrantInfo()
    {
        this("id");
    }
    protected AbstractFeesWarrantInfo(String pkField)
    {
        super(pkField);
        put("feesWarrantEntry", new com.kingdee.eas.fdc.tenancy.FeesWarrantEntrysCollection());
    }
    /**
     * Object: 应收费用凭证 's 租售项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: 应收费用凭证 's 应收费用凭证分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.FeesWarrantEntrysCollection getFeesWarrantEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.FeesWarrantEntrysCollection)get("feesWarrantEntry");
    }
    /**
     * Object:应收费用凭证's 是否已生成凭证property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AA2EA367");
    }
}