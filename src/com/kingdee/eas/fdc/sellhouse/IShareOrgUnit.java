package com.kingdee.eas.fdc.sellhouse;

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

public interface IShareOrgUnit extends IFDCDataBase
{
    public ShareOrgUnitInfo getShareOrgUnitInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ShareOrgUnitInfo getShareOrgUnitInfo(String oql) throws BOSException, EASBizException;
    public ShareOrgUnitInfo getShareOrgUnitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ShareOrgUnitCollection getShareOrgUnitCollection() throws BOSException;
    public ShareOrgUnitCollection getShareOrgUnitCollection(EntityViewInfo view) throws BOSException;
    public ShareOrgUnitCollection getShareOrgUnitCollection(String oql) throws BOSException;
}