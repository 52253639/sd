package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseChangeCustomerInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPurchaseChangeCustomerInfo()
    {
        this("id");
    }
    protected AbstractPurchaseChangeCustomerInfo(String pkField)
    {
        super(pkField);
        put("newCustomerEntry", new com.kingdee.eas.fdc.sellhouse.PurchaseChangeCusomerEntryCollection());
        put("oldCustomerEntry", new com.kingdee.eas.fdc.sellhouse.PurchaseChangeCusomerOldEntryCollection());
    }
    /**
     * Object: 更名单 's 认购单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("purchase");
    }
    public void setPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("purchase", item);
    }
    /**
     * Object: 更名单 's 新的客户分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseChangeCusomerEntryCollection getNewCustomerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseChangeCusomerEntryCollection)get("newCustomerEntry");
    }
    /**
     * Object: 更名单 's 旧的客户分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseChangeCusomerOldEntryCollection getOldCustomerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseChangeCusomerOldEntryCollection)get("oldCustomerEntry");
    }
    /**
     * Object:更名单's 新客户property 
     */
    public String getNewCustomer()
    {
        return getString("newCustomer");
    }
    public void setNewCustomer(String item)
    {
        setString("newCustomer", item);
    }
    /**
     * Object:更名单's 老客户property 
     */
    public String getOldCustomer()
    {
        return getString("oldCustomer");
    }
    public void setOldCustomer(String item)
    {
        setString("oldCustomer", item);
    }
    /**
     * Object:更名单's 更名状态property 
     */
    public String getChangeState()
    {
        return getString("changeState");
    }
    public void setChangeState(String item)
    {
        setString("changeState", item);
    }
    /**
     * Object: 更名单 's 更名原因 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RenameRoomReasonInfo getRenameReason()
    {
        return (com.kingdee.eas.fdc.sellhouse.RenameRoomReasonInfo)get("renameReason");
    }
    public void setRenameReason(com.kingdee.eas.fdc.sellhouse.RenameRoomReasonInfo item)
    {
        put("renameReason", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EB55C3AA");
    }
}