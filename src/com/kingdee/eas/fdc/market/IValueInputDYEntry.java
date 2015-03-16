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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IValueInputDYEntry extends ICoreBillEntryBase
{
    public ValueInputDYEntryInfo getValueInputDYEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ValueInputDYEntryInfo getValueInputDYEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ValueInputDYEntryInfo getValueInputDYEntryInfo(String oql) throws BOSException, EASBizException;
    public ValueInputDYEntryCollection getValueInputDYEntryCollection() throws BOSException;
    public ValueInputDYEntryCollection getValueInputDYEntryCollection(EntityViewInfo view) throws BOSException;
    public ValueInputDYEntryCollection getValueInputDYEntryCollection(String oql) throws BOSException;
}