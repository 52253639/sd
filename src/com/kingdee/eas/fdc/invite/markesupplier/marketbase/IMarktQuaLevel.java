package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

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

public interface IMarktQuaLevel extends IFDCDataBase
{
    public MarktQuaLevelInfo getMarktQuaLevelInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarktQuaLevelInfo getMarktQuaLevelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarktQuaLevelInfo getMarktQuaLevelInfo(String oql) throws BOSException, EASBizException;
    public MarktQuaLevelCollection getMarktQuaLevelCollection() throws BOSException;
    public MarktQuaLevelCollection getMarktQuaLevelCollection(EntityViewInfo view) throws BOSException;
    public MarktQuaLevelCollection getMarktQuaLevelCollection(String oql) throws BOSException;
}