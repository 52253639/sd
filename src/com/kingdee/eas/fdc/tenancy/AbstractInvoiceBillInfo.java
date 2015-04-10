package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvoiceBillInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractInvoiceBillInfo()
    {
        this("id");
    }
    protected AbstractInvoiceBillInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.tenancy.InvoiceBillEntryCollection());
    }
    /**
     * Object: Ʊ�ݹ��� 's ���޺�ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyBill");
    }
    public void setTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyBill", item);
    }
    /**
     * Object: Ʊ�ݹ��� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.InvoiceBillEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.InvoiceBillEntryCollection)get("entry");
    }
    /**
     * Object:Ʊ�ݹ���'s Ʊ������property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:Ʊ�ݹ���'s �Ƿ�����ƾ֤property 
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
     * Object:Ʊ�ݹ���'s Ʊ�ݽ��property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F345578B");
    }
}