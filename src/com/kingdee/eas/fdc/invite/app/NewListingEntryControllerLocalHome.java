package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListingEntryControllerLocalHome extends EJBLocalHome
{
    NewListingEntryControllerLocal create() throws CreateException;
}