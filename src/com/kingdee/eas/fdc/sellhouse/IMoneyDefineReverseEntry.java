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

public interface IMoneyDefineReverseEntry extends IObjectBase
{
    public MoneyDefineReverseEntryInfo getMoneyDefineReverseEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MoneyDefineReverseEntryInfo getMoneyDefineReverseEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MoneyDefineReverseEntryInfo getMoneyDefineReverseEntryInfo(String oql) throws BOSException, EASBizException;
    public MoneyDefineReverseEntryCollection getMoneyDefineReverseEntryCollection() throws BOSException;
    public MoneyDefineReverseEntryCollection getMoneyDefineReverseEntryCollection(EntityViewInfo view) throws BOSException;
    public MoneyDefineReverseEntryCollection getMoneyDefineReverseEntryCollection(String oql) throws BOSException;
}