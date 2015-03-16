package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface InviteTypeControllerLocalHome extends EJBLocalHome
{
    InviteTypeControllerLocal create() throws CreateException;
}