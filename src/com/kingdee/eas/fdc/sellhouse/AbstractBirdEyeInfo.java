package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBirdEyeInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBirdEyeInfo()
    {
        this("id");
    }
    protected AbstractBirdEyeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 鸟瞰图标签 's 项目 property 
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
     * Object:鸟瞰图标签's 标签X坐标property 
     */
    public int getLocationX()
    {
        return getInt("locationX");
    }
    public void setLocationX(int item)
    {
        setInt("locationX", item);
    }
    /**
     * Object:鸟瞰图标签's 标签Y坐标property 
     */
    public int getLocationY()
    {
        return getInt("locationY");
    }
    public void setLocationY(int item)
    {
        setInt("locationY", item);
    }
    /**
     * Object:鸟瞰图标签's 标签关联的项目或者楼栋property 
     */
    public String getSellProjectOrBuildingId()
    {
        return getString("sellProjectOrBuildingId");
    }
    public void setSellProjectOrBuildingId(String item)
    {
        setString("sellProjectOrBuildingId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4789C37D");
    }
}