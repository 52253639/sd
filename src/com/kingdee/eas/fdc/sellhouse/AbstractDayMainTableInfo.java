package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDayMainTableInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDayMainTableInfo()
    {
        this("id");
    }
    protected AbstractDayMainTableInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 月结主表 's 销售月结 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo getSaleBalance()
    {
        return (com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo)get("saleBalance");
    }
    public void setSaleBalance(com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo item)
    {
        put("saleBalance", item);
    }
    /**
     * Object:月结主表's 日期property 
     */
    public int getDayNum()
    {
        return getInt("dayNum");
    }
    public void setDayNum(int item)
    {
        setInt("dayNum", item);
    }
    /**
     * Object: 月结主表 's 项目 property 
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
     * Object:月结主表's 预订合同总额property 
     */
    public java.math.BigDecimal getPreAmount()
    {
        return getBigDecimal("preAmount");
    }
    public void setPreAmount(java.math.BigDecimal item)
    {
        setBigDecimal("preAmount", item);
    }
    /**
     * Object:月结主表's 认购合同总额property 
     */
    public java.math.BigDecimal getPurAmount()
    {
        return getBigDecimal("purAmount");
    }
    public void setPurAmount(java.math.BigDecimal item)
    {
        setBigDecimal("purAmount", item);
    }
    /**
     * Object:月结主表's 签约合同总额property 
     */
    public java.math.BigDecimal getSignAmount()
    {
        return getBigDecimal("signAmount");
    }
    public void setSignAmount(java.math.BigDecimal item)
    {
        setBigDecimal("signAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("82E5D3B4");
    }
}