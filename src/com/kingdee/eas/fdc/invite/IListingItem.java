package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IListingItem extends IFDCBill
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public ListingItemInfo getListingItemInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ListingItemInfo getListingItemInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ListingItemInfo getListingItemInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(ListingItemInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, ListingItemInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, ListingItemInfo model) throws BOSException, EASBizException;
    public void updatePartial(ListingItemInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, ListingItemInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public ListingItemCollection getListingItemCollection() throws BOSException;
    public ListingItemCollection getListingItemCollection(EntityViewInfo view) throws BOSException;
    public ListingItemCollection getListingItemCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public ListingItemCollection getCollectionForUpOrg(BOSUuid currentOrgId, BOSUuid headTypeId) throws BOSException, EASBizException;
    public ListingItemCollection getCollectionForDownOrg(BOSUuid currentOrgId, BOSUuid headTypeId) throws BOSException, EASBizException;
    public ListingItemCollection getCollectionForCurOrg(BOSUuid currentOrgId, BOSUuid headTypeId) throws BOSException, EASBizException;
}