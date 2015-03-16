package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFlatLayerTypeInfo extends com.kingdee.eas.fdc.propertymgmt.PPMDataBaseInfo implements Serializable 
{
    public AbstractFlatLayerTypeInfo()
    {
        this("id");
    }
    protected AbstractFlatLayerTypeInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F71E9681");
    }
}