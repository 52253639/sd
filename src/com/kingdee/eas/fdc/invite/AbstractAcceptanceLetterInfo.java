package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAcceptanceLetterInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractAcceptanceLetterInfo()
    {
        this("id");
    }
    protected AbstractAcceptanceLetterInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:中标通知书's 是否生成合同property 
     */
    public boolean isIsCreateContract()
    {
        return getBoolean("isCreateContract");
    }
    public void setIsCreateContract(boolean item)
    {
        setBoolean("isCreateContract", item);
    }
    /**
     * Object: 中标通知书 's 中标单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object: 中标通知书 's 招标立项 property 
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
     * Object:中标通知书's 签约日期property 
     */
    public java.util.Date getSignDate()
    {
        return getDate("signDate");
    }
    public void setSignDate(java.util.Date item)
    {
        setDate("signDate", item);
    }
    /**
     * Object:中标通知书's 中标金额property 
     */
    public java.math.BigDecimal getInviteAmount()
    {
        return getBigDecimal("inviteAmount");
    }
    public void setInviteAmount(java.math.BigDecimal item)
    {
        setBigDecimal("inviteAmount", item);
    }
    /**
     * Object:中标通知书's 单位数量property 
     */
    public java.math.BigDecimal getQuantity()
    {
        return getBigDecimal("quantity");
    }
    public void setQuantity(java.math.BigDecimal item)
    {
        setBigDecimal("quantity", item);
    }
    /**
     * Object:中标通知书's 招标方式property 
     */
    public com.kingdee.eas.fdc.invite.InviteFormEnum getInviteForm()
    {
        return com.kingdee.eas.fdc.invite.InviteFormEnum.getEnum(getString("inviteForm"));
    }
    public void setInviteForm(com.kingdee.eas.fdc.invite.InviteFormEnum item)
    {
		if (item != null) {
        setString("inviteForm", item.getValue());
		}
    }
    /**
     * Object:中标通知书's 最低投标价property 
     */
    public java.math.BigDecimal getLowestPrice()
    {
        return getBigDecimal("lowestPrice");
    }
    public void setLowestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("lowestPrice", item);
    }
    /**
     * Object:中标通知书's 次低投标价property 
     */
    public java.math.BigDecimal getLowerPrice()
    {
        return getBigDecimal("lowerPrice");
    }
    public void setLowerPrice(java.math.BigDecimal item)
    {
        setBigDecimal("lowerPrice", item);
    }
    /**
     * Object:中标通知书's 中间投标价property 
     */
    public java.math.BigDecimal getMiddlePrice()
    {
        return getBigDecimal("middlePrice");
    }
    public void setMiddlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("middlePrice", item);
    }
    /**
     * Object:中标通知书's 次高投标价property 
     */
    public java.math.BigDecimal getHigherPrice()
    {
        return getBigDecimal("higherPrice");
    }
    public void setHigherPrice(java.math.BigDecimal item)
    {
        setBigDecimal("higherPrice", item);
    }
    /**
     * Object:中标通知书's 最高投标价property 
     */
    public java.math.BigDecimal getHighestPrice()
    {
        return getBigDecimal("highestPrice");
    }
    public void setHighestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("highestPrice", item);
    }
    /**
     * Object: 中标通知书 's 最低投标价单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getLowestPriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("lowestPriceUnit");
    }
    public void setLowestPriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("lowestPriceUnit", item);
    }
    /**
     * Object: 中标通知书 's 次低投标价单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getLowerPriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("lowerPriceUnit");
    }
    public void setLowerPriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("lowerPriceUnit", item);
    }
    /**
     * Object: 中标通知书 's 中间投标价单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getMiddlePriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("middlePriceUnit");
    }
    public void setMiddlePriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("middlePriceUnit", item);
    }
    /**
     * Object: 中标通知书 's 次高投标价单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getHigherPriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("higherPriceUnit");
    }
    public void setHigherPriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("higherPriceUnit", item);
    }
    /**
     * Object: 中标通知书 's 最高投标价单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getHighestPriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("highestPriceUnit");
    }
    public void setHighestPriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("highestPriceUnit", item);
    }
    /**
     * Object:中标通知书's 版本号property 
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
     * Object:中标通知书's 是否最终版本property 
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
        return new BOSObjectType("1DD52081");
    }
}