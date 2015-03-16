package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface LinkmanEntryControllerLocalHome extends EJBLocalHome
{
    LinkmanEntryControllerLocal create() throws CreateException;
}