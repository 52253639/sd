package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;

public interface ICheckNode extends IFDCDataBase
{
    public CheckNodeInfo getCheckNodeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CheckNodeInfo getCheckNodeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CheckNodeInfo getCheckNodeInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(CheckNodeInfo model) throws BOSException, EASBizException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public CheckNodeCollection getCheckNodeCollection() throws BOSException;
    public CheckNodeCollection getCheckNodeCollection(EntityViewInfo view) throws BOSException;
    public CheckNodeCollection getCheckNodeCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}