package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBaseInviteEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBaseInviteEntryInfo()
    {
        this("id");
    }
    protected AbstractBaseInviteEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:采购招标分录基类's 备注property 
     */
    public String getContent()
    {
        return getString("content");
    }
    public void setContent(String item)
    {
        setString("content", item);
    }
    /**
     * Object:采购招标分录基类's 上传时间property 
     */
    public java.util.Date getTime()
    {
        return getDate("time");
    }
    public void setTime(java.util.Date item)
    {
        setDate("time", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1EF21BB4");
    }
}