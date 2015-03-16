package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMediaInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractMediaInfo()
    {
        this("id");
    }
    protected AbstractMediaInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:媒体管理's 简称property 
     */
    public String getShortName()
    {
        return getString("shortName");
    }
    public void setShortName(String item)
    {
        setString("shortName", item);
    }
    /**
     * Object:媒体管理's 简介property 
     */
    public String getSynopsis()
    {
        return getString("synopsis");
    }
    public void setSynopsis(String item)
    {
        setString("synopsis", item);
    }
    /**
     * Object:媒体管理's 优惠信息property 
     */
    public String getPreferentialMsg()
    {
        return getString("preferentialMsg");
    }
    public void setPreferentialMsg(String item)
    {
        setString("preferentialMsg", item);
    }
    /**
     * Object: 媒体管理 's 联络人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getLinkMan()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("linkMan");
    }
    public void setLinkMan(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("linkMan", item);
    }
    /**
     * Object: 媒体管理 's 营销项目 property 
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
     * Object: 媒体管理 's 媒体分类 property 
     */
    public com.kingdee.eas.fdc.market.MediaTypeInfo getMediaType()
    {
        return (com.kingdee.eas.fdc.market.MediaTypeInfo)get("mediaType");
    }
    public void setMediaType(com.kingdee.eas.fdc.market.MediaTypeInfo item)
    {
        put("mediaType", item);
    }
    /**
     * Object:媒体管理's 联络人property 
     */
    public String getContractMan()
    {
        return getString("contractMan");
    }
    public void setContractMan(String item)
    {
        setString("contractMan", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FC7A04B3");
    }
}