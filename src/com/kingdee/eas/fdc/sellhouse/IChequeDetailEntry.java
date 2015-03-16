package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.Object;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.eas.framework.ICoreBase;

public interface IChequeDetailEntry extends ICoreBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public ChequeDetailEntryInfo getChequeDetailEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ChequeDetailEntryInfo getChequeDetailEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ChequeDetailEntryInfo getChequeDetailEntryInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(ChequeDetailEntryInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, ChequeDetailEntryInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, ChequeDetailEntryInfo model) throws BOSException, EASBizException;
    public void updatePartial(ChequeDetailEntryInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, ChequeDetailEntryInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public ChequeDetailEntryCollection getChequeDetailEntryCollection() throws BOSException;
    public ChequeDetailEntryCollection getChequeDetailEntryCollection(EntityViewInfo view) throws BOSException;
    public ChequeDetailEntryCollection getChequeDetailEntryCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void reBack(String id) throws BOSException, EASBizException;
    public void cancelInvoice(String id) throws BOSException, EASBizException;
    public void invalid(String id) throws BOSException, EASBizException;
    public void changeInvoice(String oldId, String newId, String desc) throws BOSException, EASBizException;
    public void changeInvoice(ArrayList oldIds, String newId, String desc) throws BOSException, EASBizException;
    public boolean clearInvoice(String id, boolean isRevBillId) throws BOSException, EASBizException;
    public boolean clearInvoice(Object[] idArray, boolean isRevBill) throws BOSException, EASBizException;
    public void dealForSheRevBillCheque(SHERevBillInfo newSheRevBillInfo) throws BOSException, EASBizException;
}