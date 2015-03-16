package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewListingInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractNewListingInfo()
    {
        this("id");
    }
    protected AbstractNewListingInfo(String pkField)
    {
        super(pkField);
        put("pages", new com.kingdee.eas.fdc.invite.NewListingPageCollection());
    }
    /**
     * Object: 招标清单 's 招标类型 property 
     */
    public com.kingdee.eas.fdc.invite.InviteTypeInfo getInviteType()
    {
        return (com.kingdee.eas.fdc.invite.InviteTypeInfo)get("inviteType");
    }
    public void setInviteType(com.kingdee.eas.fdc.invite.InviteTypeInfo item)
    {
        put("inviteType", item);
    }
    /**
     * Object:招标清单's 模板二进制数据property 
     */
    public byte[] getTableData()
    {
        return (byte[])get("tableData");
    }
    public void setTableData(byte[] item)
    {
        put("tableData", item);
    }
    /**
     * Object: 招标清单 's 页签 property 
     */
    public com.kingdee.eas.fdc.invite.NewListingPageCollection getPages()
    {
        return (com.kingdee.eas.fdc.invite.NewListingPageCollection)get("pages");
    }
    /**
     * Object: 招标清单 's 工程项目 property 
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
     * Object: 招标清单 's 模板 property 
     */
    public com.kingdee.eas.fdc.invite.NewListTempletInfo getTemplet()
    {
        return (com.kingdee.eas.fdc.invite.NewListTempletInfo)get("templet");
    }
    public void setTemplet(com.kingdee.eas.fdc.invite.NewListTempletInfo item)
    {
        put("templet", item);
    }
    /**
     * Object:招标清单's 税率property 
     */
    public java.math.BigDecimal getTax()
    {
        return getBigDecimal("tax");
    }
    public void setTax(java.math.BigDecimal item)
    {
        setBigDecimal("tax", item);
    }
    /**
     * Object:招标清单's 税金property 
     */
    public java.math.BigDecimal getTaxAmount()
    {
        return getBigDecimal("taxAmount");
    }
    public void setTaxAmount(java.math.BigDecimal item)
    {
        setBigDecimal("taxAmount", item);
    }
    /**
     * Object:招标清单's 总造价property 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    /**
     * Object: 招标清单 's 币别 property 
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
     * Object:招标清单's 报价评审状态property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getInviteAuditState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("inviteAuditState"));
    }
    public void setInviteAuditState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("inviteAuditState", item.getValue());
		}
    }
    /**
     * Object: 招标清单 's 招标立项 property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectInfo getInviteProject()
    {
        return (com.kingdee.eas.fdc.invite.InviteProjectInfo)get("inviteProject");
    }
    public void setInviteProject(com.kingdee.eas.fdc.invite.InviteProjectInfo item)
    {
        put("inviteProject", item);
    }
    /**
     * Object:招标清单's 版本号property 
     */
    public double getVersion()
    {
        return getDouble("version");
    }
    public void setVersion(double item)
    {
        setDouble("version", item);
    }
    /**
     * Object:招标清单's 是否最新版本property 
     */
    public boolean isIsLastVersion()
    {
        return getBoolean("isLastVersion");
    }
    public void setIsLastVersion(boolean item)
    {
        setBoolean("isLastVersion", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("457A7AE8");
    }
}