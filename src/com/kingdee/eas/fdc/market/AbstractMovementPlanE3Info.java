package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMovementPlanE3Info extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractMovementPlanE3Info()
    {
        this("id");
    }
    protected AbstractMovementPlanE3Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 计划参与人员 's null property 
     */
    public com.kingdee.eas.fdc.market.MovementPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MovementPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MovementPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 计划参与人员 's 客户id property 
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
        return new BOSObjectType("06163F17");
    }
}