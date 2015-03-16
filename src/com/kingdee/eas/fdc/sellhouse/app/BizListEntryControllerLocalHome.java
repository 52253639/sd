package com.kingdee.eas.fdc.sellhouse.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BizListEntryControllerLocalHome extends EJBLocalHome
{
    BizListEntryControllerLocal create() throws CreateException;
}