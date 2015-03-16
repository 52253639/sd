package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListTempletControllerLocalHome extends EJBLocalHome
{
    NewListTempletControllerLocal create() throws CreateException;
}