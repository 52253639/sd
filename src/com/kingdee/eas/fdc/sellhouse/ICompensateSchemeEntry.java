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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface ICompensateSchemeEntry extends ICoreBillEntryBase
{
    public CompensateSchemeEntryInfo getCompensateSchemeEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CompensateSchemeEntryInfo getCompensateSchemeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CompensateSchemeEntryInfo getCompensateSchemeEntryInfo(String oql) throws BOSException, EASBizException;
    public CompensateSchemeEntryCollection getCompensateSchemeEntryCollection() throws BOSException;
    public CompensateSchemeEntryCollection getCompensateSchemeEntryCollection(EntityViewInfo view) throws BOSException;
    public CompensateSchemeEntryCollection getCompensateSchemeEntryCollection(String oql) throws BOSException;
}