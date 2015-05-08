package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSelfAndFinalEvaluationEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSelfAndFinalEvaluationEntryInfo()
    {
        this("id");
    }
    protected AbstractSelfAndFinalEvaluationEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 自评终评分录 's 关联任务 property 
     */
    public com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo getRelateTask()
    {
        return (com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo)get("relateTask");
    }
    public void setRelateTask(com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo item)
    {
        put("relateTask", item);
    }
    /**
     * Object:自评终评分录's 完成进度(%)property 
     */
    public java.math.BigDecimal getCompletePercent()
    {
        return getBigDecimal("completePercent");
    }
    public void setCompletePercent(java.math.BigDecimal item)
    {
        setBigDecimal("completePercent", item);
    }
    /**
     * Object:自评终评分录's 实际完成日期property 
     */
    public java.util.Date getActualEndDate()
    {
        return getDate("actualEndDate");
    }
    public void setActualEndDate(java.util.Date item)
    {
        setDate("actualEndDate", item);
    }
    /**
     * Object:自评终评分录's 自评得分property 
     */
    public java.math.BigDecimal getSelfEvaluationScore()
    {
        return getBigDecimal("selfEvaluationScore");
    }
    public void setSelfEvaluationScore(java.math.BigDecimal item)
    {
        setBigDecimal("selfEvaluationScore", item);
    }
    /**
     * Object:自评终评分录's 自评的完成情况说明property 
     */
    public String getSelfCompleteDes()
    {
        return getString("selfCompleteDes");
    }
    public void setSelfCompleteDes(String item)
    {
        setString("selfCompleteDes", item);
    }
    /**
     * Object:自评终评分录's 终评得分property 
     */
    public java.math.BigDecimal getFinalEvaluationScore()
    {
        return getBigDecimal("finalEvaluationScore");
    }
    public void setFinalEvaluationScore(java.math.BigDecimal item)
    {
        setBigDecimal("finalEvaluationScore", item);
    }
    /**
     * Object:自评终评分录's 终评完成情况说明property 
     */
    public String getFinalCompleteDes()
    {
        return getString("finalCompleteDes");
    }
    public void setFinalCompleteDes(String item)
    {
        setString("finalCompleteDes", item);
    }
    /**
     * Object: 自评终评分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("69C56035");
    }
}