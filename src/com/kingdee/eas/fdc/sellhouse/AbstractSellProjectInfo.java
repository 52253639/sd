package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSellProjectInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractSellProjectInfo()
    {
        this("id");
    }
    protected AbstractSellProjectInfo(String pkField)
    {
        super(pkField);
        put("orgUnitShareList", new com.kingdee.eas.fdc.sellhouse.ShareOrgUnitCollection());
        put("subarea", new com.kingdee.eas.fdc.sellhouse.SubareaCollection());
        put("projectArchEntrys", new com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryCollection());
        put("shareRoomModels", new com.kingdee.eas.fdc.sellhouse.ShareRoomModelsCollection());
        put("buildings", new com.kingdee.eas.fdc.sellhouse.BuildingCollection());
    }
    /**
     * Object: 项目资料 's 组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object: 项目资料 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 项目资料 's 分区 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SubareaCollection getSubarea()
    {
        return (com.kingdee.eas.fdc.sellhouse.SubareaCollection)get("subarea");
    }
    /**
     * Object: 项目资料 's 楼栋 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingCollection getBuildings()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingCollection)get("buildings");
    }
    /**
     * Object:项目资料's 地块编号property 
     */
    public String getTerraNumber()
    {
        return getString("terraNumber");
    }
    public void setTerraNumber(String item)
    {
        setString("terraNumber", item);
    }
    /**
     * Object:项目资料's 土地面积property 
     */
    public java.math.BigDecimal getTerraArea()
    {
        return getBigDecimal("terraArea");
    }
    public void setTerraArea(java.math.BigDecimal item)
    {
        setBigDecimal("terraArea", item);
    }
    /**
     * Object: 项目资料 's 建筑性质 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo getBuildingProperty()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo)get("buildingProperty");
    }
    public void setBuildingProperty(com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo item)
    {
        put("buildingProperty", item);
    }
    /**
     * Object:项目资料's 土地许可证property 
     */
    public String getTerraLicence()
    {
        return getString("terraLicence");
    }
    public void setTerraLicence(String item)
    {
        setString("terraLicence", item);
    }
    /**
     * Object:项目资料's 开始期限property 
     */
    public java.util.Date getTermBegin()
    {
        return getDate("termBegin");
    }
    public void setTermBegin(java.util.Date item)
    {
        setDate("termBegin", item);
    }
    /**
     * Object:项目资料's 结束期限property 
     */
    public java.util.Date getTermEnd()
    {
        return getDate("termEnd");
    }
    public void setTermEnd(java.util.Date item)
    {
        setDate("termEnd", item);
    }
    /**
     * Object:项目资料's 土地用途property 
     */
    public String getTerraUse()
    {
        return getString("terraUse");
    }
    public void setTerraUse(String item)
    {
        setString("terraUse", item);
    }
    /**
     * Object:项目资料's 结构性质property 
     */
    public String getFrameProperty()
    {
        return getString("frameProperty");
    }
    public void setFrameProperty(String item)
    {
        setString("frameProperty", item);
    }
    /**
     * Object:项目资料's 容积率property 
     */
    public java.math.BigDecimal getCubageRate()
    {
        return getBigDecimal("cubageRate");
    }
    public void setCubageRate(java.math.BigDecimal item)
    {
        setBigDecimal("cubageRate", item);
    }
    /**
     * Object:项目资料's 房地产证号property 
     */
    public String getRealtyPaperNumber()
    {
        return getString("realtyPaperNumber");
    }
    public void setRealtyPaperNumber(String item)
    {
        setString("realtyPaperNumber", item);
    }
    /**
     * Object:项目资料's 预售许可证property 
     */
    public String getPresellLicence()
    {
        return getString("presellLicence");
    }
    public void setPresellLicence(String item)
    {
        setString("presellLicence", item);
    }
    /**
     * Object:项目资料's 外销许可证property 
     */
    public String getExportLicence()
    {
        return getString("exportLicence");
    }
    public void setExportLicence(String item)
    {
        setString("exportLicence", item);
    }
    /**
     * Object:项目资料's 项目地址property 
     */
    public String getProAddress()
    {
        return getString("proAddress");
    }
    public void setProAddress(String item)
    {
        setString("proAddress", item);
    }
    /**
     * Object:项目资料's 周边环境信息property 
     */
    public String getSideEntironment()
    {
        return getString("sideEntironment");
    }
    public void setSideEntironment(String item)
    {
        setString("sideEntironment", item);
    }
    /**
     * Object:项目资料's 售楼属性property 
     */
    public boolean isIsForSHE()
    {
        return getBoolean("isForSHE");
    }
    public void setIsForSHE(boolean item)
    {
        setBoolean("isForSHE", item);
    }
    /**
     * Object:项目资料's 租赁属性property 
     */
    public boolean isIsForTen()
    {
        return getBoolean("isForTen");
    }
    public void setIsForTen(boolean item)
    {
        setBoolean("isForTen", item);
    }
    /**
     * Object:项目资料's 物业属性property 
     */
    public boolean isIsForPPM()
    {
        return getBoolean("isForPPM");
    }
    public void setIsForPPM(boolean item)
    {
        setBoolean("isForPPM", item);
    }
    /**
     * Object: 项目资料 's 共享组织分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareOrgUnitCollection getOrgUnitShareList()
    {
        return (com.kingdee.eas.fdc.sellhouse.ShareOrgUnitCollection)get("orgUnitShareList");
    }
    /**
     * Object:项目资料's 项目来源property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectResourceEnum getProjectResource()
    {
        return com.kingdee.eas.fdc.sellhouse.SellProjectResourceEnum.getEnum(getString("projectResource"));
    }
    public void setProjectResource(com.kingdee.eas.fdc.sellhouse.SellProjectResourceEnum item)
    {
		if (item != null) {
        setString("projectResource", item.getValue());
		}
    }
    /**
     * Object: 项目资料 's 投资房源 property 
     */
    public com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo getInvestorHouse()
    {
        return (com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo)get("investorHouse");
    }
    public void setInvestorHouse(com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo item)
    {
        put("investorHouse", item);
    }
    /**
     * Object: 项目资料 's 项目档案分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryCollection getProjectArchEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryCollection)get("projectArchEntrys");
    }
    /**
     * Object:项目资料's 结算截止日property 
     */
    public java.util.Date getBalanceEndDate()
    {
        return getDate("balanceEndDate");
    }
    public void setBalanceEndDate(java.util.Date item)
    {
        setDate("balanceEndDate", item);
    }
    /**
     * Object:项目资料's 是否结算初始化property 
     */
    public boolean isIsEndInit()
    {
        return getBoolean("isEndInit");
    }
    public void setIsEndInit(boolean item)
    {
        setBoolean("isEndInit", item);
    }
    /**
     * Object:项目资料's 月结启用期间property 
     */
    public String getSaleTerm()
    {
        return getString("saleTerm");
    }
    public void setSaleTerm(String item)
    {
        setString("saleTerm", item);
    }
    /**
     * Object:项目资料's 月结当前期间property 
     */
    public String getSaleNowTerm()
    {
        return getString("saleNowTerm");
    }
    public void setSaleNowTerm(String item)
    {
        setString("saleNowTerm", item);
    }
    /**
     * Object: 项目资料 's 销售项目结束初始化的处理人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getDisposePerson()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("disposePerson");
    }
    public void setDisposePerson(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("disposePerson", item);
    }
    /**
     * Object:项目资料's 处理日期property 
     */
    public java.util.Date getDisposeDate()
    {
        return getDate("disposeDate");
    }
    public void setDisposeDate(java.util.Date item)
    {
        setDate("disposeDate", item);
    }
    /**
     * Object: 项目资料 's 启用期间 property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getSalePeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("salePeriod");
    }
    public void setSalePeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("salePeriod", item);
    }
    /**
     * Object: 项目资料 's 当前期间 property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getSaleNowPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("saleNowPeriod");
    }
    public void setSaleNowPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("saleNowPeriod", item);
    }
    /**
     * Object: 项目资料 's 公司 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompanyOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("companyOrgUnit");
    }
    public void setCompanyOrgUnit(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("companyOrgUnit", item);
    }
    /**
     * Object: 项目资料 's 上级项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:项目资料's 是否被使用property 
     */
    public boolean isIsUse()
    {
        return getBoolean("isUse");
    }
    public void setIsUse(boolean item)
    {
        setBoolean("isUse", item);
    }
    /**
     * Object: 项目资料 's 户型列表 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareRoomModelsCollection getShareRoomModels()
    {
        return (com.kingdee.eas.fdc.sellhouse.ShareRoomModelsCollection)get("shareRoomModels");
    }
    /**
     * Object:项目资料's 引入项目IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getImportID()
    {
        return getBOSUuid("importID");
    }
    public void setImportID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("importID", item);
    }
    /**
     * Object: 项目资料 's 主数据项目信息 property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectBaseInfo getProjectBase()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectBaseInfo)get("projectBase");
    }
    public void setProjectBase(com.kingdee.eas.fdc.basedata.ProjectBaseInfo item)
    {
        put("projectBase", item);
    }
    /**
     * Object:项目资料's 竣工备案日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2FFBE5AC");
    }
}