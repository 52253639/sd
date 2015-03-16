package com.kingdee.eas.fdc.basecrm;

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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.framework.ICoreBase;

public interface IFDCOrgStructure extends ICoreBase
{
    public FDCOrgStructureInfo getFDCOrgStructureInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCOrgStructureInfo getFDCOrgStructureInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCOrgStructureInfo getFDCOrgStructureInfo(String oql) throws BOSException, EASBizException;
    public FDCOrgStructureCollection getFDCOrgStructureCollection() throws BOSException;
    public FDCOrgStructureCollection getFDCOrgStructureCollection(EntityViewInfo view) throws BOSException;
    public FDCOrgStructureCollection getFDCOrgStructureCollection(String oql) throws BOSException;
    public void updateData() throws BOSException, EASBizException;
    public void saveData(List idList) throws BOSException, EASBizException;
}