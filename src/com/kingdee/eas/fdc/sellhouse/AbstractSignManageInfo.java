package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSignManageInfo extends com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo implements Serializable 
{
    public AbstractSignManageInfo()
    {
        this("id");
    }
    protected AbstractSignManageInfo(String pkField)
    {
        super(pkField);
        put("signPayListEntry", new com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection());
        put("signCustomerEntry", new com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection());
        put("signAgioEntry", new com.kingdee.eas.fdc.sellhouse.SignAgioEntryCollection());
        put("signSaleManEntry", new com.kingdee.eas.fdc.sellhouse.SignSaleManEntryCollection());
        put("signRoomAttachmentEntry", new com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryCollection());
    }
    /**
     * Object:签约管理's 销售总价property 
     */
    public java.math.BigDecimal getSellAmount()
    {
        return getBigDecimal("sellAmount");
    }
    public void setSellAmount(java.math.BigDecimal item)
    {
        setBigDecimal("sellAmount", item);
    }
    /**
     * Object:签约管理's 面积补差property 
     */
    public java.math.BigDecimal getAreaCompensate()
    {
        return getBigDecimal("areaCompensate");
    }
    public void setAreaCompensate(java.math.BigDecimal item)
    {
        setBigDecimal("areaCompensate", item);
    }
    /**
     * Object:签约管理's 预估补差property 
     */
    public java.math.BigDecimal getPlanningCompensate()
    {
        return getBigDecimal("planningCompensate");
    }
    public void setPlanningCompensate(java.math.BigDecimal item)
    {
        setBigDecimal("planningCompensate", item);
    }
    /**
     * Object:签约管理's 现售补差property 
     */
    public java.math.BigDecimal getCashSalesCompensate()
    {
        return getBigDecimal("cashSalesCompensate");
    }
    public void setCashSalesCompensate(java.math.BigDecimal item)
    {
        setBigDecimal("cashSalesCompensate", item);
    }
    /**
     * Object:签约管理's 预估面积property 
     */
    public java.math.BigDecimal getPlanningArea()
    {
        return getBigDecimal("planningArea");
    }
    public void setPlanningArea(java.math.BigDecimal item)
    {
        setBigDecimal("planningArea", item);
    }
    /**
     * Object:签约管理's 预售面积property 
     */
    public java.math.BigDecimal getPreArea()
    {
        return getBigDecimal("preArea");
    }
    public void setPreArea(java.math.BigDecimal item)
    {
        setBigDecimal("preArea", item);
    }
    /**
     * Object:签约管理's 实测面积property 
     */
    public java.math.BigDecimal getActualArea()
    {
        return getBigDecimal("actualArea");
    }
    public void setActualArea(java.math.BigDecimal item)
    {
        setBigDecimal("actualArea", item);
    }
    /**
     * Object: 签约管理 's 付款明细分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection getSignPayListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection)get("signPayListEntry");
    }
    /**
     * Object: 签约管理 's 附属房产分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryCollection getSignRoomAttachmentEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryCollection)get("signRoomAttachmentEntry");
    }
    /**
     * Object: 签约管理 's 折扣分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignAgioEntryCollection getSignAgioEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignAgioEntryCollection)get("signAgioEntry");
    }
    /**
     * Object: 签约管理 's 客户信息分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection getSignCustomerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection)get("signCustomerEntry");
    }
    /**
     * Object:签约管理's 是否网签property 
     */
    public boolean isIsOnRecord()
    {
        return getBoolean("isOnRecord");
    }
    public void setIsOnRecord(boolean item)
    {
        setBoolean("isOnRecord", item);
    }
    /**
     * Object:签约管理's 备案日期property 
     */
    public java.util.Date getOnRecordDate()
    {
        return getDate("onRecordDate");
    }
    public void setOnRecordDate(java.util.Date item)
    {
        setDate("onRecordDate", item);
    }
    /**
     * Object:签约管理's 约定入伙日期property 
     */
    public java.util.Date getJoinInDate()
    {
        return getDate("joinInDate");
    }
    public void setJoinInDate(java.util.Date item)
    {
        setDate("joinInDate", item);
    }
    /**
     * Object: 签约管理 's 置业顾问分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignSaleManEntryCollection getSignSaleManEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignSaleManEntryCollection)get("signSaleManEntry");
    }
    /**
     * Object:签约管理's 置业顾问property 
     */
    public String getSaleManNames()
    {
        return getString("saleManNames");
    }
    public void setSaleManNames(String item)
    {
        setString("saleManNames", item);
    }
    /**
     * Object: 签约管理 's 按揭银行 property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getLoanBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("LoanBank");
    }
    public void setLoanBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("LoanBank", item);
    }
    /**
     * Object: 签约管理 's 公积金银行 property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getAcfBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("AcfBank");
    }
    public void setAcfBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("AcfBank", item);
    }
    /**
     * Object: 签约管理 's 特殊折扣 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo getSpecialAgio()
    {
        return (com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo)get("specialAgio");
    }
    public void setSpecialAgio(com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo item)
    {
        put("specialAgio", item);
    }
    /**
     * Object:签约管理's 是否工抵房property 
     */
    public boolean isIsWorkRoom()
    {
        return getBoolean("isWorkRoom");
    }
    public void setIsWorkRoom(boolean item)
    {
        setBoolean("isWorkRoom", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D840369D");
    }
}