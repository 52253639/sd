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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IQuestionBase extends IDataBase
{
    public QuestionBaseInfo getQuestionBaseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public QuestionBaseInfo getQuestionBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public QuestionBaseInfo getQuestionBaseInfo(String oql) throws BOSException, EASBizException;
    public QuestionBaseCollection getQuestionBaseCollection() throws BOSException;
    public QuestionBaseCollection getQuestionBaseCollection(EntityViewInfo view) throws BOSException;
    public QuestionBaseCollection getQuestionBaseCollection(String oql) throws BOSException;
}