package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListTempletEntryControllerLocalHome extends EJBLocalHome
{
    NewListTempletEntryControllerLocal create() throws CreateException;
}