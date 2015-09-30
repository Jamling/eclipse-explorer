package cn.ieclipse.pde.explorer;

import org.eclipse.core.runtime.IAdaptable;

public interface IExplorable extends IAdaptable {
    public String getFolder();
    
    public String getFile();
}
