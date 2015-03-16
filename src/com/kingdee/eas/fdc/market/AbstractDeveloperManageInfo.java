package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDeveloperManageInfo extends com.kingdee.eas.fdc.propertymgmt.PPMDataBaseInfo implements Serializable 
{
    public AbstractDeveloperManageInfo()
    {
        this("id");
    }
    protected AbstractDeveloperManageInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:开发商管理's 联系人property 
     */
    public String getLinkMan()
    {
        return getString("linkMan");
    }
    public void setLinkMan(String item)
    {
        setString("linkMan", item);
    }
    /**
     * Object:开发商管理's 联系电话property 
     */
    public String getLinkTel()
    {
        return getString("linkTel");
    }
    public void setLinkTel(String item)
    {
        setString("linkTel", item);
    }
    /**
     * Object:开发商管理's 通讯地址property 
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
     * Object:开发商管理's 邮编property 
     */
    public String getPostCode()
    {
        return getString("postCode");
    }
    public void setPostCode(String item)
    {
        setString("postCode", item);
    }
    /**
     * Object:开发商管理's 网站property 
     */
    public String getWebSite()
    {
        return getString("webSite");
    }
    public void setWebSite(String item)
    {
        setString("webSite", item);
    }
    /**
     * Object:开发商管理's 电子邮箱property 
     */
    public String getEMail()
    {
        return getString("eMail");
    }
    public void setEMail(String item)
    {
        setString("eMail", item);
    }
    /**
     * Object:开发商管理's 营业执照号property 
     */
    public String getLicenceNumber()
    {
        return getString("licenceNumber");
    }
    public void setLicenceNumber(String item)
    {
        setString("licenceNumber", item);
    }
    /**
     * Object:开发商管理's 法人代表  property 
     */
    public String getLawPerson()
    {
        return getString("lawPerson");
    }
    public void setLawPerson(String item)
    {
        setString("lawPerson", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9B500FDE");
    }
}