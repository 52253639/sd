package com.kingdee.eas.fdc.aimcost.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CostSplitControllerLocalHome extends EJBLocalHome
{
    CostSplitControllerLocal create() throws CreateException;
}