package com.kingdee.eas.fdc.market.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.BillBaseController;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface QuestionPaperAnswerController extends BillBaseController
{
    public QuestionPaperAnswerInfo getQuestionPaperAnswerInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public QuestionPaperAnswerInfo getQuestionPaperAnswerInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public QuestionPaperAnswerInfo getQuestionPaperAnswerInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public QuestionPaperAnswerCollection getQuestionPaperAnswerCollection(Context ctx) throws BOSException, RemoteException;
    public QuestionPaperAnswerCollection getQuestionPaperAnswerCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public QuestionPaperAnswerCollection getQuestionPaperAnswerCollection(Context ctx, String oql) throws BOSException, RemoteException;
}