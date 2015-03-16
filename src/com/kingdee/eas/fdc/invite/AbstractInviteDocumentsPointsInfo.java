package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteDocumentsPointsInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteDocumentsPointsInfo()
    {
        this("id");
    }
    protected AbstractInviteDocumentsPointsInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:招标/议标文件---招标要点表's 招标范围property 
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
     * Object:招标/议标文件---招标要点表's 计量规则property 
     */
    public String getRule()
    {
        return getString("rule");
    }
    public void setRule(String item)
    {
        setString("rule", item);
    }
    /**
     * Object:招标/议标文件---招标要点表's 工程量清单/组价清单property 
     */
    public String getList()
    {
        return getString("list");
    }
    public void setList(String item)
    {
        setString("list", item);
    }
    /**
     * Object:招标/议标文件---招标要点表's 付款方式property 
     */
    public String getPayMethod()
    {
        return getString("payMethod");
    }
    public void setPayMethod(String item)
    {
        setString("payMethod", item);
    }
    /**
     * Object:招标/议标文件---招标要点表's 主材调差公式property 
     */
    public String getFormula()
    {
        return getString("formula");
    }
    public void setFormula(String item)
    {
        setString("formula", item);
    }
    /**
     * Object:招标/议标文件---招标要点表's 结算方式property 
     */
    public String getSettle()
    {
        return getString("settle");
    }
    public void setSettle(String item)
    {
        setString("settle", item);
    }
    /**
     * Object:招标/议标文件---招标要点表's 与标准文本不一致处property 
     */
    public String getDiff()
    {
        return getString("diff");
    }
    public void setDiff(String item)
    {
        setString("diff", item);
    }
    /**
     * Object:招标/议标文件---招标要点表's 其他property 
     */
    public String getOther()
    {
        return getString("other");
    }
    public void setOther(String item)
    {
        setString("other", item);
    }
    /**
     * Object: 招标/议标文件---招标要点表 's null property 
     */
    public com.kingdee.eas.fdc.invite.InviteDocumentsInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.InviteDocumentsInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.InviteDocumentsInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0821C2CE");
    }
}