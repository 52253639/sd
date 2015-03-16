package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCertificateInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractCertificateInfo()
    {
        this("id");
    }
    protected AbstractCertificateInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:证件名称's 客户类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum getCustomerType()
    {
        return com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum.getEnum(getString("customerType"));
    }
    public void setCustomerType(com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum item)
    {
		if (item != null) {
        setString("customerType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AFFE93DC");
    }
}