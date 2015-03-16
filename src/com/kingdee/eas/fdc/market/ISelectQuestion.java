package com.kingdee.eas.fdc.market;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ISelectQuestion extends IQuestionBase
{
    public SelectQuestionInfo getSelectQuestionInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SelectQuestionInfo getSelectQuestionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SelectQuestionInfo getSelectQuestionInfo(String oql) throws BOSException, EASBizException;
    public SelectQuestionCollection getSelectQuestionCollection() throws BOSException;
    public SelectQuestionCollection getSelectQuestionCollection(EntityViewInfo view) throws BOSException;
    public SelectQuestionCollection getSelectQuestionCollection(String oql) throws BOSException;
}