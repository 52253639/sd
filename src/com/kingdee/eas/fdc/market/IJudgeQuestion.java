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

public interface IJudgeQuestion extends IQuestionBase
{
    public JudgeQuestionInfo getJudgeQuestionInfo(IObjectPK pk) throws BOSException, EASBizException;
    public JudgeQuestionInfo getJudgeQuestionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public JudgeQuestionInfo getJudgeQuestionInfo(String oql) throws BOSException, EASBizException;
    public JudgeQuestionCollection getJudgeQuestionCollection() throws BOSException;
    public JudgeQuestionCollection getJudgeQuestionCollection(EntityViewInfo view) throws BOSException;
    public JudgeQuestionCollection getJudgeQuestionCollection(String oql) throws BOSException;
}