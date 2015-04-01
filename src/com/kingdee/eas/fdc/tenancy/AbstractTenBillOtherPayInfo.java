package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenBillOtherPayInfo extends com.kingdee.eas.fdc.basecrm.RevListInfo implements Serializable 
{
    public AbstractTenBillOtherPayInfo()
    {
        this("id");
    }
    protected AbstractTenBillOtherPayInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 租赁合同其他应付明细分录 's 租赁合同头 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object:租赁合同其他应付明细分录's 备注property 
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
     * Object: 租赁合同其他应付明细分录 's 币别 property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:租赁合同其他应付明细分录's 开始日期property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:租赁合同其他应付明细分录's 结束日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object: 租赁合同其他应付明细分录 's 其它应收单 property 
     */
    public com.kingdee.eas.fdc.tenancy.RestReceivableInfo getRestReceivable()
    {
        return (com.kingdee.eas.fdc.tenancy.RestReceivableInfo)get("restReceivable");
    }
    public void setRestReceivable(com.kingdee.eas.fdc.tenancy.RestReceivableInfo item)
    {
        put("restReceivable", item);
    }
    /**
     * Object:租赁合同其他应付明细分录's 是否托收property 
     */
    public com.kingdee.eas.fdc.propertymgmt.DeductFlagEnum getCollectionFlag()
    {
        return com.kingdee.eas.fdc.propertymgmt.DeductFlagEnum.getEnum(getString("collectionFlag"));
    }
    public void setCollectionFlag(com.kingdee.eas.fdc.propertymgmt.DeductFlagEnum item)
    {
		if (item != null) {
        setString("collectionFlag", item.getValue());
		}
    }
    /**
     * Object: 租赁合同其他应付明细分录 's 其他合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.OtherBillInfo getOtherBill()
    {
        return (com.kingdee.eas.fdc.tenancy.OtherBillInfo)get("otherBill");
    }
    public void setOtherBill(com.kingdee.eas.fdc.tenancy.OtherBillInfo item)
    {
        put("otherBill", item);
    }
    /**
     * Object:租赁合同其他应付明细分录's 租期序号property 
     */
    public int getLeaseSeq()
    {
        return getInt("leaseSeq");
    }
    public void setLeaseSeq(int item)
    {
        setInt("leaseSeq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E17EA893");
    }
}