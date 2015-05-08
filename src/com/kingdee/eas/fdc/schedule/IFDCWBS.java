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
import java.lang.Object;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.IFDCTreeBaseData;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IFDCWBS extends IFDCTreeBaseData
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public FDCWBSInfo getFDCWBSInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCWBSInfo getFDCWBSInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCWBSInfo getFDCWBSInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(FDCWBSInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, FDCWBSInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, FDCWBSInfo model) throws BOSException, EASBizException;
    public void updatePartial(FDCWBSInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, FDCWBSInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public FDCWBSCollection getFDCWBSCollection() throws BOSException;
    public FDCWBSCollection getFDCWBSCollection(EntityViewInfo view) throws BOSException;
    public FDCWBSCollection getFDCWBSCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void importWBSTemplate(Map param) throws BOSException, ScheduleException, EASBizException;
    public void batChangeTaskPro(Set wbsIDs, String taskProID) throws BOSException, EASBizException;
    public void batChangeAdminDept(Set wbsIDs, String adminDeptID) throws BOSException, EASBizException;
    public void batChangeAdminPerson(Set wbsIds, String adminPersonID) throws BOSException, EASBizException;
    public void batChangeRespDept(Set wbsIds, String respDeptId) throws BOSException, EASBizException;
    public Map getTemplateFromFDCWBS(String prjId) throws BOSException, EASBizException;
    public Map getTaskWBSByProjectId(String projectId) throws BOSException, EASBizException;
    public void reCalculateNumber(Object wbsAdjustManager) throws BOSException, EASBizException;
    public void handleCancleCancle(Map paramMap) throws BOSException, EASBizException;
    public Map saveOrderWBS(FDCWBSTree tree, boolean isRetMap) throws BOSException, EASBizException;
}