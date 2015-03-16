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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IInviteFileItem extends IFDCBill
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public InviteFileItemInfo getInviteFileItemInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InviteFileItemInfo getInviteFileItemInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InviteFileItemInfo getInviteFileItemInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(InviteFileItemInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, InviteFileItemInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, InviteFileItemInfo model) throws BOSException, EASBizException;
    public void updatePartial(InviteFileItemInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, InviteFileItemInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public InviteFileItemCollection getInviteFileItemCollection() throws BOSException;
    public InviteFileItemCollection getInviteFileItemCollection(EntityViewInfo view) throws BOSException;
    public InviteFileItemCollection getInviteFileItemCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void aduit(IObjectPK pk) throws BOSException, EASBizException;
    public void unAduit(IObjectValue pk) throws BOSException, EASBizException;
    public IObjectValue getAllReversionData(BOSUuid billId) throws BOSException, FDCInviteException;
}