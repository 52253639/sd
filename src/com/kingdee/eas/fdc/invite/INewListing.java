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
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface INewListing extends IFDCBill
{
    public NewListingInfo getNewListingInfo(IObjectPK pk) throws BOSException, EASBizException;
    public NewListingInfo getNewListingInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public NewListingInfo getNewListingInfo(String oql) throws BOSException, EASBizException;
    public NewListingCollection getNewListingCollection() throws BOSException;
    public NewListingCollection getNewListingCollection(EntityViewInfo view) throws BOSException;
    public NewListingCollection getNewListingCollection(String oql) throws BOSException;
    public NewListingInfo getNewListingAllValue(String listingId) throws BOSException, EASBizException;
    public void inviteAuditSubmit(BOSUuid listingId) throws BOSException, EASBizException;
    public void setAuditing(BOSUuid listingId) throws BOSException, EASBizException;
    public void setAudited(BOSUuid listingId) throws BOSException, EASBizException;
    public void setInviteSubmitStatus(BOSUuid listingId) throws BOSException, EASBizException;
    public IObjectValue getAllReversionData(BOSUuid billId) throws BOSException, FDCInviteException;
}