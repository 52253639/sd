package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractLinkmanEntryInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractLinkmanEntryInfo()
    {
        this("id");
    }
    protected AbstractLinkmanEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 客户联系人分录 's 客户信息头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 客户联系人分录 's 房地产客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getFdcCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("fdcCustomer");
    }
    public void setFdcCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("fdcCustomer", item);
    }
    /**
     * Object:客户联系人分录's 关系property 
     */
    public String getRelation()
    {
        return getString("relation");
    }
    public void setRelation(String item)
    {
        setString("relation", item);
    }
    /**
     * Object:客户联系人分录's 电话property 
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
     * Object:客户联系人分录's 性别property 
     */
    public String getSex()
    {
        return getString("sex");
    }
    public void setSex(String item)
    {
        setString("sex", item);
    }
    /**
     * Object:客户联系人分录's 联系人姓名property 
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
     * Object:客户联系人分录's 邮箱property 
     */
    public String getMail()
    {
        return getString("mail");
    }
    public void setMail(String item)
    {
        setString("mail", item);
    }
    /**
     * Object:客户联系人分录's 通信地址property 
     */
    public String getAddress()
    {
        return getString("address");
    }
    public void setAddress(String item)
    {
        setString("address", item);
    }
    /**
     * Object:客户联系人分录's 职务property 
     */
    public String getJob()
    {
        return getString("job");
    }
    public void setJob(String item)
    {
        setString("job", item);
    }
    /**
     * Object:客户联系人分录's 爱好property 
     */
    public String getAh()
    {
        return getString("ah");
    }
    public void setAh(String item)
    {
        setString("ah", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7F04C78D");
    }
}