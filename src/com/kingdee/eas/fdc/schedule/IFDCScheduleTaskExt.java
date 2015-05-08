package com.kingdee.eas.fdc.schedule;

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
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public interface IFDCScheduleTaskExt extends ICoreBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public FDCScheduleTaskExtInfo getFDCScheduleTaskExtInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCScheduleTaskExtInfo getFDCScheduleTaskExtInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCScheduleTaskExtInfo getFDCScheduleTaskExtInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(FDCScheduleTaskExtInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, FDCScheduleTaskExtInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, FDCScheduleTaskExtInfo model) throws BOSException, EASBizException;
    public void updatePartial(FDCScheduleTaskExtInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, FDCScheduleTaskExtInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public FDCScheduleTaskExtCollection getFDCScheduleTaskExtCollection() throws BOSException;
    public FDCScheduleTaskExtCollection getFDCScheduleTaskExtCollection(EntityViewInfo view) throws BOSException;
    public FDCScheduleTaskExtCollection getFDCScheduleTaskExtCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void deletePropByWBSID(String wbsID) throws BOSException, EASBizException;
    public void deletePropByTaskExtID(String taskExtID) throws BOSException, EASBizException;
    public Map getProByWBSID(String WBSID) throws BOSException, EASBizException;
    public Map getProByTaskExtID(String taskExtID) throws BOSException, EASBizException;
    public void updateExeProp(Map extProperties) throws BOSException, EASBizException;
    public void updateCompleProp(Map extProperties) throws BOSException, EASBizException;
    public void saveExeProp(Map exeExtProperties) throws BOSException, EASBizException;
    public void saveCompileProp(Map exeCompileProperties) throws BOSException, EASBizException;
    public void deletePropByWBSIDs(Set wbsIDs) throws BOSException, EASBizException;
    public void deletePropByTaskExtIDs(Set wbsIDs) throws BOSException, EASBizException;
    public void copyFromAdjuestBill(String oldWBSID, String newWBSID) throws BOSException, EASBizException;
}