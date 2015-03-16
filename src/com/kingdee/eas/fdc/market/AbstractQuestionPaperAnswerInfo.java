package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuestionPaperAnswerInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractQuestionPaperAnswerInfo()
    {
        this("id");
    }
    protected AbstractQuestionPaperAnswerInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryCollection());
    }
    /**
     * Object: 问卷回答 's 分录 property 
     */
    public com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryCollection)get("entrys");
    }
    /**
     * Object:问卷回答's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("fivouchered", item);
    }
    /**
     * Object: 问卷回答 's 业务员 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPersion()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("persion");
    }
    public void setPersion(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("persion", item);
    }
    /**
     * Object:问卷回答's 问卷填写日期property 
     */
    public java.util.Date getInputDate()
    {
        return getDate("inputDate");
    }
    public void setInputDate(java.util.Date item)
    {
        setDate("inputDate", item);
    }
    /**
     * Object:问卷回答's 会员卡号property 
     */
    public String getInsiderCard()
    {
        return getString("insiderCard");
    }
    public void setInsiderCard(String item)
    {
        setString("insiderCard", item);
    }
    /**
     * Object:问卷回答's 业务员编码property 
     */
    public String getPersionNumber()
    {
        return getString("persionNumber");
    }
    public void setPersionNumber(String item)
    {
        setString("persionNumber", item);
    }
    /**
     * Object: 问卷回答 's 调查问卷 property 
     */
    public com.kingdee.eas.fdc.market.QuestionPaperDefineInfo getQuestionPaper()
    {
        return (com.kingdee.eas.fdc.market.QuestionPaperDefineInfo)get("questionPaper");
    }
    public void setQuestionPaper(com.kingdee.eas.fdc.market.QuestionPaperDefineInfo item)
    {
        put("questionPaper", item);
    }
    /**
     * Object: 问卷回答 's 会员 property 
     */
    public com.kingdee.eas.fdc.insider.InsiderInfo getInsider()
    {
        return (com.kingdee.eas.fdc.insider.InsiderInfo)get("insider");
    }
    public void setInsider(com.kingdee.eas.fdc.insider.InsiderInfo item)
    {
        put("insider", item);
    }
    /**
     * Object: 问卷回答 's 营销项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: 问卷回答 's 客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object: 问卷回答 's 认购单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("purchase");
    }
    public void setPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("purchase", item);
    }
    /**
     * Object: 问卷回答 's 业务主管 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getGoverner()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("governer");
    }
    public void setGoverner(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("governer", item);
    }
    /**
     * Object: 问卷回答 's 商机 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo getCommerceChance()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo)get("commerceChance");
    }
    public void setCommerceChance(com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo item)
    {
        put("commerceChance", item);
    }
    /**
     * Object: 问卷回答 's 客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getQuestionCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("questionCustomer");
    }
    public void setQuestionCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("questionCustomer", item);
    }
    /**
     * Object: 问卷回答 's 售楼客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getSheCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("sheCustomer");
    }
    public void setSheCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("sheCustomer", item);
    }
    /**
     * Object: 问卷回答 's 组织 property 
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
     * Object:问卷回答's nullproperty 
     */
    public String getCluesManage()
    {
        return getString("cluesManage");
    }
    public void setCluesManage(String item)
    {
        setString("cluesManage", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7E608333");
    }
}