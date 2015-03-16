package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRevFDCCustomerEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRevFDCCustomerEntryInfo()
    {
        this("id");
    }
    protected AbstractRevFDCCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 交费客户分录 's 收款单 property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 交费客户分录 's 房地产客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getFdcCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("fdcCustomer");
    }
    public void setFdcCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("fdcCustomer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0F394DB6");
    }
}