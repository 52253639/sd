package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListTempletValueControllerLocalHome extends EJBLocalHome
{
    NewListTempletValueControllerLocal create() throws CreateException;
}