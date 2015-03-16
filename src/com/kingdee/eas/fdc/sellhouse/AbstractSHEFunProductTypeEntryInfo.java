package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEFunProductTypeEntryInfo extends AbstractObjectValue implements Serializable 
{
    public AbstractSHEFunProductTypeEntryInfo()
    {
        this("id");
    }
    protected AbstractSHEFunProductTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:售楼设置-产品类型分录's nullproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    /**
     * Object: 售楼设置-产品类型分录 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEFunctionSetEntryInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEFunctionSetEntryInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SHEFunctionSetEntryInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 售楼设置-产品类型分录 's 产品类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B354D293");
    }
}