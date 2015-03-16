package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IDeployType extends IFDCDataBase
{
    public DeployTypeInfo getDeployTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DeployTypeInfo getDeployTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DeployTypeInfo getDeployTypeInfo(String oql) throws BOSException, EASBizException;
    public DeployTypeCollection getDeployTypeCollection() throws BOSException;
    public DeployTypeCollection getDeployTypeCollection(EntityViewInfo view) throws BOSException;
    public DeployTypeCollection getDeployTypeCollection(String oql) throws BOSException;
    public boolean updateRelateData(String oldID, String newID, Object[] tables) throws BOSException;
    public Object[] getRelateData(String id, String[] tables) throws BOSException;
}