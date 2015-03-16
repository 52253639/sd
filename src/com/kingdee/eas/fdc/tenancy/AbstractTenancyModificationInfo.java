package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyModificationInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractTenancyModificationInfo()
    {
        this("id");
    }
    protected AbstractTenancyModificationInfo(String pkField)
    {
        super(pkField);
        put("newIncreasedRents", new com.kingdee.eas.fdc.tenancy.NewIncRentEntryCollection());
        put("newAttachDealAmountEntry", new com.kingdee.eas.fdc.tenancy.NewAttachDealAmountEntryCollection());
        put("oldIncreasedRents", new com.kingdee.eas.fdc.tenancy.OldIncRentEntryCollection());
        put("newDealAmountEntry", new com.kingdee.eas.fdc.tenancy.NewDealAmountEntryCollection());
        put("newRentFrees", new com.kingdee.eas.fdc.tenancy.NewRentFreeEntryCollection());
        put("newPayList", new com.kingdee.eas.fdc.tenancy.TenancyModificationPayCollection());
        put("oldDealAmountEntry", new com.kingdee.eas.fdc.tenancy.OldDealAmountEntryCollection());
        put("oldAttachDealAmountEntry", new com.kingdee.eas.fdc.tenancy.OldAttachDealAmountEntryCollection());
        put("oldRentFrees", new com.kingdee.eas.fdc.tenancy.OldRentFreeEntryCollection());
    }
    /**
     * Object: 合同变更 's 租赁合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancy()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancy");
    }
    public void setTenancy(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancy", item);
    }
    /**
     * Object:合同变更's 变更原因property 
     */
    public String getModificationReason()
    {
        return getString("modificationReason");
    }
    public void setModificationReason(String item)
    {
        setString("modificationReason", item);
    }
    /**
     * Object:合同变更's 备注property 
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
     * Object:合同变更's 新结束日期property 
     */
    public java.util.Date getNewEndDate()
    {
        return getDate("newEndDate");
    }
    public void setNewEndDate(java.util.Date item)
    {
        setDate("newEndDate", item);
    }
    /**
     * Object: 合同变更 's 销售项目 property 
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
     * Object: 合同变更 's 新租金递增列表分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.NewIncRentEntryCollection getNewIncreasedRents()
    {
        return (com.kingdee.eas.fdc.tenancy.NewIncRentEntryCollection)get("newIncreasedRents");
    }
    /**
     * Object: 合同变更 's 合同递增分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.OldIncRentEntryCollection getOldIncreasedRents()
    {
        return (com.kingdee.eas.fdc.tenancy.OldIncRentEntryCollection)get("oldIncreasedRents");
    }
    /**
     * Object: 合同变更 's 新免租分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.NewRentFreeEntryCollection getNewRentFrees()
    {
        return (com.kingdee.eas.fdc.tenancy.NewRentFreeEntryCollection)get("newRentFrees");
    }
    /**
     * Object: 合同变更 's 合同免租分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.OldRentFreeEntryCollection getOldRentFrees()
    {
        return (com.kingdee.eas.fdc.tenancy.OldRentFreeEntryCollection)get("oldRentFrees");
    }
    /**
     * Object: 合同变更 's 房间成交价格变更分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.NewDealAmountEntryCollection getNewDealAmountEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.NewDealAmountEntryCollection)get("newDealAmountEntry");
    }
    /**
     * Object: 合同变更 's 合同房间成交价格分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.OldDealAmountEntryCollection getOldDealAmountEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.OldDealAmountEntryCollection)get("oldDealAmountEntry");
    }
    /**
     * Object: 合同变更 's 配套资源成交价格变更分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.NewAttachDealAmountEntryCollection getNewAttachDealAmountEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.NewAttachDealAmountEntryCollection)get("newAttachDealAmountEntry");
    }
    /**
     * Object: 合同变更 's 合同配套资源成交价格变更分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.OldAttachDealAmountEntryCollection getOldAttachDealAmountEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.OldAttachDealAmountEntryCollection)get("oldAttachDealAmountEntry");
    }
    /**
     * Object: 合同变更 's 新付款计划 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyModificationPayCollection getNewPayList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyModificationPayCollection)get("newPayList");
    }
    /**
     * Object:合同变更's 旧是否自由合同property 
     */
    public boolean isOldIsFreeContract()
    {
        return getBoolean("oldIsFreeContract");
    }
    public void setOldIsFreeContract(boolean item)
    {
        setBoolean("oldIsFreeContract", item);
    }
    /**
     * Object:合同变更's 新旧是否自由合同property 
     */
    public boolean isNewIsFreeContract()
    {
        return getBoolean("newIsFreeContract");
    }
    public void setNewIsFreeContract(boolean item)
    {
        setBoolean("newIsFreeContract", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4E1D0473");
    }
}