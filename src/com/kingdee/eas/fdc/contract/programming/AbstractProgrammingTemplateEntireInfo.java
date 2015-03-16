package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProgrammingTemplateEntireInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractProgrammingTemplateEntireInfo()
    {
        this("id");
    }
    protected AbstractProgrammingTemplateEntireInfo(String pkField)
    {
        super(pkField);
        put("pteCost", new com.kingdee.eas.fdc.contract.programming.PTECostCollection());
        put("pteEnonomy", new com.kingdee.eas.fdc.contract.programming.PTEEnonomyCollection());
    }
    /**
     * Object: 合约框架 's 合约框架模版 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:合约框架's 工作范围property 
     */
    public String getScope()
    {
        return getString("scope");
    }
    public void setScope(String item)
    {
        setString("scope", item);
    }
    /**
     * Object:合约框架's 甲供及甲指材设property 
     */
    public String getProblem()
    {
        return getString("problem");
    }
    public void setProblem(String item)
    {
        setString("problem", item);
    }
    /**
     * Object:合约框架's 附件property 
     */
    public String getAttachment()
    {
        return getString("attachment");
    }
    public void setAttachment(String item)
    {
        setString("attachment", item);
    }
    /**
     * Object: 合约框架 's 上级框架合约 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 合约框架 's 成本构成 property 
     */
    public com.kingdee.eas.fdc.contract.programming.PTECostCollection getPteCost()
    {
        return (com.kingdee.eas.fdc.contract.programming.PTECostCollection)get("pteCost");
    }
    /**
     * Object: 合约框架 's 经济条款 property 
     */
    public com.kingdee.eas.fdc.contract.programming.PTEEnonomyCollection getPteEnonomy()
    {
        return (com.kingdee.eas.fdc.contract.programming.PTEEnonomyCollection)get("pteEnonomy");
    }
    /**
     * Object:合约框架's 排序列property 
     */
    public int getSortNumber()
    {
        return getInt("sortNumber");
    }
    public void setSortNumber(int item)
    {
        setInt("sortNumber", item);
    }
    /**
     * Object: 合约框架 's 合同类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B9EEC6B4");
    }
}