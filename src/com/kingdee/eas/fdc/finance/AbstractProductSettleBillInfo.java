package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProductSettleBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProductSettleBillInfo()
    {
        this("id");
    }
    protected AbstractProductSettleBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 产品结算单 's 工程项目产品分录 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo getCurProjProductEntries()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo)get("curProjProductEntries");
    }
    public void setCurProjProductEntries(com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo item)
    {
        put("curProjProductEntries", item);
    }
    /**
     * Object:产品结算单's 竣工面积property 
     */
    public java.math.BigDecimal getCompArea()
    {
        return getBigDecimal("compArea");
    }
    public void setCompArea(java.math.BigDecimal item)
    {
        setBigDecimal("compArea", item);
    }
    /**
     * Object:产品结算单's 竣工日期property 
     */
    public java.util.Date getCompDate()
    {
        return getDate("compDate");
    }
    public void setCompDate(java.util.Date item)
    {
        setDate("compDate", item);
    }
    /**
     * Object:产品结算单's 总成本property 
     */
    public java.math.BigDecimal getTotalCost()
    {
        return getBigDecimal("totalCost");
    }
    public void setTotalCost(java.math.BigDecimal item)
    {
        setBigDecimal("totalCost", item);
    }
    /**
     * Object:产品结算单's 是否竣工结算property 
     */
    public boolean isIsCompSettle()
    {
        return getBoolean("isCompSettle");
    }
    public void setIsCompSettle(boolean item)
    {
        setBoolean("isCompSettle", item);
    }
    /**
     * Object:产品结算单's 是否已生成凭证property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object: 产品结算单 's 一体化科目 property 
     */
    public com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo getBeforeAccount()
    {
        return (com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo)get("beforeAccount");
    }
    public void setBeforeAccount(com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo item)
    {
        put("beforeAccount", item);
    }
    /**
     * Object: 产品结算单 's 凭证 property 
     */
    public com.kingdee.eas.fi.gl.VoucherInfo getVoucher()
    {
        return (com.kingdee.eas.fi.gl.VoucherInfo)get("voucher");
    }
    public void setVoucher(com.kingdee.eas.fi.gl.VoucherInfo item)
    {
        put("voucher", item);
    }
    /**
     * Object:产品结算单's 是否已经红冲property 
     */
    public boolean isIsRedVouchered()
    {
        return getBoolean("isRedVouchered");
    }
    public void setIsRedVouchered(boolean item)
    {
        setBoolean("isRedVouchered", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5CA3F112");
    }
}