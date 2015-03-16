package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListingValueControllerLocalHome extends EJBLocalHome
{
    NewListingValueControllerLocal create() throws CreateException;
}