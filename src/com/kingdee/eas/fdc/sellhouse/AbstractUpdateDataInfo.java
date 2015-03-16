package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractUpdateDataInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractUpdateDataInfo()
    {
        this("id");
    }
    protected AbstractUpdateDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:数据升级's 执行时间property 
     */
    public java.util.Date getExecuteTime()
    {
        return getDate("executeTime");
    }
    public void setExecuteTime(java.util.Date item)
    {
        setDate("executeTime", item);
    }
    /**
     * Object:数据升级's 执行完成property 
     */
    public boolean isIsExecuted()
    {
        return getBoolean("isExecuted");
    }
    public void setIsExecuted(boolean item)
    {
        setBoolean("isExecuted", item);
    }
    /**
     * Object:数据升级's 依赖前置执行property 
     */
    public boolean isIsDependPre()
    {
        return getBoolean("isDependPre");
    }
    public void setIsDependPre(boolean item)
    {
        setBoolean("isDependPre", item);
    }
    /**
     * Object:数据升级's 方法名property 
     */
    public String getMethodName()
    {
        return getString("methodName");
    }
    public void setMethodName(String item)
    {
        setString("methodName", item);
    }
    /**
     * Object:数据升级's nullproperty 
     */
    public com.kingdee.eas.fdc.basedata.MoneySysTypeEnum getMoneySysTypeEnum()
    {
        return com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.getEnum(getString("moneySysTypeEnum"));
    }
    public void setMoneySysTypeEnum(com.kingdee.eas.fdc.basedata.MoneySysTypeEnum item)
    {
		if (item != null) {
        setString("moneySysTypeEnum", item.getValue());
		}
    }
    /**
     * Object:数据升级's 执行次数property 
     */
    public int getExexuteTimes()
    {
        return getInt("exexuteTimes");
    }
    public void setExexuteTimes(int item)
    {
        setInt("exexuteTimes", item);
    }
    /**
     * Object:数据升级's 脚本开发人property 
     */
    public String getProgrammer()
    {
        return getString("programmer");
    }
    public void setProgrammer(String item)
    {
        setString("programmer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2A0FE12E");
    }
}