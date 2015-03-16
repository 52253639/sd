package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface TempletTypeControllerLocalHome extends EJBLocalHome
{
    TempletTypeControllerLocal create() throws CreateException;
}