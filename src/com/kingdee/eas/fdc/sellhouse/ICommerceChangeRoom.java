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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;

public interface ICommerceChangeRoom extends ICoreBase
{
    public CommerceChangeRoomInfo getCommerceChangeRoomInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CommerceChangeRoomInfo getCommerceChangeRoomInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CommerceChangeRoomInfo getCommerceChangeRoomInfo(String oql) throws BOSException, EASBizException;
    public CommerceChangeRoomCollection getCommerceChangeRoomCollection() throws BOSException;
    public CommerceChangeRoomCollection getCommerceChangeRoomCollection(EntityViewInfo view) throws BOSException;
    public CommerceChangeRoomCollection getCommerceChangeRoomCollection(String oql) throws BOSException;
}