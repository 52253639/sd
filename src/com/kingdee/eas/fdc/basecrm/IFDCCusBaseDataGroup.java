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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IFDCCusBaseDataGroup extends ITreeBase
{
    public FDCCusBaseDataGroupInfo getFDCCusBaseDataGroupInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCCusBaseDataGroupInfo getFDCCusBaseDataGroupInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCCusBaseDataGroupInfo getFDCCusBaseDataGroupInfo(String oql) throws BOSException, EASBizException;
    public FDCCusBaseDataGroupCollection getFDCCusBaseDataGroupCollection() throws BOSException;
    public FDCCusBaseDataGroupCollection getFDCCusBaseDataGroupCollection(EntityViewInfo view) throws BOSException;
    public FDCCusBaseDataGroupCollection getFDCCusBaseDataGroupCollection(String oql) throws BOSException;
}