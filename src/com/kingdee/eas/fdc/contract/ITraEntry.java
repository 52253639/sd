package com.kingdee.eas.fdc.contract;

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

public interface ITraEntry extends ICoreBillEntryBase
{
    public TraEntryInfo getTraEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TraEntryInfo getTraEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TraEntryInfo getTraEntryInfo(String oql) throws BOSException, EASBizException;
    public TraEntryCollection getTraEntryCollection() throws BOSException;
    public TraEntryCollection getTraEntryCollection(EntityViewInfo view) throws BOSException;
    public TraEntryCollection getTraEntryCollection(String oql) throws BOSException;
}