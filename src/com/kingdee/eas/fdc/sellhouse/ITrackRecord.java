package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ITrackRecord extends IFDCBill
{
    public TrackRecordInfo getTrackRecordInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TrackRecordInfo getTrackRecordInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TrackRecordInfo getTrackRecordInfo(String oql) throws BOSException, EASBizException;
    public TrackRecordCollection getTrackRecordCollection() throws BOSException;
    public TrackRecordCollection getTrackRecordCollection(EntityViewInfo view) throws BOSException;
    public TrackRecordCollection getTrackRecordCollection(String oql) throws BOSException;
}