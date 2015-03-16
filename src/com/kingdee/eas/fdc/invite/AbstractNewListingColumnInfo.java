package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewListingColumnInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractNewListingColumnInfo()
    {
        this("id");
    }
    protected AbstractNewListingColumnInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 清单页签列 's 表头 property 
     */
    public com.kingdee.eas.fdc.invite.HeadColumnInfo getHeadColumn()
    {
        return (com.kingdee.eas.fdc.invite.HeadColumnInfo)get("headColumn");
    }
    public void setHeadColumn(com.kingdee.eas.fdc.invite.HeadColumnInfo item)
    {
        put("headColumn", item);
    }
    /**
     * Object: 清单页签列 's 页签头 property 
     */
    public com.kingdee.eas.fdc.invite.NewListingPageInfo getPage()
    {
        return (com.kingdee.eas.fdc.invite.NewListingPageInfo)get("page");
    }
    public void setPage(com.kingdee.eas.fdc.invite.NewListingPageInfo item)
    {
        put("page", item);
    }
    /**
     * Object:清单页签列's 次序property 
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
     * Object:清单页签列's 是否报价列property 
     */
    public boolean isIsQuoting()
    {
        return getBoolean("isQuoting");
    }
    public void setIsQuoting(boolean item)
    {
        setBoolean("isQuoting", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E3806C5E");
    }
}