package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierStockLinkPersonInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierStockLinkPersonInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierStockLinkPersonInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:联系人's 姓名property 
     */
    public String getPersonName()
    {
        return getString("personName");
    }
    public void setPersonName(String item)
    {
        setString("personName", item);
    }
    /**
     * Object:联系人's 职务property 
     */
    public String getPosition()
    {
        return getString("position");
    }
    public void setPosition(String item)
    {
        setString("position", item);
    }
    /**
     * Object:联系人's 手机property 
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
     * Object:联系人's 办公电话property 
     */
    public String getWorkPhone()
    {
        return getString("workPhone");
    }
    public void setWorkPhone(String item)
    {
        setString("workPhone", item);
    }
    /**
     * Object:联系人's 传真property 
     */
    public String getPersonFax()
    {
        return getString("personFax");
    }
    public void setPersonFax(String item)
    {
        setString("personFax", item);
    }
    /**
     * Object:联系人's 是否为授权联系人property 
     */
    public boolean isIsDefault()
    {
        return getBoolean("isDefault");
    }
    public void setIsDefault(boolean item)
    {
        setBoolean("isDefault", item);
    }
    /**
     * Object:联系人's 邮箱property 
     */
    public String getEmail()
    {
        return getString("email");
    }
    public void setEmail(String item)
    {
        setString("email", item);
    }
    /**
     * Object: 联系人 's 所属单据头 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:联系人's 联系方式property 
     */
    public String getContact()
    {
        return getString("contact");
    }
    public void setContact(String item)
    {
        setString("contact", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9BB9DD8F");
    }
}