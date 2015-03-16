package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBirdEyePicInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBirdEyePicInfo()
    {
        this("id");
    }
    protected AbstractBirdEyePicInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Äñî«Í¼Í¼Æ¬ 's ÏúÊÛÏîÄ¿ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object:Äñî«Í¼Í¼Æ¬'s property 
     */
    public byte[] getPicFile()
    {
        return (byte[])get("picFile");
    }
    public void setPicFile(byte[] item)
    {
        put("picFile", item);
    }
    /**
     * Object:Äñî«Í¼Í¼Æ¬'s Í¼Æ¬Â·¾¶property 
     */
    public String getPicPath()
    {
        return getString("picPath");
    }
    public void setPicPath(String item)
    {
        setString("picPath", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F8B568CD");
    }
}