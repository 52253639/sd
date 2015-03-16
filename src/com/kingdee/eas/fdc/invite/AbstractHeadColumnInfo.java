package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHeadColumnInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractHeadColumnInfo()
    {
        this("id");
    }
    protected AbstractHeadColumnInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:表头's 列类型property 
     */
    public com.kingdee.eas.fdc.invite.ColumnTypeEnum getColumnType()
    {
        return com.kingdee.eas.fdc.invite.ColumnTypeEnum.getEnum(getString("columnType"));
    }
    public void setColumnType(com.kingdee.eas.fdc.invite.ColumnTypeEnum item)
    {
		if (item != null) {
        setString("columnType", item.getValue());
		}
    }
    /**
     * Object: 表头 's 表头类型 property 
     */
    public com.kingdee.eas.fdc.invite.HeadTypeInfo getHeadType()
    {
        return (com.kingdee.eas.fdc.invite.HeadTypeInfo)get("headType");
    }
    public void setHeadType(com.kingdee.eas.fdc.invite.HeadTypeInfo item)
    {
        put("headType", item);
    }
    /**
     * Object: 表头 's 父节点 property 
     */
    public com.kingdee.eas.fdc.invite.HeadColumnInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.HeadColumnInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.HeadColumnInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:表头's 次序property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object:表头's 是否报价列property 
     */
    public boolean isIsQuoting()
    {
        return getBoolean("isQuoting");
    }
    public void setIsQuoting(boolean item)
    {
        setBoolean("isQuoting", item);
    }
    /**
     * Object:表头's 是否有下级结点property 
     */
    public boolean isIsHasChild()
    {
        return getBoolean("isHasChild");
    }
    public void setIsHasChild(boolean item)
    {
        setBoolean("isHasChild", item);
    }
    /**
     * Object:表头's 是否价格相关property 
     */
    public boolean isIsRefPrice()
    {
        return getBoolean("isRefPrice");
    }
    public void setIsRefPrice(boolean item)
    {
        setBoolean("isRefPrice", item);
    }
    /**
     * Object:表头's 属性property 
     */
    public com.kingdee.eas.fdc.invite.DescriptionEnum getProperty()
    {
        return com.kingdee.eas.fdc.invite.DescriptionEnum.getEnum(getString("property"));
    }
    public void setProperty(com.kingdee.eas.fdc.invite.DescriptionEnum item)
    {
		if (item != null) {
        setString("property", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("52E98B7A");
    }
}