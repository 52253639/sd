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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IJoinDoScheme extends IFDCDataBase
{
    public JoinDoSchemeInfo getJoinDoSchemeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public JoinDoSchemeInfo getJoinDoSchemeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public JoinDoSchemeInfo getJoinDoSchemeInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public JoinDoSchemeCollection getJoinDoSchemeCollection() throws BOSException;
    public JoinDoSchemeCollection getJoinDoSchemeCollection(EntityViewInfo view) throws BOSException;
    public JoinDoSchemeCollection getJoinDoSchemeCollection(String oql) throws BOSException;
    public void setEnabled(List idList, boolean isEnabled) throws BOSException, EASBizException;
}