package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPurchaseBillInfo()
    {
        this("id");
    }
    protected AbstractPurchaseBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.invite.PurchaseBillEntryCollection());
    }
    /**
     * Object: 采购申请单 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object:采购申请单's 申购对象property 
     */
    public com.kingdee.eas.fdc.invite.PurchaseTypeEnum getPurchaseType()
    {
        return com.kingdee.eas.fdc.invite.PurchaseTypeEnum.getEnum(getString("purchaseType"));
    }
    public void setPurchaseType(com.kingdee.eas.fdc.invite.PurchaseTypeEnum item)
    {
		if (item != null) {
        setString("purchaseType", item.getValue());
		}
    }
    /**
     * Object: 采购申请单 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.PurchaseBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.invite.PurchaseBillEntryCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("991A21CC");
    }
}