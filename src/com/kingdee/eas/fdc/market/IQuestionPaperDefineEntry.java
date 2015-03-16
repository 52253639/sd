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

public interface IQuestionPaperDefineEntry extends IBillEntryBase
{
    public QuestionPaperDefineEntryInfo getQuestionPaperDefineEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public QuestionPaperDefineEntryInfo getQuestionPaperDefineEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public QuestionPaperDefineEntryInfo getQuestionPaperDefineEntryInfo(String oql) throws BOSException, EASBizException;
    public QuestionPaperDefineEntryCollection getQuestionPaperDefineEntryCollection() throws BOSException;
    public QuestionPaperDefineEntryCollection getQuestionPaperDefineEntryCollection(EntityViewInfo view) throws BOSException;
    public QuestionPaperDefineEntryCollection getQuestionPaperDefineEntryCollection(String oql) throws BOSException;
}