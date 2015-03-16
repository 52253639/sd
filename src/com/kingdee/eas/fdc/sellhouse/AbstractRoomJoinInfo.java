package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomJoinInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractRoomJoinInfo()
    {
        this("id");
    }
    protected AbstractRoomJoinInfo(String pkField)
    {
        super(pkField);
        put("approachEntry", new com.kingdee.eas.fdc.sellhouse.RoomJoinApproachEntryCollection());
        put("dataEntry", new com.kingdee.eas.fdc.sellhouse.RoomJoinDataEntryCollection());
        put("sharePPMList", new com.kingdee.eas.fdc.sellhouse.SharePPMCollection());
    }
    /**
     * Object: 入伙管理 's 房间 property 
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
     * Object:入伙管理's 办理入伙日期property 
     */
    public java.util.Date getJoinDate()
    {
        return getDate("joinDate");
    }
    public void setJoinDate(java.util.Date item)
    {
        setDate("joinDate", item);
    }
    /**
     * Object:入伙管理's 办理开始时间property 
     */
    public java.sql.Time getJoinStartDate()
    {
        return getTime("joinStartDate");
    }
    public void setJoinStartDate(java.sql.Time item)
    {
        setTime("joinStartDate", item);
    }
    /**
     * Object:入伙管理's 办理结束时间property 
     */
    public java.sql.Time getJoinEndDate()
    {
        return getTime("joinEndDate");
    }
    public void setJoinEndDate(java.sql.Time item)
    {
        setTime("joinEndDate", item);
    }
    /**
     * Object:入伙管理's 约定入伙日期property 
     */
    public java.util.Date getApptJoinDate()
    {
        return getDate("apptJoinDate");
    }
    public void setApptJoinDate(java.util.Date item)
    {
        setDate("apptJoinDate", item);
    }
    /**
     * Object:入伙管理's 计费日期property 
     */
    public java.util.Date getCalcFeeDate()
    {
        return getDate("calcFeeDate");
    }
    public void setCalcFeeDate(java.util.Date item)
    {
        setDate("calcFeeDate", item);
    }
    /**
     * Object:入伙管理's 竣工日期property 
     */
    public java.util.Date getFinishDate()
    {
        return getDate("finishDate");
    }
    public void setFinishDate(java.util.Date item)
    {
        setDate("finishDate", item);
    }
    /**
     * Object: 入伙管理 's 经办人 property 
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
     * Object:入伙管理's 客户共享方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareModelEnum getJoinShareModel()
    {
        return com.kingdee.eas.fdc.sellhouse.ShareModelEnum.getEnum(getString("joinShareModel"));
    }
    public void setJoinShareModel(com.kingdee.eas.fdc.sellhouse.ShareModelEnum item)
    {
		if (item != null) {
        setString("joinShareModel", item.getValue());
		}
    }
    /**
     * Object: 入伙管理 's 共享物业分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SharePPMCollection getSharePPMList()
    {
        return (com.kingdee.eas.fdc.sellhouse.SharePPMCollection)get("sharePPMList");
    }
    /**
     * Object:入伙管理's 当前进程property 
     */
    public String getCurrentApproach()
    {
        return getString("currentApproach");
    }
    public void setCurrentApproach(String item)
    {
        setString("currentApproach", item);
    }
    /**
     * Object: 入伙管理 's 办理批次 property 
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
     * Object: 入伙管理 's 签约单 property 
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
     * Object: 入伙管理 's 办理进程 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomJoinApproachEntryCollection getApproachEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomJoinApproachEntryCollection)get("approachEntry");
    }
    /**
     * Object: 入伙管理 's 办理资料 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomJoinDataEntryCollection getDataEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomJoinDataEntryCollection)get("dataEntry");
    }
    /**
     * Object: 入伙管理 's 入伙方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo getRoomJoinDoScheme()
    {
        return (com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo)get("roomJoinDoScheme");
    }
    public void setRoomJoinDoScheme(com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo item)
    {
        put("roomJoinDoScheme", item);
    }
    /**
     * Object:入伙管理's 应完成日期property 
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
     * Object:入伙管理's 实际完成日期property 
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
     * Object:入伙管理's 入伙办理状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum getJoinState()
    {
        return com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum.getEnum(getInt("joinState"));
    }
    public void setJoinState(com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum item)
    {
		if (item != null) {
        setInt("joinState", item.getValue());
		}
    }
    /**
     * Object:入伙管理's 前一个状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum getPreJoinState()
    {
        return com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum.getEnum(getInt("preJoinState"));
    }
    public void setPreJoinState(com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum item)
    {
		if (item != null) {
        setInt("preJoinState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("60894880");
    }
}