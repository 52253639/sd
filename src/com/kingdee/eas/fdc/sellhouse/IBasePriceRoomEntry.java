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

public interface IBasePriceRoomEntry extends IDataBase
{
    public BasePriceRoomEntryInfo getBasePriceRoomEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BasePriceRoomEntryInfo getBasePriceRoomEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BasePriceRoomEntryInfo getBasePriceRoomEntryInfo(String oql) throws BOSException, EASBizException;
    public BasePriceRoomEntryCollection getBasePriceRoomEntryCollection() throws BOSException;
    public BasePriceRoomEntryCollection getBasePriceRoomEntryCollection(EntityViewInfo view) throws BOSException;
    public BasePriceRoomEntryCollection getBasePriceRoomEntryCollection(String oql) throws BOSException;
}