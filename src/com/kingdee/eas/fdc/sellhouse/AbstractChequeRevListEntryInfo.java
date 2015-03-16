package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChequeRevListEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractChequeRevListEntryInfo()
    {
        this("id");
    }
    protected AbstractChequeRevListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 票据收款单明细 's 票据明细头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo getChequeDetail()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo)get("chequeDetail");
    }
    public void setChequeDetail(com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo item)
    {
        put("chequeDetail", item);
    }
    /**
     * Object: 票据收款单明细 's 收款单明细 property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo getRevBillEntry()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo)get("revBillEntry");
    }
    public void setRevBillEntry(com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo item)
    {
        put("revBillEntry", item);
    }
    /**
     * Object:票据收款单明细's 开票金额property 
     */
    public java.math.BigDecimal getChequeAmount()
    {
        return getBigDecimal("chequeAmount");
    }
    public void setChequeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("chequeAmount", item);
    }
    /**
     * Object:票据收款单明细's 是否勾选property 
     */
    public boolean isIsSelect()
    {
        return getBoolean("isSelect");
    }
    public void setIsSelect(boolean item)
    {
        setBoolean("isSelect", item);
    }
    /**
     * Object:票据收款单明细's 收据号property 
     */
    public String getReceipt()
    {
        return getString("receipt");
    }
    public void setReceipt(String item)
    {
        setString("receipt", item);
    }
    /**
     * Object:票据收款单明细's 发票号property 
     */
    public String getInvoiceNum()
    {
        return getString("invoiceNum");
    }
    public void setInvoiceNum(String item)
    {
        setString("invoiceNum", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("257A2BED");
    }
}