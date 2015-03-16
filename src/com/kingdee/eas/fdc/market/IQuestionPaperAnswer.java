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
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IQuestionPaperAnswer extends IBillBase
{
    public QuestionPaperAnswerInfo getQuestionPaperAnswerInfo(IObjectPK pk) throws BOSException, EASBizException;
    public QuestionPaperAnswerInfo getQuestionPaperAnswerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public QuestionPaperAnswerInfo getQuestionPaperAnswerInfo(String oql) throws BOSException, EASBizException;
    public QuestionPaperAnswerCollection getQuestionPaperAnswerCollection() throws BOSException;
    public QuestionPaperAnswerCollection getQuestionPaperAnswerCollection(EntityViewInfo view) throws BOSException;
    public QuestionPaperAnswerCollection getQuestionPaperAnswerCollection(String oql) throws BOSException;
}