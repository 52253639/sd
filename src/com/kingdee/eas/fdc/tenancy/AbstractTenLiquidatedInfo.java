package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenLiquidatedInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTenLiquidatedInfo()
    {
        this("id");
    }
    protected AbstractTenLiquidatedInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 租凭合同违约金计算方案设置 's 款项名称 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object: 租凭合同违约金计算方案设置 's 违约金计算方案 property 
     */
    public com.kingdee.eas.fdc.tenancy.LiquidatedInfo getLiquidated()
    {
        return (com.kingdee.eas.fdc.tenancy.LiquidatedInfo)get("liquidated");
    }
    public void setLiquidated(com.kingdee.eas.fdc.tenancy.LiquidatedInfo item)
    {
        put("liquidated", item);
    }
    /**
     * Object: 租凭合同违约金计算方案设置 's 租赁合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyBill");
    }
    public void setTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6375E842");
    }
}