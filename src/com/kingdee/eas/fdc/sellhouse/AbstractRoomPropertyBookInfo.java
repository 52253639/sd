package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPropertyBookInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractRoomPropertyBookInfo()
    {
        this("id");
    }
    protected AbstractRoomPropertyBookInfo(String pkField)
    {
        super(pkField);
        put("entryTwo", new com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoCollection());
        put("entry", new com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryCollection());
    }
    /**
     * Object: 产权管理 's 房间 property 
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
     * Object:产权管理's 产权上报日期property 
     */
    public java.util.Date getReportDate()
    {
        return getDate("reportDate");
    }
    public void setReportDate(java.util.Date item)
    {
        setDate("reportDate", item);
    }
    /**
     * Object:产权管理's 产权批准日期property 
     */
    public java.util.Date getApproveDate()
    {
        return getDate("approveDate");
    }
    public void setApproveDate(java.util.Date item)
    {
        setDate("approveDate", item);
    }
    /**
     * Object:产权管理's 产权领取日期property 
     */
    public java.util.Date getDrawDate()
    {
        return getDate("drawDate");
    }
    public void setDrawDate(java.util.Date item)
    {
        setDate("drawDate", item);
    }
    /**
     * Object:产权管理's 产权办理日期property 
     */
    public java.util.Date getTransactDate()
    {
        return getDate("transactDate");
    }
    public void setTransactDate(java.util.Date item)
    {
        setDate("transactDate", item);
    }
    /**
     * Object:产权管理's 产权备案日期property 
     */
    public java.util.Date getRecordDate()
    {
        return getDate("recordDate");
    }
    public void setRecordDate(java.util.Date item)
    {
        setDate("recordDate", item);
    }
    /**
     * Object:产权管理's 产权证编号property 
     */
    public String getPropertyNumber()
    {
        return getString("propertyNumber");
    }
    public void setPropertyNumber(String item)
    {
        setString("propertyNumber", item);
    }
    /**
     * Object:产权管理's 入户指标(已废弃)property 
     */
    public int getRuhuIndex()
    {
        return getInt("ruhuIndex");
    }
    public void setRuhuIndex(int item)
    {
        setInt("ruhuIndex", item);
    }
    /**
     * Object: 产权管理 's 经办人 property 
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
     * Object: 产权管理 's 产权办理方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo getPropertyDoScheme()
    {
        return (com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo)get("propertyDoScheme");
    }
    public void setPropertyDoScheme(com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo item)
    {
        put("propertyDoScheme", item);
    }
    /**
     * Object:产权管理's 产权证状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.PropertyStateEnum getPropertyState()
    {
        return com.kingdee.eas.fdc.sellhouse.PropertyStateEnum.getEnum(getString("propertyState"));
    }
    public void setPropertyState(com.kingdee.eas.fdc.sellhouse.PropertyStateEnum item)
    {
		if (item != null) {
        setString("propertyState", item.getValue());
		}
    }
    /**
     * Object: 产权管理 's 分录一 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryCollection)get("entry");
    }
    /**
     * Object: 产权管理 's 分录二 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoCollection getEntryTwo()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoCollection)get("entryTwo");
    }
    /**
     * Object:产权管理's 当前步骤property 
     */
    public String getStep()
    {
        return getString("step");
    }
    public void setStep(String item)
    {
        setString("step", item);
    }
    /**
     * Object:产权管理's 入户指标（新）property 
     */
    public String getRuhuVarchar()
    {
        return getString("ruhuVarchar");
    }
    public void setRuhuVarchar(String item)
    {
        setString("ruhuVarchar", item);
    }
    /**
     * Object: 产权管理 's 批次 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo getBatch()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo)get("batch");
    }
    public void setBatch(com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo item)
    {
        put("batch", item);
    }
    /**
     * Object:产权管理's 抵押办理日期property 
     */
    public java.util.Date getMortgageDate()
    {
        return getDate("mortgageDate");
    }
    public void setMortgageDate(java.util.Date item)
    {
        setDate("mortgageDate", item);
    }
    /**
     * Object:产权管理's 抵押银行property 
     */
    public String getMortgageBank()
    {
        return getString("mortgageBank");
    }
    public void setMortgageBank(String item)
    {
        setString("mortgageBank", item);
    }
    /**
     * Object: 产权管理 's 批次 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BatchManageInfo getBatchManage()
    {
        return (com.kingdee.eas.fdc.sellhouse.BatchManageInfo)get("batchManage");
    }
    public void setBatchManage(com.kingdee.eas.fdc.sellhouse.BatchManageInfo item)
    {
        put("batchManage", item);
    }
    /**
     * Object: 产权管理 's 签约单 property 
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
     * Object:产权管理's 应完成日期property 
     */
    public java.util.Date getPromiseFinishDate()
    {
        return getDate("promiseFinishDate");
    }
    public void setPromiseFinishDate(java.util.Date item)
    {
        setDate("promiseFinishDate", item);
    }
    /**
     * Object:产权管理's 实际完成日期property 
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
     * Object:产权管理's 产权单据状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum getPropState()
    {
        return com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum.getEnum(getInt("propState"));
    }
    public void setPropState(com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum item)
    {
		if (item != null) {
        setInt("propState", item.getValue());
		}
    }
    /**
     * Object:产权管理's 前一个状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum getPrePropState()
    {
        return com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum.getEnum(getInt("prePropState"));
    }
    public void setPrePropState(com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum item)
    {
		if (item != null) {
        setInt("prePropState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("04BB90B9");
    }
}