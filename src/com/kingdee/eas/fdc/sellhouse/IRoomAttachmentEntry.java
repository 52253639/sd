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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IRoomAttachmentEntry extends IDataBase
{
    public RoomAttachmentEntryInfo getRoomAttachmentEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RoomAttachmentEntryInfo getRoomAttachmentEntryInfo(String oql) throws BOSException, EASBizException;
    public RoomAttachmentEntryInfo getRoomAttachmentEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RoomAttachmentEntryCollection getRoomAttachmentEntryCollection() throws BOSException;
    public RoomAttachmentEntryCollection getRoomAttachmentEntryCollection(EntityViewInfo view) throws BOSException;
    public RoomAttachmentEntryCollection getRoomAttachmentEntryCollection(String oql) throws BOSException;
}