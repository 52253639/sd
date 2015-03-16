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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IQuestionType extends ITreeBase
{
    public QuestionTypeInfo getQuestionTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public QuestionTypeInfo getQuestionTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public QuestionTypeInfo getQuestionTypeInfo(String oql) throws BOSException, EASBizException;
    public QuestionTypeCollection getQuestionTypeCollection() throws BOSException;
    public QuestionTypeCollection getQuestionTypeCollection(EntityViewInfo view) throws BOSException;
    public QuestionTypeCollection getQuestionTypeCollection(String oql) throws BOSException;
}