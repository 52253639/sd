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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IBoutiqueRoomTypeTree extends ITreeBase
{
    public BoutiqueRoomTypeTreeInfo getBoutiqueRoomTypeTreeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BoutiqueRoomTypeTreeInfo getBoutiqueRoomTypeTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BoutiqueRoomTypeTreeInfo getBoutiqueRoomTypeTreeInfo(String oql) throws BOSException, EASBizException;
    public BoutiqueRoomTypeTreeCollection getBoutiqueRoomTypeTreeCollection() throws BOSException;
    public BoutiqueRoomTypeTreeCollection getBoutiqueRoomTypeTreeCollection(EntityViewInfo view) throws BOSException;
    public BoutiqueRoomTypeTreeCollection getBoutiqueRoomTypeTreeCollection(String oql) throws BOSException;
}