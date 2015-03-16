package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface InviteTypeEntryControllerLocalHome extends EJBLocalHome
{
    InviteTypeEntryControllerLocal create() throws CreateException;
}