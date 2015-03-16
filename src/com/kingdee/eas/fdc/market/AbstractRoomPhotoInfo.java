package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPhotoInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractRoomPhotoInfo()
    {
        this("id");
    }
    protected AbstractRoomPhotoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 房屋有关照片 's  property 
     */
    public com.kingdee.eas.fdc.market.BoutiqueRoomTypeInfo getHead()
    {
        return (com.kingdee.eas.fdc.market.BoutiqueRoomTypeInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.market.BoutiqueRoomTypeInfo item)
    {
        put("head", item);
    }
    /**
     * Object:房屋有关照片's 户型图片数据property 
     */
    public byte[] getRoomTypePicData()
    {
        return (byte[])get("roomTypePicData");
    }
    public void setRoomTypePicData(byte[] item)
    {
        put("roomTypePicData", item);
    }
    /**
     * Object:房屋有关照片's 平层图片property 
     */
    public byte[] getFlatTypePicData()
    {
        return (byte[])get("flatTypePicData");
    }
    public void setFlatTypePicData(byte[] item)
    {
        put("flatTypePicData", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B1CDB366");
    }
}