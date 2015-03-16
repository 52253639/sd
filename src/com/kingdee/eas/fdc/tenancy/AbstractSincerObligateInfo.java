package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSincerObligateInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractSincerObligateInfo()
    {
        this("id");
    }
    protected AbstractSincerObligateInfo(String pkField)
    {
        super(pkField);
        put("sinAttachList", new com.kingdee.eas.fdc.tenancy.SinObligateAttachEntryCollection());
        put("sinCustomerList", new com.kingdee.eas.fdc.tenancy.SinCustomerEntrysCollection());
        put("sinRoomList", new com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryCollection());
        put("payListEntrys", new com.kingdee.eas.fdc.tenancy.SincerPaylistEntrysCollection());
    }
    /**
     * Object: 诚意预留 's 客户分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.SinCustomerEntrysCollection getSinCustomerList()
    {
        return (com.kingdee.eas.fdc.tenancy.SinCustomerEntrysCollection)get("sinCustomerList");
    }
    /**
     * Object: 诚意预留 's 房间分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryCollection getSinRoomList()
    {
        return (com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryCollection)get("sinRoomList");
    }
    /**
     * Object: 诚意预留 's 配套资源分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.SinObligateAttachEntryCollection getSinAttachList()
    {
        return (com.kingdee.eas.fdc.tenancy.SinObligateAttachEntryCollection)get("sinAttachList");
    }
    /**
     * Object:诚意预留's 预留天数property 
     */
    public int getObligateDateCount()
    {
        return getInt("obligateDateCount");
    }
    public void setObligateDateCount(int item)
    {
        setInt("obligateDateCount", item);
    }
    /**
     * Object:诚意预留's 约定计租期间长度property 
     */
    public int getTenancyTermLength()
    {
        return getInt("tenancyTermLength");
    }
    public void setTenancyTermLength(int item)
    {
        setInt("tenancyTermLength", item);
    }
    /**
     * Object:诚意预留's 约定免租天数property 
     */
    public int getPlightFreeDays()
    {
        return getInt("plightFreeDays");
    }
    public void setPlightFreeDays(int item)
    {
        setInt("plightFreeDays", item);
    }
    /**
     * Object:诚意预留's 约定开始日期property 
     */
    public java.util.Date getPlightStrartDate()
    {
        return getDate("plightStrartDate");
    }
    public void setPlightStrartDate(java.util.Date item)
    {
        setDate("plightStrartDate", item);
    }
    /**
     * Object:诚意预留's 约定租期property 
     */
    public java.math.BigDecimal getLeaseCount()
    {
        return getBigDecimal("leaseCount");
    }
    public void setLeaseCount(java.math.BigDecimal item)
    {
        setBigDecimal("leaseCount", item);
    }
    /**
     * Object:诚意预留's 约定结束日期property 
     */
    public java.util.Date getPlightEndDate()
    {
        return getDate("plightEndDate");
    }
    public void setPlightEndDate(java.util.Date item)
    {
        setDate("plightEndDate", item);
    }
    /**
     * Object:诚意预留's 是否执行property 
     */
    public boolean isIsExecuted()
    {
        return getBoolean("isExecuted");
    }
    public void setIsExecuted(boolean item)
    {
        setBoolean("isExecuted", item);
    }
    /**
     * Object:诚意预留's 执行时间property 
     */
    public java.sql.Timestamp getExecutedTime()
    {
        return getTimestamp("executedTime");
    }
    public void setExecutedTime(java.sql.Timestamp item)
    {
        setTimestamp("executedTime", item);
    }
    /**
     * Object: 诚意预留 's 销售项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object:诚意预留's 业务单据状态property 
     */
    public com.kingdee.eas.fdc.tenancy.BizStateEnum getBizState()
    {
        return com.kingdee.eas.fdc.tenancy.BizStateEnum.getEnum(getString("bizState"));
    }
    public void setBizState(com.kingdee.eas.fdc.tenancy.BizStateEnum item)
    {
		if (item != null) {
        setString("bizState", item.getValue());
		}
    }
    /**
     * Object:诚意预留's 是否应收诚意金property 
     */
    public boolean isIsSincerReceive()
    {
        return getBoolean("isSincerReceive");
    }
    public void setIsSincerReceive(boolean item)
    {
        setBoolean("isSincerReceive", item);
    }
    /**
     * Object: 诚意预留 's 付款明细分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.SincerPaylistEntrysCollection getPayListEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.SincerPaylistEntrysCollection)get("payListEntrys");
    }
    /**
     * Object:诚意预留's 满意度property 
     */
    public int getSatisfaction()
    {
        return getInt("satisfaction");
    }
    public void setSatisfaction(int item)
    {
        setInt("satisfaction", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("37796DDC");
    }
}