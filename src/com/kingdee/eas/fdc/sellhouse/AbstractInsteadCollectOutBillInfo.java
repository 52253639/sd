package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInsteadCollectOutBillInfo extends com.kingdee.eas.fdc.propertymgmt.PPMProjectBillInfo implements Serializable 
{
    public AbstractInsteadCollectOutBillInfo()
    {
        this("id");
    }
    protected AbstractInsteadCollectOutBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillEntryCollection());
    }
    /**
     * Object: 代收费转出 's 分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillEntryCollection)get("entrys");
    }
    /**
     * Object:代收费转出's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:代收费转出's 本次代付金额property 
     */
    public java.math.BigDecimal getMoneyInsteadCur()
    {
        return getBigDecimal("moneyInsteadCur");
    }
    public void setMoneyInsteadCur(java.math.BigDecimal item)
    {
        setBigDecimal("moneyInsteadCur", item);
    }
    /**
     * Object: 代收费转出 's 代付人员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getManInsteadPay()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("manInsteadPay");
    }
    public void setManInsteadPay(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("manInsteadPay", item);
    }
    /**
     * Object: 代收费转出 's 认购单其他应收分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo getPurchaseElsePayListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo)get("purchaseElsePayListEntry");
    }
    public void setPurchaseElsePayListEntry(com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo item)
    {
        put("purchaseElsePayListEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F2A687D2");
    }
}