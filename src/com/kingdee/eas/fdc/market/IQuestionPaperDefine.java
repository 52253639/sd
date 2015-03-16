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

public interface IQuestionPaperDefine extends IBillBase
{
    public QuestionPaperDefineInfo getQuestionPaperDefineInfo(IObjectPK pk) throws BOSException, EASBizException;
    public QuestionPaperDefineInfo getQuestionPaperDefineInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public QuestionPaperDefineInfo getQuestionPaperDefineInfo(String oql) throws BOSException, EASBizException;
    public QuestionPaperDefineCollection getQuestionPaperDefineCollection() throws BOSException;
    public QuestionPaperDefineCollection getQuestionPaperDefineCollection(EntityViewInfo view) throws BOSException;
    public QuestionPaperDefineCollection getQuestionPaperDefineCollection(String oql) throws BOSException;
    public void action_NewSubject(QuestionPaperDefineInfo model) throws BOSException;
    public void action_DeleteSubject(QuestionPaperDefineInfo model) throws BOSException;
    public void action_SelectSubject(QuestionPaperDefineInfo model) throws BOSException;
    public void action_UpdateSubject(QuestionPaperDefineInfo model) throws BOSException;
}