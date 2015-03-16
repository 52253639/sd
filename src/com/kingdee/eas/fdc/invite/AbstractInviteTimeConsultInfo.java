package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteTimeConsultInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractInviteTimeConsultInfo()
    {
        this("id");
    }
    protected AbstractInviteTimeConsultInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:增加回标次数请示's 增加原因property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    /**
     * Object:增加回标次数请示's 目前采购进度property 
     */
    public String getSchedule()
    {
        return getString("schedule");
    }
    public void setSchedule(String item)
    {
        setString("schedule", item);
    }
    /**
     * Object:增加回标次数请示's 后续安排property 
     */
    public String getArrangements()
    {
        return getString("arrangements");
    }
    public void setArrangements(String item)
    {
        setString("arrangements", item);
    }
    /**
     * Object:增加回标次数请示's 增加回标次数property 
     */
    public int getTimes()
    {
        return getInt("times");
    }
    public void setTimes(int item)
    {
        setInt("times", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F70982F2");
    }
}