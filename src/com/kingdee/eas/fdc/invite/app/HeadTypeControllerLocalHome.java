package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface HeadTypeControllerLocalHome extends EJBLocalHome
{
    HeadTypeControllerLocal create() throws CreateException;
}