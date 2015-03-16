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

public interface IQuaLevel extends IFDCDataBase
{
    public QuaLevelInfo getQuaLevelInfo(IObjectPK pk) throws BOSException, EASBizException;
    public QuaLevelInfo getQuaLevelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public QuaLevelInfo getQuaLevelInfo(String oql) throws BOSException, EASBizException;
    public QuaLevelCollection getQuaLevelCollection() throws BOSException;
    public QuaLevelCollection getQuaLevelCollection(EntityViewInfo view) throws BOSException;
    public QuaLevelCollection getQuaLevelCollection(String oql) throws BOSException;
}