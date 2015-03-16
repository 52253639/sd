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
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IDirectAccepterResult extends IBaseInvite
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public DirectAccepterResultInfo getDirectAccepterResultInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DirectAccepterResultInfo getDirectAccepterResultInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DirectAccepterResultInfo getDirectAccepterResultInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(DirectAccepterResultInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, DirectAccepterResultInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, DirectAccepterResultInfo model) throws BOSException, EASBizException;
    public void updatePartial(DirectAccepterResultInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, DirectAccepterResultInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public DirectAccepterResultCollection getDirectAccepterResultCollection() throws BOSException;
    public DirectAccepterResultCollection getDirectAccepterResultCollection(EntityViewInfo view) throws BOSException;
    public DirectAccepterResultCollection getDirectAccepterResultCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}