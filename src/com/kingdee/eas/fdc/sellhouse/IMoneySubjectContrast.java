package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.framework.IObjectBase;

public interface IMoneySubjectContrast extends IObjectBase
{
    public MoneySubjectContrastInfo getMoneySubjectContrastInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MoneySubjectContrastInfo getMoneySubjectContrastInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MoneySubjectContrastInfo getMoneySubjectContrastInfo(String oql) throws BOSException, EASBizException;
    public MoneySubjectContrastCollection getMoneySubjectContrastCollection() throws BOSException;
    public MoneySubjectContrastCollection getMoneySubjectContrastCollection(EntityViewInfo view) throws BOSException;
    public MoneySubjectContrastCollection getMoneySubjectContrastCollection(String oql) throws BOSException;
}