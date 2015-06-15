package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IEvaluationType extends ITreeBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public EvaluationTypeInfo getEvaluationTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public EvaluationTypeInfo getEvaluationTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public EvaluationTypeInfo getEvaluationTypeInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(EvaluationTypeInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, EvaluationTypeInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, EvaluationTypeInfo model) throws BOSException, EASBizException;
    public void updatePartial(EvaluationTypeInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, EvaluationTypeInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public EvaluationTypeCollection getEvaluationTypeCollection() throws BOSException;
    public EvaluationTypeCollection getEvaluationTypeCollection(EntityViewInfo view) throws BOSException;
    public EvaluationTypeCollection getEvaluationTypeCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public boolean disEnabled(IObjectPK ctPK) throws BOSException, EASBizException;
    public boolean enabled(IObjectPK ctPK) throws BOSException, EASBizException;
}