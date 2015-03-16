package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MaterialSampleEntrysCollection extends AbstractObjectCollection 
{
    public MaterialSampleEntrysCollection()
    {
        super(MaterialSampleEntrysInfo.class);
    }
    public boolean add(MaterialSampleEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MaterialSampleEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MaterialSampleEntrysInfo item)
    {
        return removeObject(item);
    }
    public MaterialSampleEntrysInfo get(int index)
    {
        return(MaterialSampleEntrysInfo)getObject(index);
    }
    public MaterialSampleEntrysInfo get(Object key)
    {
        return(MaterialSampleEntrysInfo)getObject(key);
    }
    public void set(int index, MaterialSampleEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MaterialSampleEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MaterialSampleEntrysInfo item)
    {
        return super.indexOf(item);
    }
}