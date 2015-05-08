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

public interface ITaskEvaluationBill extends IFDCBill
{
    public TaskEvaluationBillInfo getTaskEvaluationBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TaskEvaluationBillInfo getTaskEvaluationBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TaskEvaluationBillInfo getTaskEvaluationBillInfo(String oql) throws BOSException, EASBizException;
    public TaskEvaluationBillCollection getTaskEvaluationBillCollection(String oql) throws BOSException;
    public TaskEvaluationBillCollection getTaskEvaluationBillCollection(EntityViewInfo view) throws BOSException;
    public TaskEvaluationBillCollection getTaskEvaluationBillCollection() throws BOSException;
}