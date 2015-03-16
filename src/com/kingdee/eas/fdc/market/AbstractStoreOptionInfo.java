package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractStoreOptionInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractStoreOptionInfo()
    {
        this("id");
    }
    protected AbstractStoreOptionInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:题库题目选项's 选项标题property 
     */
    public String getTopic()
    {
        return getString("topic");
    }
    public void setTopic(String item)
    {
        setString("topic", item);
    }
    /**
     * Object:题库题目选项's 预留长度property 
     */
    public java.math.BigDecimal getXLength()
    {
        return getBigDecimal("xLength");
    }
    public void setXLength(java.math.BigDecimal item)
    {
        setBigDecimal("xLength", item);
    }
    /**
     * Object:题库题目选项's 预留高度property 
     */
    public java.math.BigDecimal getXHeight()
    {
        return getBigDecimal("xHeight");
    }
    public void setXHeight(java.math.BigDecimal item)
    {
        setBigDecimal("xHeight", item);
    }
    /**
     * Object:题库题目选项's 是否标题倒置property 
     */
    public boolean isIsTopicInverse()
    {
        return getBoolean("isTopicInverse");
    }
    public void setIsTopicInverse(boolean item)
    {
        setBoolean("isTopicInverse", item);
    }
    /**
     * Object:题库题目选项's 选项序号property 
     */
    public java.math.BigDecimal getOptionNumber()
    {
        return getBigDecimal("optionNumber");
    }
    public void setOptionNumber(java.math.BigDecimal item)
    {
        setBigDecimal("optionNumber", item);
    }
    /**
     * Object: 题库题目选项 's 分组ID property 
     */
    public com.kingdee.eas.fdc.market.StoreItemInfo getItemId()
    {
        return (com.kingdee.eas.fdc.market.StoreItemInfo)get("itemId");
    }
    public void setItemId(com.kingdee.eas.fdc.market.StoreItemInfo item)
    {
        put("itemId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("032FFE45");
    }
}