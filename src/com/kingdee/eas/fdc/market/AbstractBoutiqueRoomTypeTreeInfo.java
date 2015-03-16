package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBoutiqueRoomTypeTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractBoutiqueRoomTypeTreeInfo()
    {
        this("id");
    }
    protected AbstractBoutiqueRoomTypeTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 精品户型组别 's 父节点 property 
     */
    public com.kingdee.eas.fdc.market.BoutiqueRoomTypeTreeInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.BoutiqueRoomTypeTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.BoutiqueRoomTypeTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D379CCC8");
    }
}