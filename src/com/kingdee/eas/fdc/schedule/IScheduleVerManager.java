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
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IScheduleVerManager extends IBillBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public ScheduleVerManagerInfo getScheduleVerManagerInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ScheduleVerManagerInfo getScheduleVerManagerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ScheduleVerManagerInfo getScheduleVerManagerInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(ScheduleVerManagerInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, ScheduleVerManagerInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, ScheduleVerManagerInfo model) throws BOSException, EASBizException;
    public void updatePartial(ScheduleVerManagerInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, ScheduleVerManagerInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public ScheduleVerManagerCollection getScheduleVerManagerCollection() throws BOSException;
    public ScheduleVerManagerCollection getScheduleVerManagerCollection(EntityViewInfo view) throws BOSException;
    public ScheduleVerManagerCollection getScheduleVerManagerCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public FDCScheduleInfo getNewVerData(String prjId) throws BOSException, EASBizException;
    public FDCScheduleInfo getVerData(String prjId, float version) throws BOSException, EASBizException;
    public FDCScheduleInfo getVerData(String verId) throws BOSException, EASBizException;
    public ScheduleVerManagerCollection getVerData(Map paramMap) throws BOSException, EASBizException;
    public IObjectPK createNewVer(String prjId) throws BOSException, EASBizException;
    public void audit(BOSUuid billId) throws BOSException, EASBizException;
    public void unAudit(BOSUuid billId) throws BOSException, EASBizException;
    public void setAudittingStatus(BOSUuid billId) throws BOSException, EASBizException;
    public void setSubmitStatus(BOSUuid billId) throws BOSException, EASBizException;
}