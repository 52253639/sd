package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomLoanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractRoomLoanInfo()
    {
        this("id");
    }
    protected AbstractRoomLoanInfo(String pkField)
    {
        super(pkField);
        put("aFMortgaged", new com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysCollection());
    }
    /**
     * Object: 按揭管理 's 房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object: 按揭管理 's 按揭经办人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getTransactor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("transactor");
    }
    public void setTransactor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("transactor", item);
    }
    /**
     * Object:按揭管理's 办理按揭日期property 
     */
    public java.util.Date getProcessLoanDate()
    {
        return getDate("processLoanDate");
    }
    public void setProcessLoanDate(java.util.Date item)
    {
        setDate("processLoanDate", item);
    }
    /**
     * Object:按揭管理's 抵押登记日期property 
     */
    public java.util.Date getHockEnrolDate()
    {
        return getDate("hockEnrolDate");
    }
    public void setHockEnrolDate(java.util.Date item)
    {
        setDate("hockEnrolDate", item);
    }
    /**
     * Object:按揭管理's 按揭年限property 
     */
    public int getLoanFixedYear()
    {
        return getInt("loanFixedYear");
    }
    public void setLoanFixedYear(int item)
    {
        setInt("loanFixedYear", item);
    }
    /**
     * Object:按揭管理's 实办按揭金额property 
     */
    public java.math.BigDecimal getActualLoanAmt()
    {
        return getBigDecimal("actualLoanAmt");
    }
    public void setActualLoanAmt(java.math.BigDecimal item)
    {
        setBigDecimal("actualLoanAmt", item);
    }
    /**
     * Object:按揭管理's 按揭律师property 
     */
    public String getLoanLawyer()
    {
        return getString("loanLawyer");
    }
    public void setLoanLawyer(String item)
    {
        setString("loanLawyer", item);
    }
    /**
     * Object:按揭管理's 按揭协议号property 
     */
    public String getLoanCompactNumber()
    {
        return getString("loanCompactNumber");
    }
    public void setLoanCompactNumber(String item)
    {
        setString("loanCompactNumber", item);
    }
    /**
     * Object:按揭管理's 担保期限property 
     */
    public String getAssureTerm()
    {
        return getString("assureTerm");
    }
    public void setAssureTerm(String item)
    {
        setString("assureTerm", item);
    }
    /**
     * Object:按揭管理's 合同送律师日期property 
     */
    public java.util.Date getConToLawyerDate()
    {
        return getDate("conToLawyerDate");
    }
    public void setConToLawyerDate(java.util.Date item)
    {
        setDate("conToLawyerDate", item);
    }
    /**
     * Object:按揭管理's 资料送银行日期property 
     */
    public java.util.Date getInfoToBankDate()
    {
        return getDate("infoToBankDate");
    }
    public void setInfoToBankDate(java.util.Date item)
    {
        setDate("infoToBankDate", item);
    }
    /**
     * Object:按揭管理's 贷款合同日期property 
     */
    public java.util.Date getLoanContractDate()
    {
        return getDate("loanContractDate");
    }
    public void setLoanContractDate(java.util.Date item)
    {
        setDate("loanContractDate", item);
    }
    /**
     * Object:按揭管理's 借资料日期property 
     */
    public java.util.Date getLendInfoDate()
    {
        return getDate("lendInfoDate");
    }
    public void setLendInfoDate(java.util.Date item)
    {
        setDate("lendInfoDate", item);
    }
    /**
     * Object:按揭管理's 产证交银行日期property 
     */
    public java.util.Date getBookToBankDate()
    {
        return getDate("bookToBankDate");
    }
    public void setBookToBankDate(java.util.Date item)
    {
        setDate("bookToBankDate", item);
    }
    /**
     * Object:按揭管理's 保证金划出日期property 
     */
    public java.util.Date getDepositOutDate()
    {
        return getDate("depositOutDate");
    }
    public void setDepositOutDate(java.util.Date item)
    {
        setDate("depositOutDate", item);
    }
    /**
     * Object:按揭管理's 保证金划出金额property 
     */
    public java.math.BigDecimal getDepositOutAmt()
    {
        return getBigDecimal("depositOutAmt");
    }
    public void setDepositOutAmt(java.math.BigDecimal item)
    {
        setBigDecimal("depositOutAmt", item);
    }
    /**
     * Object:按揭管理's 按揭是否公证property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanNotarizeEnum getLoanIsNotarize()
    {
        return com.kingdee.eas.fdc.sellhouse.LoanNotarizeEnum.getEnum(getString("loanIsNotarize"));
    }
    public void setLoanIsNotarize(com.kingdee.eas.fdc.sellhouse.LoanNotarizeEnum item)
    {
		if (item != null) {
        setString("loanIsNotarize", item.getValue());
		}
    }
    /**
     * Object:按揭管理's 保证金划回日期property 
     */
    public java.util.Date getDepositInDate()
    {
        return getDate("depositInDate");
    }
    public void setDepositInDate(java.util.Date item)
    {
        setDate("depositInDate", item);
    }
    /**
     * Object:按揭管理's 保证金划回金额property 
     */
    public java.math.BigDecimal getDepositInAmt()
    {
        return getBigDecimal("depositInAmt");
    }
    public void setDepositInAmt(java.math.BigDecimal item)
    {
        setBigDecimal("depositInAmt", item);
    }
    /**
     * Object:按揭管理's 公积金年限property 
     */
    public int getFundFixedYear()
    {
        return getInt("fundFixedYear");
    }
    public void setFundFixedYear(int item)
    {
        setInt("fundFixedYear", item);
    }
    /**
     * Object:按揭管理's 公积金property 
     */
    public java.math.BigDecimal getFundAmt()
    {
        return getBigDecimal("fundAmt");
    }
    public void setFundAmt(java.math.BigDecimal item)
    {
        setBigDecimal("fundAmt", item);
    }
    /**
     * Object:按揭管理's 公积金办理日期property 
     */
    public java.util.Date getFundProcessDate()
    {
        return getDate("fundProcessDate");
    }
    public void setFundProcessDate(java.util.Date item)
    {
        setDate("fundProcessDate", item);
    }
    /**
     * Object:按揭管理's 公积金放款日期property 
     */
    public java.util.Date getFundGrantDate()
    {
        return getDate("fundGrantDate");
    }
    public void setFundGrantDate(java.util.Date item)
    {
        setDate("fundGrantDate", item);
    }
    /**
     * Object:按揭管理's 公积金律师property 
     */
    public String getFundLawyer()
    {
        return getString("fundLawyer");
    }
    public void setFundLawyer(String item)
    {
        setString("fundLawyer", item);
    }
    /**
     * Object: 按揭管理 's 公积金经办人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getFundTransactor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("fundTransactor");
    }
    public void setFundTransactor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("fundTransactor", item);
    }
    /**
     * Object: 按揭管理 's 按揭资料 property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanDataInfo getLoanData()
    {
        return (com.kingdee.eas.fdc.sellhouse.LoanDataInfo)get("loanData");
    }
    public void setLoanData(com.kingdee.eas.fdc.sellhouse.LoanDataInfo item)
    {
        put("loanData", item);
    }
    /**
     * Object: 按揭管理 's 公积金银行 property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanDataInfo getFundBank()
    {
        return (com.kingdee.eas.fdc.sellhouse.LoanDataInfo)get("fundBank");
    }
    public void setFundBank(com.kingdee.eas.fdc.sellhouse.LoanDataInfo item)
    {
        put("fundBank", item);
    }
    /**
     * Object: 按揭管理 's 公积金/按揭方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysCollection getAFMortgaged()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysCollection)get("aFMortgaged");
    }
    /**
     * Object:按揭管理's 公积金/按揭状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum getAFMortgagedState()
    {
        return com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum.getEnum(getInt("aFMortgagedState"));
    }
    public void setAFMortgagedState(com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum item)
    {
		if (item != null) {
        setInt("aFMortgagedState", item.getValue());
		}
    }
    /**
     * Object:按揭管理's 当前步骤property 
     */
    public String getApproach()
    {
        return getString("approach");
    }
    public void setApproach(String item)
    {
        setString("approach", item);
    }
    /**
     * Object: 按揭管理 's 认购单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo getPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo)get("purchase");
    }
    public void setPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo item)
    {
        put("purchase", item);
    }
    /**
     * Object: 按揭管理 's 款项类别 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMmType()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("mmType");
    }
    public void setMmType(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("mmType", item);
    }
    /**
     * Object: 按揭管理 's 公积金/按揭方案基础资料 property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo getORSOMortgaged()
    {
        return (com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo)get("oRSOMortgaged");
    }
    public void setORSOMortgaged(com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo item)
    {
        put("oRSOMortgaged", item);
    }
    /**
     * Object:按揭管理's 资料是否修改property 
     */
    public boolean isIsEditData()
    {
        return getBoolean("isEditData");
    }
    public void setIsEditData(boolean item)
    {
        setBoolean("isEditData", item);
    }
    /**
     * Object:按揭管理's 申请办理日期property 
     */
    public java.util.Date getApplicationDate()
    {
        return getDate("applicationDate");
    }
    public void setApplicationDate(java.util.Date item)
    {
        setDate("applicationDate", item);
    }
    /**
     * Object:按揭管理's 承诺完成日期property 
     */
    public java.util.Date getPromiseDate()
    {
        return getDate("promiseDate");
    }
    public void setPromiseDate(java.util.Date item)
    {
        setDate("promiseDate", item);
    }
    /**
     * Object: 按揭管理 's 签约单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignManageInfo getSign()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignManageInfo)get("sign");
    }
    public void setSign(com.kingdee.eas.fdc.sellhouse.SignManageInfo item)
    {
        put("sign", item);
    }
    /**
     * Object:按揭管理's 实际完成日期property 
     */
    public java.util.Date getActualFinishDate()
    {
        return getDate("actualFinishDate");
    }
    public void setActualFinishDate(java.util.Date item)
    {
        setDate("actualFinishDate", item);
    }
    /**
     * Object: 按揭管理 's 按揭银行 property 
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
     * Object:按揭管理's 前一个按揭状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum getPreAfmState()
    {
        return com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum.getEnum(getInt("preAfmState"));
    }
    public void setPreAfmState(com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum item)
    {
		if (item != null) {
        setInt("preAfmState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("608A3046");
    }
}