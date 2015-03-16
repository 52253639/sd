package com.kingdee.eas.fdc.invite.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface NewListTempletColumnControllerLocalHome extends EJBLocalHome
{
    NewListTempletColumnControllerLocal create() throws CreateException;
}