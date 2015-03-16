package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RefPriceControllerLocalHome extends EJBLocalHome
{
    RefPriceControllerLocal create() throws CreateException;
}