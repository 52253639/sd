package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public interface ISHEProject extends ITreeBase
{
    public SHEProjectInfo getSHEProjectInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SHEProjectInfo getSHEProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SHEProjectInfo getSHEProjectInfo(String oql) throws BOSException, EASBizException;
    public SHEProjectCollection getSHEProjectCollection() throws BOSException;
    public SHEProjectCollection getSHEProjectCollection(EntityViewInfo view) throws BOSException;
    public SHEProjectCollection getSHEProjectCollection(String oql) throws BOSException;
    public void updateRoomModel(List idList, String id) throws BOSException, EASBizException;
    public void deleteRoomModel(List idList) throws BOSException, EASBizException;
    public void updateEnable(BOSUuid id, boolean isEnabled) throws BOSException, EASBizException;
    public void updateSHEProjectToSellProject(BOSUuid id) throws BOSException, EASBizException;
    public void updateSellProjectToSHEProject(String orgId) throws BOSException, EASBizException;
}