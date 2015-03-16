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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ICommerceChanceTrack extends IFDCBill
{
    public CommerceChanceTrackCollection getCommerceChanceTrackCollection(String oql) throws BOSException;
    public CommerceChanceTrackCollection getCommerceChanceTrackCollection(EntityViewInfo view) throws BOSException;
    public CommerceChanceTrackCollection getCommerceChanceTrackCollection() throws BOSException;
    public CommerceChanceTrackInfo getCommerceChanceTrackInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CommerceChanceTrackInfo getCommerceChanceTrackInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CommerceChanceTrackInfo getCommerceChanceTrackInfo(String oql) throws BOSException, EASBizException;
}