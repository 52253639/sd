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

public interface IAcceptanceLetter extends IFDCBill
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public AcceptanceLetterInfo getAcceptanceLetterInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AcceptanceLetterInfo getAcceptanceLetterInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AcceptanceLetterInfo getAcceptanceLetterInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(AcceptanceLetterInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, AcceptanceLetterInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, AcceptanceLetterInfo model) throws BOSException, EASBizException;
    public void updatePartial(AcceptanceLetterInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, AcceptanceLetterInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public AcceptanceLetterCollection getAcceptanceLetterCollection() throws BOSException;
    public AcceptanceLetterCollection getAcceptanceLetterCollection(EntityViewInfo view) throws BOSException;
    public AcceptanceLetterCollection getAcceptanceLetterCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void setCreateContract(String billId, boolean paramIsCreate) throws BOSException, EASBizException;
    public AcceptanceLetterInfo revise(BOSUuid billId) throws BOSException, FDCInviteException;
}