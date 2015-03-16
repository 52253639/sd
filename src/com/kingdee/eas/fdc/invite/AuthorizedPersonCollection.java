package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AuthorizedPersonCollection extends AbstractObjectCollection 
{
    public AuthorizedPersonCollection()
    {
        super(AuthorizedPersonInfo.class);
    }
    public boolean add(AuthorizedPersonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AuthorizedPersonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AuthorizedPersonInfo item)
    {
        return removeObject(item);
    }
    public AuthorizedPersonInfo get(int index)
    {
        return(AuthorizedPersonInfo)getObject(index);
    }
    public AuthorizedPersonInfo get(Object key)
    {
        return(AuthorizedPersonInfo)getObject(key);
    }
    public void set(int index, AuthorizedPersonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AuthorizedPersonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AuthorizedPersonInfo item)
    {
        return super.indexOf(item);
    }
}