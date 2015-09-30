package cn.ieclipse.pde.explorer;

import org.eclipse.core.runtime.IAdapterFactory;

public abstract class AdapterFactory implements IAdapterFactory {
    
    @Override
    public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
        if (adapterType == IExplorable.class) {
            return (T) getExplorable(adaptableObject);
        }
        return null;
    }
    
    @Override
    public Class<?>[] getAdapterList() {
        return new Class<?>[] { IExplorable.class };
    }
    
    public abstract IExplorable getExplorable(Object adaptableObject);
}
