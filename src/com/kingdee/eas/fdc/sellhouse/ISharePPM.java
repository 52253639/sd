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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ISharePPM extends IDataBase
{
    public SharePPMInfo getSharePPMInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SharePPMInfo getSharePPMInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SharePPMInfo getSharePPMInfo(String oql) throws BOSException, EASBizException;
    public SharePPMCollection getSharePPMCollection() throws BOSException;
    public SharePPMCollection getSharePPMCollection(EntityViewInfo view) throws BOSException;
    public SharePPMCollection getSharePPMCollection(String oql) throws BOSException;
}