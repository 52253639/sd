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

public interface IHopedFloor extends IFDCDataBase
{
    public HopedFloorInfo getHopedFloorInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public HopedFloorInfo getHopedFloorInfo(IObjectPK pk) throws BOSException, EASBizException;
    public HopedFloorInfo getHopedFloorInfo(String oql) throws BOSException, EASBizException;
    public HopedFloorCollection getHopedFloorCollection() throws BOSException;
    public HopedFloorCollection getHopedFloorCollection(EntityViewInfo view) throws BOSException;
    public HopedFloorCollection getHopedFloorCollection(String oql) throws BOSException;
}