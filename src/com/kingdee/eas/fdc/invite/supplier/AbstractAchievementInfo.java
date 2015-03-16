package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAchievementInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractAchievementInfo()
    {
        this("id");
    }
    protected AbstractAchievementInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:业绩's 工程名称property 
     */
    public String getProjectName()
    {
        return getString("projectName");
    }
    public void setProjectName(String item)
    {
        setString("projectName", item);
    }
    /**
     * Object:业绩's 客户名称property 
     */
    public String getClientName()
    {
        return getString("clientName");
    }
    public void setClientName(String item)
    {
        setString("clientName", item);
    }
    /**
     * Object:业绩's 客户地址property 
     */
    public String getClientAddress()
    {
        return getString("clientAddress");
    }
    public void setClientAddress(String item)
    {
        setString("clientAddress", item);
    }
    /**
     * Object:业绩's 客户联系人property 
     */
    public String getClientLinkPerson()
    {
        return getString("clientLinkPerson");
    }
    public void setClientLinkPerson(String item)
    {
        setString("clientLinkPerson", item);
    }
    /**
     * Object:业绩's 电话property 
     */
    public String getPhone()
    {
        return getString("phone");
    }
    public void setPhone(String item)
    {
        setString("phone", item);
    }
    /**
     * Object:业绩's 合同金额property 
     */
    public java.math.BigDecimal getContractPay()
    {
        return getBigDecimal("contractPay");
    }
    public void setContractPay(java.math.BigDecimal item)
    {
        setBigDecimal("contractPay", item);
    }
    /**
     * Object:业绩's 开始日期property 
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
     * Object:业绩's 截止日期property 
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
     * Object:业绩's 投入人数property 
     */
    public java.math.BigDecimal getPeopleCount()
    {
        return getBigDecimal("peopleCount");
    }
    public void setPeopleCount(java.math.BigDecimal item)
    {
        setBigDecimal("peopleCount", item);
    }
    /**
     * Object:业绩's 备注property 
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
     * Object:业绩's 是否有附件property 
     */
    public boolean isIsHaveAttach()
    {
        return getBoolean("isHaveAttach");
    }
    public void setIsHaveAttach(boolean item)
    {
        setBoolean("isHaveAttach", item);
    }
    /**
     * Object: 业绩 's 所属单据体 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2AD45B71");
    }
}