package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteAllInformationInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInviteAllInformationInfo()
    {
        this("id");
    }
    protected AbstractInviteAllInformationInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 招标执行信息 's 招标立项 property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectInfo getInviteProject()
    {
        return (com.kingdee.eas.fdc.invite.InviteProjectInfo)get("inviteProject");
    }
    public void setInviteProject(com.kingdee.eas.fdc.invite.InviteProjectInfo item)
    {
        put("inviteProject", item);
    }
    /**
     * Object: 招标执行信息 's 标书文件合成 property 
     */
    public com.kingdee.eas.fdc.invite.InviteFileMergeInfo getInviteFileMerge()
    {
        return (com.kingdee.eas.fdc.invite.InviteFileMergeInfo)get("inviteFileMerge");
    }
    public void setInviteFileMerge(com.kingdee.eas.fdc.invite.InviteFileMergeInfo item)
    {
        put("inviteFileMerge", item);
    }
    /**
     * Object: 招标执行信息 's 供应商资格预审 property 
     */
    public com.kingdee.eas.fdc.invite.SupplierQualifyInfo getSupplierQualify()
    {
        return (com.kingdee.eas.fdc.invite.SupplierQualifyInfo)get("supplierQualify");
    }
    public void setSupplierQualify(com.kingdee.eas.fdc.invite.SupplierQualifyInfo item)
    {
        put("supplierQualify", item);
    }
    /**
     * Object: 招标执行信息 's 确定评标专家 property 
     */
    public com.kingdee.eas.fdc.invite.ExpertQualifyInfo getExpertQualify()
    {
        return (com.kingdee.eas.fdc.invite.ExpertQualifyInfo)get("expertQualify");
    }
    public void setExpertQualify(com.kingdee.eas.fdc.invite.ExpertQualifyInfo item)
    {
        put("expertQualify", item);
    }
    /**
     * Object: 招标执行信息 's 确定评标模板 property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseTemplateInfo getAppraiseTemplate()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseTemplateInfo)get("appraiseTemplate");
    }
    public void setAppraiseTemplate(com.kingdee.eas.fdc.invite.AppraiseTemplateInfo item)
    {
        put("appraiseTemplate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A9E40558");
    }
}