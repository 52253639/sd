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
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.base.permission.UserInfo;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

public interface ISellProject extends ITreeBase
{
    public SellProjectInfo getSellProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SellProjectInfo getSellProjectInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SellProjectInfo getSellProjectInfo(String oql) throws BOSException, EASBizException;
    public SellProjectCollection getSellProjectCollection() throws BOSException;
    public SellProjectCollection getSellProjectCollection(EntityViewInfo view) throws BOSException;
    public SellProjectCollection getSellProjectCollection(String oql) throws BOSException;
    public SellProjectInfo getBaseValue(String idStr) throws BOSException;
    public SellProjectCollection getBaseCollection(Set idSet) throws BOSException;
    public boolean endInit(List projectIds, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException;
    public boolean unInit(List projectIds, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException;
    public boolean allEndInit(String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException;
    public boolean allUnInit(String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException;
    public void nextSystem(String comId, UserInfo userInfo) throws BOSException, EASBizException;
    public void preSystem(String comId) throws BOSException, EASBizException;
    public void projectDataUpdate(IObjectValue model) throws BOSException, EASBizException;
    public void allProjectDataUpdate() throws BOSException, EASBizException;
    public void updateToSHEProject(BOSUuid id, BOSUuid orgUnitID, String longNumber) throws BOSException, EASBizException;
    public void updateToSellProject(BOSUuid id, String number, String name) throws BOSException, EASBizException;
    public ArrayList getSellProTreeNodes(String type) throws BOSException, EASBizException;
    public void updateDeleteStatus(BOSUuid billId) throws BOSException, EASBizException;
    public void deleteSellProject(BOSUuid billId) throws BOSException, EASBizException;
    public void deleteProjectInSystem(BOSUuid billId) throws BOSException, EASBizException;
    public void updateRoomModelForChild(BOSUuid billId, List roomModelList) throws BOSException, EASBizException;
}