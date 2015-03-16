package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDefaultAmountCreatEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractDefaultAmountCreatEntryInfo()
    {
        this("id");
    }
    protected AbstractDefaultAmountCreatEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 生成违约金分录 's 房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object: 生成违约金分录 's 单据头ID property 
     */
    public com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatInfo item)
    {
        put("head", item);
    }
    /**
     * Object:生成违约金分录's 客户property 
     */
    public String getCustomerNames()
    {
        return getString("customerNames");
    }
    public void setCustomerNames(String item)
    {
        setString("customerNames", item);
    }
    /**
     * Object:生成违约金分录's 联系电话property 
     */
    public String getTelephone()
    {
        return getString("telephone");
    }
    public void setTelephone(String item)
    {
        setString("telephone", item);
    }
    /**
     * Object:生成违约金分录's 业务日期property 
     */
    public java.util.Date getBizDate()
    {
        return getDate("bizDate");
    }
    public void setBizDate(java.util.Date item)
    {
        setDate("bizDate", item);
    }
    /**
     * Object:生成违约金分录's 业务编号property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:生成违约金分录's 合同总价property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("contractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmount", item);
    }
    /**
     * Object:生成违约金分录's 欠款金额property 
     */
    public java.math.BigDecimal getArgAmount()
    {
        return getBigDecimal("argAmount");
    }
    public void setArgAmount(java.math.BigDecimal item)
    {
        setBigDecimal("argAmount", item);
    }
    /**
     * Object:生成违约金分录's 业务类型property 
     */
    public String getBusType()
    {
        return getString("busType");
    }
    public void setBusType(String item)
    {
        setString("busType", item);
    }
    /**
     * Object:生成违约金分录's 置业顾问property 
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
     * Object:生成违约金分录's 结转违约金property 
     */
    public java.math.BigDecimal getCarryAmount()
    {
        return getBigDecimal("carryAmount");
    }
    public void setCarryAmount(java.math.BigDecimal item)
    {
        setBigDecimal("carryAmount", item);
    }
    /**
     * Object:生成违约金分录's 参考违约金property 
     */
    public java.math.BigDecimal getRefDeAmount()
    {
        return getBigDecimal("refDeAmount");
    }
    public void setRefDeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("refDeAmount", item);
    }
    /**
     * Object:生成违约金分录's 减免违约金property 
     */
    public java.math.BigDecimal getSubDeAmount()
    {
        return getBigDecimal("subDeAmount");
    }
    public void setSubDeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("subDeAmount", item);
    }
    /**
     * Object:生成违约金分录's 备注property 
     */
    public String getRemak()
    {
        return getString("remak");
    }
    public void setRemak(String item)
    {
        setString("remak", item);
    }
    /**
     * Object: 生成违约金分录 's 违约金管理分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryInfo getDefaultAmountMangerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryInfo)get("defaultAmountMangerEntry");
    }
    public void setDefaultAmountMangerEntry(com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryInfo item)
    {
        put("defaultAmountMangerEntry", item);
    }
    /**
     * Object: 生成违约金分录 's 交易付款分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo getTranBusinessOverView()
    {
        return (com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo)get("tranBusinessOverView");
    }
    public void setTranBusinessOverView(com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo item)
    {
        put("tranBusinessOverView", item);
    }
    /**
     * Object: 生成违约金分录 's 签约管理分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo getSignPayListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo)get("signPayListEntry");
    }
    public void setSignPayListEntry(com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo item)
    {
        put("signPayListEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5D7DBF07");
    }
}