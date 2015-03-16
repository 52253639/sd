package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.framework.CoreBaseCollection;

public class TranRoomAttachmentEntry extends DataBase implements ITranRoomAttachmentEntry
{
    public TranRoomAttachmentEntry()
    {
        super();
        registerInterface(ITranRoomAttachmentEntry.class, this);
    }
    public TranRoomAttachmentEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITranRoomAttachmentEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("95A68C4E");
    }
    private TranRoomAttachmentEntryController getController() throws BOSException
    {
        return (TranRoomAttachmentEntryController)getBizController();
    }
}