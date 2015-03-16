package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AFMortgagedCollection extends AbstractObjectCollection 
{
    public AFMortgagedCollection()
    {
        super(AFMortgagedInfo.class);
    }
    public boolean add(AFMortgagedInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AFMortgagedCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AFMortgagedInfo item)
    {
        return removeObject(item);
    }
    public AFMortgagedInfo get(int index)
    {
        return(AFMortgagedInfo)getObject(index);
    }
    public AFMortgagedInfo get(Object key)
    {
        return(AFMortgagedInfo)getObject(key);
    }
    public void set(int index, AFMortgagedInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AFMortgagedInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AFMortgagedInfo item)
    {
        return super.indexOf(item);
    }
}