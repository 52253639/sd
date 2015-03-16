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
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IQuestionPaperAnswerEntry extends IBillEntryBase
{
    public QuestionPaperAnswerEntryInfo getQuestionPaperAnswerEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public QuestionPaperAnswerEntryInfo getQuestionPaperAnswerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public QuestionPaperAnswerEntryInfo getQuestionPaperAnswerEntryInfo(String oql) throws BOSException, EASBizException;
    public QuestionPaperAnswerEntryCollection getQuestionPaperAnswerEntryCollection() throws BOSException;
    public QuestionPaperAnswerEntryCollection getQuestionPaperAnswerEntryCollection(EntityViewInfo view) throws BOSException;
    public QuestionPaperAnswerEntryCollection getQuestionPaperAnswerEntryCollection(String oql) throws BOSException;
}