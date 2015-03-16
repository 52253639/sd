package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEPayTypeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractSHEPayTypeInfo()
    {
        this("id");
    }
    protected AbstractSHEPayTypeInfo(String pkField)
    {
        super(pkField);
        put("payLists", new com.kingdee.eas.fdc.sellhouse.PayListEntryCollection());
        put("bizLists", new com.kingdee.eas.fdc.sellhouse.BizListEntryCollection());
    }
    /**
     * Object: 付款方案 's 销售项目 property 
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
     * Object:付款方案's 折扣property 
     */
    public java.math.BigDecimal getAgio()
    {
        return getBigDecimal("agio");
    }
    public void setAgio(java.math.BigDecimal item)
    {
        setBigDecimal("agio", item);
    }
    /**
     * Object:付款方案's 按揭贷款计算方法property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum getLoanCalculateType()
    {
        return com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum.getEnum(getString("loanCalculateType"));
    }
    public void setLoanCalculateType(com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum item)
    {
		if (item != null) {
        setString("loanCalculateType", item.getValue());
		}
    }
    /**
     * Object:付款方案's 公积金计算方法property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum getAccumulationCalculateType()
    {
        return com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum.getEnum(getString("accumulationCalculateType"));
    }
    public void setAccumulationCalculateType(com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum item)
    {
		if (item != null) {
        setString("accumulationCalculateType", item.getValue());
		}
    }
    /**
     * Object:付款方案's 启用日期property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:付款方案's 按揭精度property 
     */
    public int getLoanPre()
    {
        return getInt("loanPre");
    }
    public void setLoanPre(int item)
    {
        setInt("loanPre", item);
    }
    /**
     * Object:付款方案's 公积金精度property 
     */
    public int getAfPre()
    {
        return getInt("afPre");
    }
    public void setAfPre(int item)
    {
        setInt("afPre", item);
    }
    /**
     * Object: 付款方案 's 付款明细 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PayListEntryCollection getPayLists()
    {
        return (com.kingdee.eas.fdc.sellhouse.PayListEntryCollection)get("payLists");
    }
    /**
     * Object: 付款方案 's 业务分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BizListEntryCollection getBizLists()
    {
        return (com.kingdee.eas.fdc.sellhouse.BizListEntryCollection)get("bizLists");
    }
    /**
     * Object: 付款方案 's 贷款按揭资料 property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanDataInfo getLoanLoanData()
    {
        return (com.kingdee.eas.fdc.sellhouse.LoanDataInfo)get("loanLoanData");
    }
    public void setLoanLoanData(com.kingdee.eas.fdc.sellhouse.LoanDataInfo item)
    {
        put("loanLoanData", item);
    }
    /**
     * Object: 付款方案 's 公积金贷款资料 property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanDataInfo getAfLoanData()
    {
        return (com.kingdee.eas.fdc.sellhouse.LoanDataInfo)get("afLoanData");
    }
    public void setAfLoanData(com.kingdee.eas.fdc.sellhouse.LoanDataInfo item)
    {
        put("afLoanData", item);
    }
    /**
     * Object:付款方案's 生效日期property 
     */
    public java.util.Date getValidDate()
    {
        return getDate("validDate");
    }
    public void setValidDate(java.util.Date item)
    {
        setDate("validDate", item);
    }
    /**
     * Object:付款方案's 失效日期property 
     */
    public java.util.Date getInvalidDate()
    {
        return getDate("invalidDate");
    }
    public void setInvalidDate(java.util.Date item)
    {
        setDate("invalidDate", item);
    }
    /**
     * Object:付款方案's 是否删除property 
     */
    public boolean isIsDelete()
    {
        return getBoolean("isDelete");
    }
    public void setIsDelete(boolean item)
    {
        setBoolean("isDelete", item);
    }
    /**
     * Object:付款方案's 定金是否计入房款property 
     */
    public boolean isIsTotal()
    {
        return getBoolean("isTotal");
    }
    public void setIsTotal(boolean item)
    {
        setBoolean("isTotal", item);
    }
    /**
     * Object:付款方案's 是否按揭property 
     */
    public boolean isIsLoan()
    {
        return getBoolean("isLoan");
    }
    public void setIsLoan(boolean item)
    {
        setBoolean("isLoan", item);
    }
    /**
     * Object: 付款方案 's 按揭银行 property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getLoanBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("loanBank");
    }
    public void setLoanBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("loanBank", item);
    }
    /**
     * Object: 付款方案 's 公积金银行 property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getAcfBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("acfBank");
    }
    public void setAcfBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("acfBank", item);
    }
    /**
     * Object: 付款方案 's 公积金方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo getAfScheme()
    {
        return (com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo)get("afScheme");
    }
    public void setAfScheme(com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo item)
    {
        put("afScheme", item);
    }
    /**
     * Object: 付款方案 's 按揭方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo getLoanScheme()
    {
        return (com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo)get("loanScheme");
    }
    public void setLoanScheme(com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo item)
    {
        put("loanScheme", item);
    }
    /**
     * Object:付款方案's 新按揭精度property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanPreEnum getNewLoanPre()
    {
        return com.kingdee.eas.fdc.sellhouse.LoanPreEnum.getEnum(getString("newLoanPre"));
    }
    public void setNewLoanPre(com.kingdee.eas.fdc.sellhouse.LoanPreEnum item)
    {
		if (item != null) {
        setString("newLoanPre", item.getValue());
		}
    }
    /**
     * Object:付款方案's 新公积金精度property 
     */
    public com.kingdee.eas.fdc.sellhouse.AfPreEnum getNewAfPre()
    {
        return com.kingdee.eas.fdc.sellhouse.AfPreEnum.getEnum(getString("newAfPre"));
    }
    public void setNewAfPre(com.kingdee.eas.fdc.sellhouse.AfPreEnum item)
    {
		if (item != null) {
        setString("newAfPre", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DFF2AA4D");
    }
}