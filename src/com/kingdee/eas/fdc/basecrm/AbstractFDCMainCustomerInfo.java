package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCMainCustomerInfo extends com.kingdee.eas.fdc.basecrm.FDCBaseCustomerInfo implements Serializable 
{
    public AbstractFDCMainCustomerInfo()
    {
        this("id");
    }
    protected AbstractFDCMainCustomerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 房地产主客户 's 系统客户 property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getSysCustomer()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("sysCustomer");
    }
    public void setSysCustomer(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("sysCustomer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BD66CDE0");
    }
}