package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FirstPayProportionCollection extends AbstractObjectCollection 
{
    public FirstPayProportionCollection()
    {
        super(FirstPayProportionInfo.class);
    }
    public boolean add(FirstPayProportionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FirstPayProportionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FirstPayProportionInfo item)
    {
        return removeObject(item);
    }
    public FirstPayProportionInfo get(int index)
    {
        return(FirstPayProportionInfo)getObject(index);
    }
    public FirstPayProportionInfo get(Object key)
    {
        return(FirstPayProportionInfo)getObject(key);
    }
    public void set(int index, FirstPayProportionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FirstPayProportionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FirstPayProportionInfo item)
    {
        return super.indexOf(item);
    }
}