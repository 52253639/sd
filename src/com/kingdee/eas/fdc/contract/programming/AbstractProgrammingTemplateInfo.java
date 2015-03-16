package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingTemplateInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractProgrammingTemplateInfo()
    {
        this("id");
    }
    protected AbstractProgrammingTemplateInfo(String pkField)
    {
        super(pkField);
        put("Entires", new com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireCollection());
    }
    /**
     * Object: 合约框架模板 's 合约框架 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireCollection getEntires()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireCollection)get("Entires");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BFE63543");
    }
}