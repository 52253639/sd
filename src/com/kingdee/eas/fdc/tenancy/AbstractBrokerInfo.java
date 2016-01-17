package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBrokerInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractBrokerInfo()
    {
        this("id");
    }
    protected AbstractBrokerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:经纪人's 手机property 
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
     * Object:经纪人's 密码property 
     */
    public String getPassword()
    {
        return getString("password");
    }
    public void setPassword(String item)
    {
        setString("password", item);
    }
    /**
     * Object:经纪人's 性别property 
     */
    public com.kingdee.eas.fdc.sellhouse.SexEnum getSex()
    {
        return com.kingdee.eas.fdc.sellhouse.SexEnum.getEnum(getString("sex"));
    }
    public void setSex(com.kingdee.eas.fdc.sellhouse.SexEnum item)
    {
		if (item != null) {
        setString("sex", item.getValue());
		}
    }
    /**
     * Object:经纪人's 身份证号property 
     */
    public String getIdNum()
    {
        return getString("idNum");
    }
    public void setIdNum(String item)
    {
        setString("idNum", item);
    }
    /**
     * Object:经纪人's 微信号property 
     */
    public String getWeChatNum()
    {
        return getString("weChatNum");
    }
    public void setWeChatNum(String item)
    {
        setString("weChatNum", item);
    }
    /**
     * Object:经纪人's 开户银行property 
     */
    public String getBank()
    {
        return getString("bank");
    }
    public void setBank(String item)
    {
        setString("bank", item);
    }
    /**
     * Object:经纪人's 银行账号property 
     */
    public String getAccountNum()
    {
        return getString("accountNum");
    }
    public void setAccountNum(String item)
    {
        setString("accountNum", item);
    }
    /**
     * Object:经纪人's 开户名property 
     */
    public String getAccountName()
    {
        return getString("accountName");
    }
    public void setAccountName(String item)
    {
        setString("accountName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E65886C2");
    }
}