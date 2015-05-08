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

public interface IGlobalTaskNode extends IFDCDataBase
{
    public GlobalTaskNodeInfo getGlobalTaskNodeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public GlobalTaskNodeInfo getGlobalTaskNodeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public GlobalTaskNodeInfo getGlobalTaskNodeInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(GlobalTaskNodeInfo model) throws BOSException, EASBizException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public GlobalTaskNodeCollection getGlobalTaskNodeCollection() throws BOSException;
    public GlobalTaskNodeCollection getGlobalTaskNodeCollection(EntityViewInfo view) throws BOSException;
    public GlobalTaskNodeCollection getGlobalTaskNodeCollection(String oql) throws BOSException;
}