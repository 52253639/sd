package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;

public class SignRoomAttachmentEntry extends TranRoomAttachmentEntry implements ISignRoomAttachmentEntry
{
    public SignRoomAttachmentEntry()
    {
        super();
        registerInterface(ISignRoomAttachmentEntry.class, this);
    }
    public SignRoomAttachmentEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISignRoomAttachmentEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DC57247C");
    }
    private SignRoomAttachmentEntryController getController() throws BOSException
    {
        return (SignRoomAttachmentEntryController)getBizController();
    }
}