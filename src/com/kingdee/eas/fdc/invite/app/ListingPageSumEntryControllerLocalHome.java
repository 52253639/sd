package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ListingPageSumEntryControllerLocalHome extends EJBLocalHome
{
    ListingPageSumEntryControllerLocal create() throws CreateException;
}