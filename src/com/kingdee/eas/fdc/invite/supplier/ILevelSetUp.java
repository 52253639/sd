package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
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

public interface ILevelSetUp extends IFDCDataBase
{
    public LevelSetUpInfo getLevelSetUpInfo(IObjectPK pk) throws BOSException, EASBizException;
    public LevelSetUpInfo getLevelSetUpInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public LevelSetUpInfo getLevelSetUpInfo(String oql) throws BOSException, EASBizException;
    public LevelSetUpCollection getLevelSetUpCollection() throws BOSException;
    public LevelSetUpCollection getLevelSetUpCollection(EntityViewInfo view) throws BOSException;
    public LevelSetUpCollection getLevelSetUpCollection(String oql) throws BOSException;
}