package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ListingItemControllerLocalHome extends EJBLocalHome
{
    ListingItemControllerLocal create() throws CreateException;
}