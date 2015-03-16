package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListingControllerLocalHome extends EJBLocalHome
{
    NewListingControllerLocal create() throws CreateException;
}