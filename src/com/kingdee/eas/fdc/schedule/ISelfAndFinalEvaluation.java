package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ISelfAndFinalEvaluation extends IFDCBill
{
    public SelfAndFinalEvaluationInfo getSelfAndFinalEvaluationInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SelfAndFinalEvaluationInfo getSelfAndFinalEvaluationInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SelfAndFinalEvaluationInfo getSelfAndFinalEvaluationInfo(String oql) throws BOSException, EASBizException;
    public SelfAndFinalEvaluationCollection getSelfAndFinalEvaluationCollection() throws BOSException;
    public SelfAndFinalEvaluationCollection getSelfAndFinalEvaluationCollection(EntityViewInfo view) throws BOSException;
    public SelfAndFinalEvaluationCollection getSelfAndFinalEvaluationCollection(String oql) throws BOSException;
}