package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDefaultAmountMangerInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractDefaultAmountMangerInfo()
    {
        this("id");
    }
    protected AbstractDefaultAmountMangerInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryCollection());
    }
    /**
     * Object:违约金管理's 客户property 
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
     * Object:违约金管理's 联系电话property 
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
     * Object:违约金管理's 置业顾问property 
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
     * Object:违约金管理's 合同总价property 
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
     * Object:违约金管理's 欠款金额property 
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
     * Object:违约金管理's 参考违约金property 
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
     * Object:违约金管理's 减免违约金property 
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
     * Object:违约金管理's 结转违约金property 
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
     * Object:违约金管理's 业务类型property 
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
     * Object:违约金管理's 违约金计算日期property 
     */
    public java.util.Date getDefCalDate()
    {
        return getDate("defCalDate");
    }
    public void setDefCalDate(java.util.Date item)
    {
        setDate("defCalDate", item);
    }
    /**
     * Object:违约金管理's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object: 违约金管理 's 房间 property 
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
     * Object: 违约金管理 's 付款方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getPayment()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("payment");
    }
    public void setPayment(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("payment", item);
    }
    /**
     * Object: 违约金管理 's 单据头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryCollection)get("entry");
    }
    /**
     * Object: 违约金管理 's 交易主线Id property 
     */
    public com.kingdee.eas.fdc.sellhouse.TransactionInfo getTransaction()
    {
        return (com.kingdee.eas.fdc.sellhouse.TransactionInfo)get("transaction");
    }
    public void setTransaction(com.kingdee.eas.fdc.sellhouse.TransactionInfo item)
    {
        put("transaction", item);
    }
    /**
     * Object: 违约金管理 's 项目名称 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:违约金管理's 最新单据IDproperty 
     */
    public String getBillId()
    {
        return getString("billId");
    }
    public void setBillId(String item)
    {
        setString("billId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("29845C58");
    }
}