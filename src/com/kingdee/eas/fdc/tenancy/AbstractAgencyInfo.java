package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAgencyInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractAgencyInfo()
    {
        this("id");
    }
    protected AbstractAgencyInfo(String pkField)
    {
        super(pkField);
        put("persons", new com.kingdee.eas.fdc.tenancy.AgencyPersonCollection());
    }
    /**
     * Object:中介机构's 地址property 
     */
    public String getAdress()
    {
        return getString("adress");
    }
    public void setAdress(String item)
    {
        setString("adress", item);
    }
    /**
     * Object:中介机构's 传真property 
     */
    public String getFax()
    {
        return getString("fax");
    }
    public void setFax(String item)
    {
        setString("fax", item);
    }
    /**
     * Object:中介机构's 电话property 
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
     * Object: 中介机构 's 人员 property 
     */
    public com.kingdee.eas.fdc.tenancy.AgencyPersonCollection getPersons()
    {
        return (com.kingdee.eas.fdc.tenancy.AgencyPersonCollection)get("persons");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E4042AEE");
    }
}