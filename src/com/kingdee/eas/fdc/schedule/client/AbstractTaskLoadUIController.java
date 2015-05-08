/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import org.apache.log4j.Logger;
import com.kingdee.bos.appframework.uip.ControllerBase;
import com.kingdee.bos.appframework.uip.Navigator;
import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * output class name
 */
public class AbstractTaskLoadUIController extends ControllerBase {
    private static final Logger logger = CoreUIObject.getLogger(AbstractTaskLoadUIController.class);
    
    /**
     * output class constructor
     */
    public AbstractTaskLoadUIController(Navigator navigator) {
        super(navigator);
    }

    public void actionSave() throws Exception {
    	
    	this.getNavigator().setNextUIName(null);
		
    	this.getNavigator().setOprtState(null);
		
        this.getNavigator().setUIContext(((AbstractTaskLoadUI)this.getNavigator().getOwner()).getUIContext());
        
        this.beforeActionSaveNavigate();        
        this.getNavigator().navigate();
        this.afterActionSaveNavigate();                
    }
    
    protected void beforeActionSaveNavigate() throws Exception {
    }
    
    protected void afterActionSaveNavigate() throws Exception {
    }
    public void actionAddRow() throws Exception {
    	
    	this.getNavigator().setNextUIName(null);
		
    	this.getNavigator().setOprtState(null);
		
        this.getNavigator().setUIContext(((AbstractTaskLoadUI)this.getNavigator().getOwner()).getUIContext());
        
        this.beforeActionAddRowNavigate();        
        this.getNavigator().navigate();
        this.afterActionAddRowNavigate();                
    }
    
    protected void beforeActionAddRowNavigate() throws Exception {
    }
    
    protected void afterActionAddRowNavigate() throws Exception {
    }
    public void actionDeleteRow() throws Exception {
    	
    	this.getNavigator().setNextUIName(null);
		
    	this.getNavigator().setOprtState(null);
		
        this.getNavigator().setUIContext(((AbstractTaskLoadUI)this.getNavigator().getOwner()).getUIContext());
        
        this.beforeActionDeleteRowNavigate();        
        this.getNavigator().navigate();
        this.afterActionDeleteRowNavigate();                
    }
    
    protected void beforeActionDeleteRowNavigate() throws Exception {
    }
    
    protected void afterActionDeleteRowNavigate() throws Exception {
    }
    public void actionEntryAttachment() throws Exception {
    	
    	this.getNavigator().setNextUIName(null);
		
    	this.getNavigator().setOprtState(null);
		
        this.getNavigator().setUIContext(((AbstractTaskLoadUI)this.getNavigator().getOwner()).getUIContext());
        
        this.beforeActionEntryAttachmentNavigate();        
        this.getNavigator().navigate();
        this.afterActionEntryAttachmentNavigate();                
    }
    
    protected void beforeActionEntryAttachmentNavigate() throws Exception {
    }
    
    protected void afterActionEntryAttachmentNavigate() throws Exception {
    }
}