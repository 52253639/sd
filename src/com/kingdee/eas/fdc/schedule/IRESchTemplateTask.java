package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.List;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.IFDCTreeBaseData;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public interface IRESchTemplateTask extends IFDCTreeBaseData
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public RESchTemplateTaskInfo getRESchTemplateTaskInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RESchTemplateTaskInfo getRESchTemplateTaskInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RESchTemplateTaskInfo getRESchTemplateTaskInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(RESchTemplateTaskInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, RESchTemplateTaskInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, RESchTemplateTaskInfo model) throws BOSException, EASBizException;
    public void updatePartial(RESchTemplateTaskInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, RESchTemplateTaskInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public RESchTemplateTaskCollection getRESchTemplateTaskCollection() throws BOSException;
    public RESchTemplateTaskCollection getRESchTemplateTaskCollection(EntityViewInfo view) throws BOSException;
    public RESchTemplateTaskCollection getRESchTemplateTaskCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void templateTaskDel(List list) throws BOSException, EASBizException;
    public void importTasks(CoreBaseCollection coreBaseCollection, RESchTemplateTaskCollection currentTemplateTaskCollection) throws BOSException, EASBizException;
}