package com.kingdee.eas.fdc.basecrm;

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

public interface ISubstituteAdjustEntry extends ICoreBillEntryBase
{
    public SubstituteAdjustEntryInfo getSubstituteAdjustEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SubstituteAdjustEntryInfo getSubstituteAdjustEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SubstituteAdjustEntryInfo getSubstituteAdjustEntryInfo(String oql) throws BOSException, EASBizException;
    public SubstituteAdjustEntryCollection getSubstituteAdjustEntryCollection() throws BOSException;
    public SubstituteAdjustEntryCollection getSubstituteAdjustEntryCollection(EntityViewInfo view) throws BOSException;
    public SubstituteAdjustEntryCollection getSubstituteAdjustEntryCollection(String oql) throws BOSException;
}