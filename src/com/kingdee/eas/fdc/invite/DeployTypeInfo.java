package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class DeployTypeInfo extends AbstractDeployTypeInfo implements Serializable 
{
    public DeployTypeInfo()
    {
        super();
    }
    protected DeployTypeInfo(String pkField)
    {
        super(pkField);
    }
}