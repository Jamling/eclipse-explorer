/*
 * Copyright 2015 Jamling(li.jamling@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ieclipse.pde.explorer;

import org.eclipse.core.runtime.IAdapterFactory;

/**
 * Abstract adapter factory to adapt non-explorerable object to
 * {@link IExplorable}.
 * <p>
 * Simple extends this class and overwrite the abstract
 * {@link #getExplorable(Object)}
 * </p>
 * <p>
 * then configure your adapter adapter in plugin.xml
 * 
 * <pre>
 *  &lt;extension
         point="org.eclipse.core.runtime.adapters"&gt;
      &lt;factory
            adaptableType="org.eclipse.jdt.core.IJavaElement"
            class="cn.ieclipse.pde.explorer.java.JavaAdapterFactory"&gt;
         &lt;adapter
               type="cn.ieclipse.pde.explorer.IExplorable"&gt;
         &lt;/adapter&gt;
      &lt;/factory&gt;
   &lt;/extension&gt;
 * </pre>
 * </p>
 * 
 * @author Jamling
 *         
 */
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
    
    /**
     * Adapt non-explorable object to {@link IExplorable}
     * 
     * @param adaptableObject
     *            adaptable object
     * @return adapted {@link IExplorable} object.
     */
    public abstract IExplorable getExplorable(Object adaptableObject);
}
