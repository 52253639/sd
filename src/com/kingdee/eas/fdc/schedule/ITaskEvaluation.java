package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.math.BigDecimal;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface ITaskEvaluation extends IFDCDataBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public TaskEvaluationInfo getTaskEvaluationInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TaskEvaluationInfo getTaskEvaluationInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TaskEvaluationInfo getTaskEvaluationInfo(String oql) throws BOSException, EASBizException;
    public TaskEvaluationCollection getTaskEvaluationCollection(String oql) throws BOSException;
    public TaskEvaluationCollection getTaskEvaluationCollection(EntityViewInfo view) throws BOSException;
    public TaskEvaluationCollection getTaskEvaluationCollection() throws BOSException;
    public Set getDataBase(String type) throws BOSException, EASBizException;
    public boolean quoteDelete(String taskId) throws BOSException, EASBizException;
    public void updateSchedule(String srcId, BigDecimal complete, boolean isComplete) throws BOSException, EASBizException;
}