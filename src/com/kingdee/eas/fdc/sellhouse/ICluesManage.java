package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
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

public interface ICluesManage extends IFDCDataBase
{
    public CluesManageInfo getCluesManageInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CluesManageInfo getCluesManageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CluesManageInfo getCluesManageInfo(String oql) throws BOSException, EASBizException;
    public CluesManageCollection getCluesManageCollection() throws BOSException;
    public CluesManageCollection getCluesManageCollection(EntityViewInfo view) throws BOSException;
    public CluesManageCollection getCluesManageCollection(String oql) throws BOSException;
    public void shareClues(CluesManageCollection objectColl, Map map) throws BOSException, EASBizException;
    public void deliverClues(CluesManageInfo model, Map map) throws BOSException, EASBizException;
    public void importClues(CluesManageCollection res) throws BOSException, EASBizException;
    public SHECustomerInfo updateCluesStatus(CluesManageInfo model, String firstLinkMan) throws BOSException, EASBizException;
}