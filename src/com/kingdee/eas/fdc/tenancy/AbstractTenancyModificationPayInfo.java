package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyModificationPayInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTenancyModificationPayInfo()
    {
        this("id");
    }
    protected AbstractTenancyModificationPayInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:租赁变更付款计划's 是否新付款计划property 
     */
    public boolean isIsNewPayList()
    {
        return getBoolean("isNewPayList");
    }
    public void setIsNewPayList(boolean item)
    {
        setBoolean("isNewPayList", item);
    }
    /**
     * Object: 租赁变更付款计划 's 房间 property 
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
     * Object: 租赁变更付款计划 's 租赁变更单 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyModificationInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyModificationInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.TenancyModificationInfo item)
    {
        put("head", item);
    }
    /**
     * Object:租赁变更付款计划's 租期序列property 
     */
    public int getLeaseSeq()
    {
        return getInt("leaseSeq");
    }
    public void setLeaseSeq(int item)
    {
        setInt("leaseSeq", item);
    }
    /**
     * Object:租赁变更付款计划's 起始日期property 
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
     * Object:租赁变更付款计划's 结束日期property 
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
     * Object:租赁变更付款计划's 应付日期property 
     */
    public java.util.Date getActPayDate()
    {
        return getDate("actPayDate");
    }
    public void setActPayDate(java.util.Date item)
    {
        setDate("actPayDate", item);
    }
    /**
     * Object:租赁变更付款计划's 应付金额property 
     */
    public java.math.BigDecimal getActPayAmount()
    {
        return getBigDecimal("actPayAmount");
    }
    public void setActPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actPayAmount", item);
    }
    /**
     * Object: 租赁变更付款计划 's 款项类型 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object:租赁变更付款计划's 实付金额property 
     */
    public java.math.BigDecimal getActRevAmount()
    {
        return getBigDecimal("actRevAmount");
    }
    public void setActRevAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actRevAmount", item);
    }
    /**
     * Object:租赁变更付款计划's 实付日期property 
     */
    public java.util.Date getActRevDate()
    {
        return getDate("actRevDate");
    }
    public void setActRevDate(java.util.Date item)
    {
        setDate("actRevDate", item);
    }
    /**
     * Object:租赁变更付款计划's 树级别property 
     */
    public int getTreeLevel()
    {
        return getInt("treeLevel");
    }
    public void setTreeLevel(int item)
    {
        setInt("treeLevel", item);
    }
    /**
     * Object:租赁变更付款计划's 款项类型property 
     */
    public String getMoneyDefineName()
    {
        return getString("moneyDefineName");
    }
    public void setMoneyDefineName(String item)
    {
        setString("moneyDefineName", item);
    }
    /**
     * Object:租赁变更付款计划's 行property 
     */
    public int getRow()
    {
        return getInt("row");
    }
    public void setRow(int item)
    {
        setInt("row", item);
    }
    /**
     * Object:租赁变更付款计划's 列property 
     */
    public int getCell()
    {
        return getInt("cell");
    }
    public void setCell(int item)
    {
        setInt("cell", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("22C9FB35");
    }
}