package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAFMortgagedInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractAFMortgagedInfo()
    {
        this("id");
    }
    protected AbstractAFMortgagedInfo(String pkField)
    {
        super(pkField);
        put("ApproachEntrys", new com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryCollection());
        put("DataEntrys", new com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryCollection());
    }
    /**
     * Object: 公积金/按揭方案 's 按揭办理流程 property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryCollection getApproachEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryCollection)get("ApproachEntrys");
    }
    /**
     * Object: 公积金/按揭方案 's 按揭办理资料 property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryCollection getDataEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryCollection)get("DataEntrys");
    }
    /**
     * Object: 公积金/按揭方案 's 销售项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:公积金/按揭方案's 款项类别property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMmmTypeEnum getMmType()
    {
        return com.kingdee.eas.fdc.sellhouse.AFMmmTypeEnum.getEnum(getString("mmType"));
    }
    public void setMmType(com.kingdee.eas.fdc.sellhouse.AFMmmTypeEnum item)
    {
		if (item != null) {
        setString("mmType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6FE57B08");
    }
}