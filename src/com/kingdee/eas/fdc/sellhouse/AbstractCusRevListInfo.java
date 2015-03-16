package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCusRevListInfo extends com.kingdee.eas.fdc.basecrm.RevListInfo implements Serializable 
{
    public AbstractCusRevListInfo()
    {
        this("id");
    }
    protected AbstractCusRevListInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 客户收款明细 's 房地产客户 property 
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
        return new BOSObjectType("C64ADF7B");
    }
}