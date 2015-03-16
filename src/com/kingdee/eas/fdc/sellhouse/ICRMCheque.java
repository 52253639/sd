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
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.framework.ICoreBillBase;

public interface ICRMCheque extends ICoreBillBase
{
    public CRMChequeInfo getCRMChequeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CRMChequeInfo getCRMChequeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CRMChequeInfo getCRMChequeInfo(String oql) throws BOSException, EASBizException;
    public CRMChequeCollection getCRMChequeCollection() throws BOSException;
    public CRMChequeCollection getCRMChequeCollection(EntityViewInfo view) throws BOSException;
    public CRMChequeCollection getCRMChequeCollection(String oql) throws BOSException;
    public void addBatch(CRMChequeCollection model) throws BOSException, EASBizException;
    public void vc(Map ids) throws BOSException, EASBizException;
    public void abandon(List ids) throws BOSException, EASBizException;
    public void distribute(String[] ids, String newKeepOrgUnitId, String newKeeperId) throws BOSException, EASBizException;
    public void pickCheque(Map dataMap) throws BOSException;
}