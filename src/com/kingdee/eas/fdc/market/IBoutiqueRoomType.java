package com.kingdee.eas.fdc.market;

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

public interface IBoutiqueRoomType extends IDataBase
{
    public BoutiqueRoomTypeInfo getBoutiqueRoomTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BoutiqueRoomTypeInfo getBoutiqueRoomTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BoutiqueRoomTypeInfo getBoutiqueRoomTypeInfo(String oql) throws BOSException, EASBizException;
    public BoutiqueRoomTypeCollection getBoutiqueRoomTypeCollection() throws BOSException;
    public BoutiqueRoomTypeCollection getBoutiqueRoomTypeCollection(EntityViewInfo view) throws BOSException;
    public BoutiqueRoomTypeCollection getBoutiqueRoomTypeCollection(String oql) throws BOSException;
}