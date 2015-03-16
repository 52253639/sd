package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface InviteTypeEntryControllerRemoteHome extends EJBHome
{
    InviteTypeEntryControllerRemote create() throws CreateException, RemoteException;
}