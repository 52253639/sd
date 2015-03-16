package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.propertymgmt.IPPMProjectDataBase;
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

public interface ICompeteItem extends IPPMProjectDataBase
{
    public CompeteItemInfo getCompeteItemInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CompeteItemInfo getCompeteItemInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CompeteItemInfo getCompeteItemInfo(String oql) throws BOSException, EASBizException;
    public CompeteItemCollection getCompeteItemCollection() throws BOSException;
    public CompeteItemCollection getCompeteItemCollection(EntityViewInfo view) throws BOSException;
    public CompeteItemCollection getCompeteItemCollection(String oql) throws BOSException;
}