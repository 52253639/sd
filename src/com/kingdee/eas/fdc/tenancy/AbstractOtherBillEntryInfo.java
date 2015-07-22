package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOtherBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractOtherBillEntryInfo()
    {
        this("id");
    }
    protected AbstractOtherBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:款项明细's 收取金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object: 款项明细 's 款项 property 
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
     * Object:款项明细's 备注property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: 款项明细 's 其他合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.OtherBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.OtherBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.OtherBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object:款项明细's 单价property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:款项明细's 面积/量property 
     */
    public java.math.BigDecimal getWorkload()
    {
        return getBigDecimal("workload");
    }
    public void setWorkload(java.math.BigDecimal item)
    {
        setBigDecimal("workload", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("220E0964");
    }
}