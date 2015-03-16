package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ListingPageSumEntryControllerRemoteHome extends EJBHome
{
    ListingPageSumEntryControllerRemote create() throws CreateException, RemoteException;
}