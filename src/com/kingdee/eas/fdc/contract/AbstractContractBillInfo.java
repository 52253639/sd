package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractBillInfo()
    {
        this("id");
    }
    protected AbstractContractBillInfo(String pkField)
    {
        super(pkField);
        put("otherLandDevelpers", new com.kingdee.eas.fdc.contract.ContractLandDeveloperCollection());
        put("contractPlan", new com.kingdee.eas.fdc.contract.ContractBillContractPlanCollection());
        put("entrys", new com.kingdee.eas.fdc.contract.ContractBillEntryCollection());
        put("payItems", new com.kingdee.eas.fdc.contract.ContractPayItemCollection());
    }
    /**
     * Object: 合同单据 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillEntryCollection)get("entrys");
    }
    /**
     * Object:合同单据's 变更提示比例property 
     */
    public java.math.BigDecimal getChgPercForWarn()
    {
        return getBigDecimal("chgPercForWarn");
    }
    public void setChgPercForWarn(java.math.BigDecimal item)
    {
        setBigDecimal("chgPercForWarn", item);
    }
    /**
     * Object:合同单据's 付款提示比例property 
     */
    public java.math.BigDecimal getPayPercForWarn()
    {
        return getBigDecimal("payPercForWarn");
    }
    public void setPayPercForWarn(java.math.BigDecimal item)
    {
        setBigDecimal("payPercForWarn", item);
    }
    /**
     * Object:合同单据's 签约日期property 
     */
    public java.util.Date getSignDate()
    {
        return getDate("signDate");
    }
    public void setSignDate(java.util.Date item)
    {
        setDate("signDate", item);
    }
    /**
     * Object:合同单据's 最低投标价property 
     */
    public java.math.BigDecimal getLowestPrice()
    {
        return getBigDecimal("lowestPrice");
    }
    public void setLowestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("lowestPrice", item);
    }
    /**
     * Object:合同单据's 次低投标价property 
     */
    public java.math.BigDecimal getLowerPrice()
    {
        return getBigDecimal("lowerPrice");
    }
    public void setLowerPrice(java.math.BigDecimal item)
    {
        setBigDecimal("lowerPrice", item);
    }
    /**
     * Object:合同单据's 中间投标价property 
     */
    public java.math.BigDecimal getMiddlePrice()
    {
        return getBigDecimal("middlePrice");
    }
    public void setMiddlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("middlePrice", item);
    }
    /**
     * Object:合同单据's 次高投标价property 
     */
    public java.math.BigDecimal getHigherPrice()
    {
        return getBigDecimal("higherPrice");
    }
    public void setHigherPrice(java.math.BigDecimal item)
    {
        setBigDecimal("higherPrice", item);
    }
    /**
     * Object:合同单据's 最高投标价property 
     */
    public java.math.BigDecimal getHighestPrice()
    {
        return getBigDecimal("highestPrice");
    }
    public void setHighestPrice(java.math.BigDecimal item)
    {
        setBigDecimal("highestPrice", item);
    }
    /**
     * Object:合同单据's 中标价property 
     */
    public java.math.BigDecimal getWinPrice()
    {
        return getBigDecimal("winPrice");
    }
    public void setWinPrice(java.math.BigDecimal item)
    {
        setBigDecimal("winPrice", item);
    }
    /**
     * Object:合同单据's 单位数量property 
     */
    public java.math.BigDecimal getQuantity()
    {
        return getBigDecimal("quantity");
    }
    public void setQuantity(java.math.BigDecimal item)
    {
        setBigDecimal("quantity", item);
    }
    /**
     * Object:合同单据's 招标文件号property 
     */
    public String getFileNo()
    {
        return getString("fileNo");
    }
    public void setFileNo(String item)
    {
        setString("fileNo", item);
    }
    /**
     * Object:合同单据's 底价property 
     */
    public java.math.BigDecimal getBasePrice()
    {
        return getBigDecimal("basePrice");
    }
    public void setBasePrice(java.math.BigDecimal item)
    {
        setBigDecimal("basePrice", item);
    }
    /**
     * Object:合同单据's 二标价property 
     */
    public java.math.BigDecimal getSecondPrice()
    {
        return getBigDecimal("secondPrice");
    }
    public void setSecondPrice(java.math.BigDecimal item)
    {
        setBigDecimal("secondPrice", item);
    }
    /**
     * Object: 合同单据 's 甲方 property 
     */
    public com.kingdee.eas.fdc.basedata.LandDeveloperInfo getLandDeveloper()
    {
        return (com.kingdee.eas.fdc.basedata.LandDeveloperInfo)get("landDeveloper");
    }
    public void setLandDeveloper(com.kingdee.eas.fdc.basedata.LandDeveloperInfo item)
    {
        put("landDeveloper", item);
    }
    /**
     * Object: 合同单据 's 其他甲方 property 
     */
    public com.kingdee.eas.fdc.contract.ContractLandDeveloperCollection getOtherLandDevelpers()
    {
        return (com.kingdee.eas.fdc.contract.ContractLandDeveloperCollection)get("otherLandDevelpers");
    }
    /**
     * Object: 合同单据 's 合同类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    /**
     * Object: 合同单据 's 招标类型 property 
     */
    public com.kingdee.eas.fdc.invite.InviteTypeInfo getInviteType()
    {
        return (com.kingdee.eas.fdc.invite.InviteTypeInfo)get("inviteType");
    }
    public void setInviteType(com.kingdee.eas.fdc.invite.InviteTypeInfo item)
    {
        put("inviteType", item);
    }
    /**
     * Object: 合同单据 's 最低投标价单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getLowestPriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("lowestPriceUnit");
    }
    public void setLowestPriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("lowestPriceUnit", item);
    }
    /**
     * Object: 合同单据 's 中间投标价单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getMiddlePriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("middlePriceUnit");
    }
    public void setMiddlePriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("middlePriceUnit", item);
    }
    /**
     * Object: 合同单据 's 次低投标价单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getLowerPriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("lowerPriceUnit");
    }
    public void setLowerPriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("lowerPriceUnit", item);
    }
    /**
     * Object: 合同单据 's 次高投标价单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getHigherPriceUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("higherPriceUnit");
    }
    public void setHigherPriceUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("higherPriceUnit", item);
    }
    /**
     * Object: 合同单据 's 最高投标价单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getHighestPriceUni()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("highestPriceUni");
    }
    public void setHighestPriceUni(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("highestPriceUni", item);
    }
    /**
     * Object: 合同单据 's 中标单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getWinUnit()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("winUnit");
    }
    public void setWinUnit(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("winUnit", item);
    }
    /**
     * Object:合同单据's 造价性质property 
     */
    public com.kingdee.eas.fdc.contract.CostPropertyEnum getCostProperty()
    {
        return com.kingdee.eas.fdc.contract.CostPropertyEnum.getEnum(getString("costProperty"));
    }
    public void setCostProperty(com.kingdee.eas.fdc.contract.CostPropertyEnum item)
    {
		if (item != null) {
        setString("costProperty", item.getValue());
		}
    }
    /**
     * Object:合同单据's 合同性质property 
     */
    public com.kingdee.eas.fdc.contract.ContractPropertyEnum getContractPropert()
    {
        return com.kingdee.eas.fdc.contract.ContractPropertyEnum.getEnum(getString("contractPropert"));
    }
    public void setContractPropert(com.kingdee.eas.fdc.contract.ContractPropertyEnum item)
    {
		if (item != null) {
        setString("contractPropert", item.getValue());
		}
    }
    /**
     * Object:合同单据's 形成方式property 
     */
    public com.kingdee.eas.fdc.contract.ContractSourceEnum getContractSource()
    {
        return com.kingdee.eas.fdc.contract.ContractSourceEnum.getEnum(getString("contractSource"));
    }
    public void setContractSource(com.kingdee.eas.fdc.contract.ContractSourceEnum item)
    {
		if (item != null) {
        setString("contractSource", item.getValue());
		}
    }
    /**
     * Object: 合同单据 's 乙方 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getPartB()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("partB");
    }
    public void setPartB(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("partB", item);
    }
    /**
     * Object: 合同单据 's 丙方 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getPartC()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("partC");
    }
    public void setPartC(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("partC", item);
    }
    /**
     * Object: 合同单据 's 合同计划 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillContractPlanCollection getContractPlan()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillContractPlanCollection)get("contractPlan");
    }
    /**
     * Object: 合同单据 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object:合同单据's 是否成本拆分property 
     */
    public boolean isIsCoseSplit()
    {
        return getBoolean("isCoseSplit");
    }
    public void setIsCoseSplit(boolean item)
    {
        setBoolean("isCoseSplit", item);
    }
    /**
     * Object:合同单据's 是否已结算property 
     */
    public boolean isHasSettled()
    {
        return getBoolean("hasSettled");
    }
    public void setHasSettled(boolean item)
    {
        setBoolean("hasSettled", item);
    }
    /**
     * Object:合同单据's 合同内工程款累计实付额property 
     */
    public java.math.BigDecimal getPrjPriceInConPaid()
    {
        return getBigDecimal("prjPriceInConPaid");
    }
    public void setPrjPriceInConPaid(java.math.BigDecimal item)
    {
        setBigDecimal("prjPriceInConPaid", item);
    }
    /**
     * Object:合同单据's 增加工程款累计实付property 
     */
    public java.math.BigDecimal getAddPrjAmtPaid()
    {
        return getBigDecimal("addPrjAmtPaid");
    }
    public void setAddPrjAmtPaid(java.math.BigDecimal item)
    {
        setBigDecimal("addPrjAmtPaid", item);
    }
    /**
     * Object:合同单据's 应付甲供材料累计实付款property 
     */
    public java.math.BigDecimal getPaidPartAMatlAmt()
    {
        return getBigDecimal("paidPartAMatlAmt");
    }
    public void setPaidPartAMatlAmt(java.math.BigDecimal item)
    {
        setBigDecimal("paidPartAMatlAmt", item);
    }
    /**
     * Object:合同单据's 结算价property 
     */
    public java.math.BigDecimal getSettleAmt()
    {
        return getBigDecimal("settleAmt");
    }
    public void setSettleAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settleAmt", item);
    }
    /**
     * Object: 合同单据 's 币别 property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:合同单据's 保修金额property 
     */
    public java.math.BigDecimal getGrtAmount()
    {
        return getBigDecimal("grtAmount");
    }
    public void setGrtAmount(java.math.BigDecimal item)
    {
        setBigDecimal("grtAmount", item);
    }
    /**
     * Object:合同单据's 保修本位币金额property 
     */
    public java.math.BigDecimal getGrtLocalAmount()
    {
        return getBigDecimal("grtLocalAmount");
    }
    public void setGrtLocalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("grtLocalAmount", item);
    }
    /**
     * Object:合同单据's 汇率property 
     */
    public java.math.BigDecimal getExRate()
    {
        return getBigDecimal("exRate");
    }
    public void setExRate(java.math.BigDecimal item)
    {
        setBigDecimal("exRate", item);
    }
    /**
     * Object:合同单据's 保修金比例property 
     */
    public java.math.BigDecimal getGrtRate()
    {
        return getBigDecimal("grtRate");
    }
    public void setGrtRate(java.math.BigDecimal item)
    {
        setBigDecimal("grtRate", item);
    }
    /**
     * Object:合同单据's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:合同单据's 拆分状态property 
     */
    public com.kingdee.eas.fdc.basedata.CostSplitStateEnum getSplitState()
    {
        return com.kingdee.eas.fdc.basedata.CostSplitStateEnum.getEnum(getString("splitState"));
    }
    public void setSplitState(com.kingdee.eas.fdc.basedata.CostSplitStateEnum item)
    {
		if (item != null) {
        setString("splitState", item.getValue());
		}
    }
    /**
     * Object: 合同单据 's 责任部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getRespDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("respDept");
    }
    public void setRespDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("respDept", item);
    }
    /**
     * Object:合同单据's 付款比例property 
     */
    public java.math.BigDecimal getPayScale()
    {
        return getBigDecimal("payScale");
    }
    public void setPayScale(java.math.BigDecimal item)
    {
        setBigDecimal("payScale", item);
    }
    /**
     * Object:合同单据's 是否不计成本的金额property 
     */
    public boolean isIsAmtWithoutCost()
    {
        return getBoolean("isAmtWithoutCost");
    }
    public void setIsAmtWithoutCost(boolean item)
    {
        setBoolean("isAmtWithoutCost", item);
    }
    /**
     * Object:合同单据's 印花税率property 
     */
    public java.math.BigDecimal getStampTaxRate()
    {
        return getBigDecimal("stampTaxRate");
    }
    public void setStampTaxRate(java.math.BigDecimal item)
    {
        setBigDecimal("stampTaxRate", item);
    }
    /**
     * Object:合同单据's 印花税金额property 
     */
    public java.math.BigDecimal getStampTaxAmt()
    {
        return getBigDecimal("stampTaxAmt");
    }
    public void setStampTaxAmt(java.math.BigDecimal item)
    {
        setBigDecimal("stampTaxAmt", item);
    }
    /**
     * Object: 合同单据 's 责任人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRespPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("respPerson");
    }
    public void setRespPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("respPerson", item);
    }
    /**
     * Object: 合同单据 's 编码类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo getCodeType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo)get("codeType");
    }
    public void setCodeType(com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo item)
    {
        put("codeType", item);
    }
    /**
     * Object:合同单据's 主合同编码property 
     */
    public String getMainContractNumber()
    {
        return getString("mainContractNumber");
    }
    public void setMainContractNumber(String item)
    {
        setString("mainContractNumber", item);
    }
    /**
     * Object:合同单据's 是否已归档property 
     */
    public boolean isIsArchived()
    {
        return getBoolean("isArchived");
    }
    public void setIsArchived(boolean item)
    {
        setBoolean("isArchived", item);
    }
    /**
     * Object:合同单据's 执行状态property 
     */
    public com.kingdee.eas.fdc.contract.ContractExecStateEnum getExecState()
    {
        return com.kingdee.eas.fdc.contract.ContractExecStateEnum.getEnum(getString("execState"));
    }
    public void setExecState(com.kingdee.eas.fdc.contract.ContractExecStateEnum item)
    {
		if (item != null) {
        setString("execState", item.getValue());
		}
    }
    /**
     * Object:合同单据's 战略合作级别property 
     */
    public com.kingdee.eas.fdc.contract.CoopLevelEnum getCoopLevel()
    {
        return com.kingdee.eas.fdc.contract.CoopLevelEnum.getEnum(getString("coopLevel"));
    }
    public void setCoopLevel(com.kingdee.eas.fdc.contract.CoopLevelEnum item)
    {
		if (item != null) {
        setString("coopLevel", item.getValue());
		}
    }
    /**
     * Object:合同单据's 计价方式property 
     */
    public com.kingdee.eas.fdc.contract.PriceTypeEnum getPriceType()
    {
        return com.kingdee.eas.fdc.contract.PriceTypeEnum.getEnum(getString("priceType"));
    }
    public void setPriceType(com.kingdee.eas.fdc.contract.PriceTypeEnum item)
    {
		if (item != null) {
        setString("priceType", item.getValue());
		}
    }
    /**
     * Object:合同单据's 待处理状态property 
     */
    public com.kingdee.eas.fdc.contract.ConSplitExecStateEnum getConSplitExecState()
    {
        return com.kingdee.eas.fdc.contract.ConSplitExecStateEnum.getEnum(getString("conSplitExecState"));
    }
    public void setConSplitExecState(com.kingdee.eas.fdc.contract.ConSplitExecStateEnum item)
    {
		if (item != null) {
        setString("conSplitExecState", item.getValue());
		}
    }
    /**
     * Object:合同单据's 外部单据号码property 
     */
    public String getWebSrvNumber()
    {
        return getString("webSrvNumber");
    }
    public void setWebSrvNumber(String item)
    {
        setString("webSrvNumber", item);
    }
    /**
     * Object:合同单据's 单据来源方式property 
     */
    public com.kingdee.eas.fdc.basedata.SourceTypeEnum getSourceType()
    {
        return com.kingdee.eas.fdc.basedata.SourceTypeEnum.getEnum(getInt("sourceType"));
    }
    public void setSourceType(com.kingdee.eas.fdc.basedata.SourceTypeEnum item)
    {
		if (item != null) {
        setInt("sourceType", item.getValue());
		}
    }
    /**
     * Object:合同单据's 归档前编码property 
     */
    public String getArchivingNumber()
    {
        return getString("archivingNumber");
    }
    public void setArchivingNumber(String item)
    {
        setString("archivingNumber", item);
    }
    /**
     * Object:合同单据's 是否甲材料合同property 
     */
    public boolean isIsPartAMaterialCon()
    {
        return getBoolean("isPartAMaterialCon");
    }
    public void setIsPartAMaterialCon(boolean item)
    {
        setBoolean("isPartAMaterialCon", item);
    }
    /**
     * Object: 合同单据 's 合同费用项目 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo getConChargeType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo)get("conChargeType");
    }
    public void setConChargeType(com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo item)
    {
        put("conChargeType", item);
    }
    /**
     * Object:合同单据's 是否量价拆分property 
     */
    public boolean isIsMeasureContract()
    {
        return getBoolean("isMeasureContract");
    }
    public void setIsMeasureContract(boolean item)
    {
        setBoolean("isMeasureContract", item);
    }
    /**
     * Object: 合同单据 's 形成方式 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractSourceInfo getContractSourceId()
    {
        return (com.kingdee.eas.fdc.basedata.ContractSourceInfo)get("contractSourceId");
    }
    public void setContractSourceId(com.kingdee.eas.fdc.basedata.ContractSourceInfo item)
    {
        put("contractSourceId", item);
    }
    /**
     * Object:合同单据's 不计成本的金额property 
     */
    public java.math.BigDecimal getAmtWithoutCost()
    {
        return getBigDecimal("amtWithoutCost");
    }
    public void setAmtWithoutCost(java.math.BigDecimal item)
    {
        setBigDecimal("amtWithoutCost", item);
    }
    /**
     * Object: 合同单据 's 合同基础资料 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBaseDataInfo getContractBaseData()
    {
        return (com.kingdee.eas.fdc.contract.ContractBaseDataInfo)get("contractBaseData");
    }
    public void setContractBaseData(com.kingdee.eas.fdc.contract.ContractBaseDataInfo item)
    {
        put("contractBaseData", item);
    }
    /**
     * Object:合同单据's 合同编码property 
     */
    public String getCodingNumber()
    {
        return getString("codingNumber");
    }
    public void setCodingNumber(String item)
    {
        setString("codingNumber", item);
    }
    /**
     * Object: 合同单据 's 付款事项 property 
     */
    public com.kingdee.eas.fdc.contract.ContractPayItemCollection getPayItems()
    {
        return (com.kingdee.eas.fdc.contract.ContractPayItemCollection)get("payItems");
    }
    /**
     * Object: 合同单据 's 履约保证金及返还部分 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBailInfo getBail()
    {
        return (com.kingdee.eas.fdc.contract.ContractBailInfo)get("bail");
    }
    public void setBail(com.kingdee.eas.fdc.contract.ContractBailInfo item)
    {
        put("bail", item);
    }
    /**
     * Object:合同单据's 超过结算提示比例property 
     */
    public double getOverRate()
    {
        return getDouble("overRate");
    }
    public void setOverRate(double item)
    {
        setDouble("overRate", item);
    }
    /**
     * Object:合同单据's 归档合同编号property 
     */
    public String getArchivedNumber()
    {
        return getString("archivedNumber");
    }
    public void setArchivedNumber(String item)
    {
        setString("archivedNumber", item);
    }
    /**
     * Object:合同单据's 是否战略子合同property 
     */
    public boolean isIsSubContract()
    {
        return getBoolean("isSubContract");
    }
    public void setIsSubContract(boolean item)
    {
        setBoolean("isSubContract", item);
    }
    /**
     * Object:合同单据's 有效起始日期property 
     */
    public java.util.Date getEffectiveStartDate()
    {
        return getDate("effectiveStartDate");
    }
    public void setEffectiveStartDate(java.util.Date item)
    {
        setDate("effectiveStartDate", item);
    }
    /**
     * Object:合同单据's 有效截至日期property 
     */
    public java.util.Date getEffectiveEndDate()
    {
        return getDate("effectiveEndDate");
    }
    public void setEffectiveEndDate(java.util.Date item)
    {
        setDate("effectiveEndDate", item);
    }
    /**
     * Object: 合同单据 's 战略主合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getMainContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("mainContract");
    }
    public void setMainContract(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("mainContract", item);
    }
    /**
     * Object:合同单据's 合同摘要信息property 
     */
    public String getInformation()
    {
        return getString("information");
    }
    public void setInformation(String item)
    {
        setString("information", item);
    }
    /**
     * Object: 合同单据 's 合约规划 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getProgrammingContract()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("programmingContract");
    }
    public void setProgrammingContract(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("programmingContract", item);
    }
    /**
     * Object:合同单据's 是否从历史合同关联框架合约property 
     */
    public int getIsRenewRelateProg()
    {
        return getInt("isRenewRelateProg");
    }
    public void setIsRenewRelateProg(int item)
    {
        setInt("isRenewRelateProg", item);
    }
    /**
     * Object:合同单据's 源框架合约IDproperty 
     */
    public String getSrcProID()
    {
        return getString("srcProID");
    }
    public void setSrcProID(String item)
    {
        setString("srcProID", item);
    }
    /**
     * Object: 合同单据 's 合同范本 property 
     */
    public com.kingdee.eas.base.attachment.AttachmentInfo getModel()
    {
        return (com.kingdee.eas.base.attachment.AttachmentInfo)get("model");
    }
    public void setModel(com.kingdee.eas.base.attachment.AttachmentInfo item)
    {
        put("model", item);
    }
    /**
     * Object: 合同单据 's 制单人职位 property 
     */
    public com.kingdee.eas.basedata.org.PositionInfo getCreatorPosition()
    {
        return (com.kingdee.eas.basedata.org.PositionInfo)get("creatorPosition");
    }
    public void setCreatorPosition(com.kingdee.eas.basedata.org.PositionInfo item)
    {
        put("creatorPosition", item);
    }
    /**
     * Object: 合同单据 's 制单部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getCreateDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("createDept");
    }
    public void setCreateDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("createDept", item);
    }
    /**
     * Object:合同单据's 是否标准合同property 
     */
    public boolean isIsStardContract()
    {
        return getBoolean("isStardContract");
    }
    public void setIsStardContract(boolean item)
    {
        setBoolean("isStardContract", item);
    }
    /**
     * Object:合同单据's 是否闭口合同property 
     */
    public boolean isIsOpenContract()
    {
        return getBoolean("isOpenContract");
    }
    public void setIsOpenContract(boolean item)
    {
        setBoolean("isOpenContract", item);
    }
    /**
     * Object:合同单据's 合同结算类型property 
     */
    public com.kingdee.eas.fdc.contract.ContractSettleTypeEnum getContractSettleType()
    {
        return com.kingdee.eas.fdc.contract.ContractSettleTypeEnum.getEnum(getString("contractSettleType"));
    }
    public void setContractSettleType(com.kingdee.eas.fdc.contract.ContractSettleTypeEnum item)
    {
		if (item != null) {
        setString("contractSettleType", item.getValue());
		}
    }
    /**
     * Object:合同单据's 合同范本property 
     */
    public String getContractModel()
    {
        return getString("contractModel");
    }
    public void setContractModel(String item)
    {
        setString("contractModel", item);
    }
    /**
     * Object:合同单据's 补充协议关联业务IDproperty 
     */
    public String getAgreementID()
    {
        return getString("agreementID");
    }
    public void setAgreementID(String item)
    {
        setString("agreementID", item);
    }
    /**
     * Object:合同单据's 审批流程发起组织property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum getOrgType()
    {
        return com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum.getEnum(getString("orgType"));
    }
    public void setOrgType(com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum item)
    {
		if (item != null) {
        setString("orgType", item.getValue());
		}
    }
    /**
     * Object: 合同单据 's 合同流程类型 property 
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
     * Object:合同单据's 原主合同金额property 
     */
    public java.math.BigDecimal getSrcAmount()
    {
        return getBigDecimal("srcAmount");
    }
    public void setSrcAmount(java.math.BigDecimal item)
    {
        setBigDecimal("srcAmount", item);
    }
    /**
     * Object:合同单据's 是否录入造价指标库property 
     */
    public boolean isIsHasCostIndex()
    {
        return getBoolean("isHasCostIndex");
    }
    public void setIsHasCostIndex(boolean item)
    {
        setBoolean("isHasCostIndex", item);
    }
    /**
     * Object: 合同单据 's 需求部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getNeedDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("needDept");
    }
    public void setNeedDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("needDept", item);
    }
    /**
     * Object: 合同单据 's 需求人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getNeedPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("needPerson");
    }
    public void setNeedPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("needPerson", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0D6DD1F4");
    }
}