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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IWorkArea extends ITreeBase
{
    public WorkAreaInfo getWorkAreaInfo(IObjectPK pk) throws BOSException, EASBizException;
    public WorkAreaInfo getWorkAreaInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public WorkAreaInfo getWorkAreaInfo(String oql) throws BOSException, EASBizException;
    public WorkAreaCollection getWorkAreaCollection() throws BOSException;
    public WorkAreaCollection getWorkAreaCollection(EntityViewInfo view) throws BOSException;
    public WorkAreaCollection getWorkAreaCollection(String oql) throws BOSException;
}