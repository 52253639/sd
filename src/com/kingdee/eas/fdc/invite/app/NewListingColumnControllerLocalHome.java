package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListingColumnControllerLocalHome extends EJBLocalHome
{
    NewListingColumnControllerLocal create() throws CreateException;
}