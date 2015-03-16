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

public interface ICollection extends IFDCDataBase
{
    public CollectionInfo getCollectionInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CollectionInfo getCollectionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CollectionInfo getCollectionInfo(String oql) throws BOSException, EASBizException;
    public CollectionCollection getCollectionCollection() throws BOSException;
    public CollectionCollection getCollectionCollection(EntityViewInfo view) throws BOSException;
    public CollectionCollection getCollectionCollection(String oql) throws BOSException;
    public void enable(IObjectPK pk) throws BOSException, EASBizException;
    public void disEnable(IObjectPK pk) throws BOSException, EASBizException;
    public Map getArAmout(Map map) throws BOSException;
}