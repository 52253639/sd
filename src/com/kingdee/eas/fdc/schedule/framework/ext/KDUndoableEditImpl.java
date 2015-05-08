package com.kingdee.eas.fdc.schedule.framework.ext;

import java.io.IOException;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import net.sourceforge.ganttproject.undo.GPUndoManager;

/**
 * @author xiaohong_shi
 *
 */
public class KDUndoableEditImpl extends AbstractUndoableEdit {
    private String myPresentationName;

    private Runnable myEditImplRunnable;

    private GPUndoManager myManager;

    public KDUndoableEditImpl(String localizedName, Runnable editRunable,
    		GPUndoManager manager) throws IOException {
        // System.out.println ("UndoableEditImpl : " + localizedName);
        myManager = manager;
        myPresentationName = localizedName;
        myEditImplRunnable = editRunable;
        beforeEdit();
        editRunable.run();
        afterEdit();
    }

    protected void beforeEdit(){
    	
    }
    protected void afterEdit(){
    	
    }


    public boolean canUndo() {
        return true;
    }

    public boolean canRedo() {
    	//TODO 
        return false;
    }

    public void redo() throws CannotRedoException {

    }

    public void undo() throws CannotUndoException {
    	
    }

    public String getPresentationName() {
        return myPresentationName;
    }

}