package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractWFEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractWFEntryInfo()
    {
        this("id");
    }
    protected AbstractContractWFEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合同流程类型分录 's 合同流程类型 property 
     */
    public com.kingdee.eas.fdc.contract.ContractWFTypeInfo getContractWFType()
    {
        return (com.kingdee.eas.fdc.contract.ContractWFTypeInfo)get("contractWFType");
    }
    public void setContractWFType(com.kingdee.eas.fdc.contract.ContractWFTypeInfo item)
    {
        put("contractWFType", item);
    }
    /**
     * Object: 合同流程类型分录 's 合同类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getHead()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4685545F");
    }
}