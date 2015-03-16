package com.kingdee.eas.fdc.sellhouse;

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

public interface IShareRoomModels extends IFDCDataBase
{
    public ShareRoomModelsInfo getShareRoomModelsInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ShareRoomModelsInfo getShareRoomModelsInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ShareRoomModelsInfo getShareRoomModelsInfo(String oql) throws BOSException, EASBizException;
    public ShareRoomModelsCollection getShareRoomModelsCollection() throws BOSException;
    public ShareRoomModelsCollection getShareRoomModelsCollection(EntityViewInfo view) throws BOSException;
    public ShareRoomModelsCollection getShareRoomModelsCollection(String oql) throws BOSException;
}