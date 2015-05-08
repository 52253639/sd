package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskEvaluationBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractTaskEvaluationBillInfo()
    {
        this("id");
    }
    protected AbstractTaskEvaluationBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:进度任务评价's 评价类型property 
     */
    public com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum getEvaluationType()
    {
        return com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum.getEnum(getString("evaluationType"));
    }
    public void setEvaluationType(com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum item)
    {
		if (item != null) {
        setString("evaluationType", item.getValue());
		}
    }
    /**
     * Object: 进度任务评价 's 评价结果 property 
     */
    public com.kingdee.eas.fdc.schedule.TaskEvaluationInfo getEvaluationResult()
    {
        return (com.kingdee.eas.fdc.schedule.TaskEvaluationInfo)get("evaluationResult");
    }
    public void setEvaluationResult(com.kingdee.eas.fdc.schedule.TaskEvaluationInfo item)
    {
        put("evaluationResult", item);
    }
    /**
     * Object: 进度任务评价 's 关联任务 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getRelateTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("relateTask");
    }
    public void setRelateTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("relateTask", item);
    }
    /**
     * Object:进度任务评价's 评价描述property 
     */
    public String getEvaluationDes()
    {
        return getString("evaluationDes");
    }
    public void setEvaluationDes(String item)
    {
        setString("evaluationDes", item);
    }
    /**
     * Object:进度任务评价's 任务srcIdproperty 
     */
    public String getSrcRelateTask()
    {
        return getString("srcRelateTask");
    }
    public void setSrcRelateTask(String item)
    {
        setString("srcRelateTask", item);
    }
    /**
     * Object: 进度任务评价 's 任务评价人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getEvalucationPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("evalucationPerson");
    }
    public void setEvalucationPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("evalucationPerson", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7629389E");
    }
}