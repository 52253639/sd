package com.kingdee.eas.fdc.invite;

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

public interface IDrawingDepth extends IFDCDataBase
{
    public DrawingDepthInfo getDrawingDepthInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DrawingDepthInfo getDrawingDepthInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DrawingDepthInfo getDrawingDepthInfo(String oql) throws BOSException, EASBizException;
    public DrawingDepthCollection getDrawingDepthCollection() throws BOSException;
    public DrawingDepthCollection getDrawingDepthCollection(EntityViewInfo view) throws BOSException;
    public DrawingDepthCollection getDrawingDepthCollection(String oql) throws BOSException;
    public boolean updateRelateData(String oldID, String newID, Object[] tables) throws BOSException;
    public Object[] getRelateData(String id, String[] tables) throws BOSException;
}