package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWebMarkFieldInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractWebMarkFieldInfo()
    {
        this("id");
    }
    protected AbstractWebMarkFieldInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:ÍøÇ©×Ö¶Î's ÃèÊöÃûproperty 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:ÍøÇ©×Ö¶Î's ÍøÒ³ÔªËØ±íÊ¾property 
     */
    public String getWebID()
    {
        return getString("webID");
    }
    public void setWebID(String item)
    {
        setString("webID", item);
    }
    /**
     * Object:ÍøÇ©×Ö¶Î's ÍøÒ³ÔªËØÃûproperty 
     */
    public String getWebName()
    {
        return getString("webName");
    }
    public void setWebName(String item)
    {
        setString("webName", item);
    }
    /**
     * Object:ÍøÇ©×Ö¶Î's ÍøÇ©ÊôĞÔproperty 
     */
    public String getWebValue()
    {
        return getString("webValue");
    }
    public void setWebValue(String item)
    {
        setString("webValue", item);
    }
    /**
     * Object:ÍøÇ©×Ö¶Î's ÍøÒ³ÔªËØÀàĞÍproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.WebMetaTypeEnum getWebMetaType()
    {
        return com.kingdee.eas.fdc.sellhouse.WebMetaTypeEnum.getEnum(getString("webMetaType"));
    }
    public void setWebMetaType(com.kingdee.eas.fdc.sellhouse.WebMetaTypeEnum item)
    {
		if (item != null) {
        setString("webMetaType", item.getValue());
		}
    }
    /**
     * Object:ÍøÇ©×Ö¶Î's ÍøÒ³×Ö¶Î¶ÔÓ¦ÊÛÂ¥ÏµÍ³µÄ×Ö¶ÎÃûproperty 
     */
    public String getFileName()
    {
        return getString("fileName");
    }
    public void setFileName(String item)
    {
        setString("fileName", item);
    }
    /**
     * Object:ÍøÇ©×Ö¶Î's ×Ö·ûÀàĞÍproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.FiledEnum getFieldType()
    {
        return com.kingdee.eas.fdc.sellhouse.FiledEnum.getEnum(getString("fieldType"));
    }
    public void setFieldType(com.kingdee.eas.fdc.sellhouse.FiledEnum item)
    {
		if (item != null) {
        setString("fieldType", item.getValue());
		}
    }
    /**
     * Object:ÍøÇ©×Ö¶Î's Ä¬ÈÏ×Ö¶Îproperty 
     */
    public String getDefultValue()
    {
        return getString("defultValue");
    }
    public void setDefultValue(String item)
    {
        setString("defultValue", item);
    }
    /**
     * Object: ÍøÇ©×Ö¶Î 's ÍøÇ©²½Öè property 
     */
    public com.kingdee.eas.fdc.sellhouse.WebMarkProcessInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.WebMarkProcessInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.WebMarkProcessInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4C46AB94");
    }
}