package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWorkLoadPrjBillEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractWorkLoadPrjBillEntryInfo()
    {
        this("id");
    }
    protected AbstractWorkLoadPrjBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 工程量填报单分录 's 工程量确认单ID property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 工程量填报单分录 's 工程量填报单ID property 
     */
    public com.kingdee.eas.fdc.pm.ProjectFillBillInfo getPrjFillBill()
    {
        return (com.kingdee.eas.fdc.pm.ProjectFillBillInfo)get("prjFillBill");
    }
    public void setPrjFillBill(com.kingdee.eas.fdc.pm.ProjectFillBillInfo item)
    {
        put("prjFillBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E8D66BA9");
    }
}