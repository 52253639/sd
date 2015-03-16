package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class JoinDataEntryInfo extends AbstractJoinDataEntryInfo implements Serializable 
{
    public JoinDataEntryInfo()
    {
        super();
    }
    protected JoinDataEntryInfo(String pkField)
    {
        super(pkField);
    }
}