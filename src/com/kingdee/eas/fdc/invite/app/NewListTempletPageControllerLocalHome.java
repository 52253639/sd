package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListTempletPageControllerLocalHome extends EJBLocalHome
{
    NewListTempletPageControllerLocal create() throws CreateException;
}