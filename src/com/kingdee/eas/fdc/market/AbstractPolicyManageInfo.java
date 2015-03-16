package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPolicyManageInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractPolicyManageInfo()
    {
        this("id");
    }
    protected AbstractPolicyManageInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.market.PolicyManageEntryCollection());
    }
    /**
     * Object: 政策管理 's 分录 property 
     */
    public com.kingdee.eas.fdc.market.PolicyManageEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.market.PolicyManageEntryCollection)get("entrys");
    }
    /**
     * Object:政策管理's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object:政策管理's 名称property 
     */
    public String getPolicyName()
    {
        return getString("policyName");
    }
    public void setPolicyName(String item)
    {
        setString("policyName", item);
    }
    /**
     * Object: 政策管理 's 国家 property 
     */
    public com.kingdee.eas.basedata.assistant.CountryInfo getCountry()
    {
        return (com.kingdee.eas.basedata.assistant.CountryInfo)get("country");
    }
    public void setCountry(com.kingdee.eas.basedata.assistant.CountryInfo item)
    {
        put("country", item);
    }
    /**
     * Object: 政策管理 's 省 property 
     */
    public com.kingdee.eas.basedata.assistant.ProvinceInfo getProvince()
    {
        return (com.kingdee.eas.basedata.assistant.ProvinceInfo)get("province");
    }
    public void setProvince(com.kingdee.eas.basedata.assistant.ProvinceInfo item)
    {
        put("province", item);
    }
    /**
     * Object: 政策管理 's 市 property 
     */
    public com.kingdee.eas.basedata.assistant.CityInfo getCity()
    {
        return (com.kingdee.eas.basedata.assistant.CityInfo)get("city");
    }
    public void setCity(com.kingdee.eas.basedata.assistant.CityInfo item)
    {
        put("city", item);
    }
    /**
     * Object:政策管理's 政策范围property 
     */
    public String getPolicyRange()
    {
        return getString("policyRange");
    }
    public void setPolicyRange(String item)
    {
        setString("policyRange", item);
    }
    /**
     * Object:政策管理's 颁布部门property 
     */
    public String getPublishDep()
    {
        return getString("publishDep");
    }
    public void setPublishDep(String item)
    {
        setString("publishDep", item);
    }
    /**
     * Object:政策管理's 关键词property 
     */
    public String getKeyWord()
    {
        return getString("keyWord");
    }
    public void setKeyWord(String item)
    {
        setString("keyWord", item);
    }
    /**
     * Object:政策管理's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F13F3588");
    }
}