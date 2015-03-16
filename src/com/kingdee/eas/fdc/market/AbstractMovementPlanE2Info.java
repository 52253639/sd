package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMovementPlanE2Info extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractMovementPlanE2Info()
    {
        this("id");
    }
    protected AbstractMovementPlanE2Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 计划费用 's null property 
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
     * Object: 计划费用 's 费用项目 property 
     */
    public com.kingdee.eas.basedata.scm.common.ExpenseItemInfo getChargeType()
    {
        return (com.kingdee.eas.basedata.scm.common.ExpenseItemInfo)get("chargeType");
    }
    public void setChargeType(com.kingdee.eas.basedata.scm.common.ExpenseItemInfo item)
    {
        put("chargeType", item);
    }
    /**
     * Object:计划费用's 费用类别property 
     */
    public String getChargeParent()
    {
        return getString("chargeParent");
    }
    public void setChargeParent(String item)
    {
        setString("chargeParent", item);
    }
    /**
     * Object:计划费用's 金额property 
     */
    public java.math.BigDecimal getMoney()
    {
        return getBigDecimal("money");
    }
    public void setMoney(java.math.BigDecimal item)
    {
        setBigDecimal("money", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("06163F16");
    }
}