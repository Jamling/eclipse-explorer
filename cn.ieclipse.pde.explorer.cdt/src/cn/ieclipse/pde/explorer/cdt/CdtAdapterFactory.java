/*
 * Copyright 2014-2015 ieclipse.cn.
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
package cn.ieclipse.pde.explorer.cdt;

import org.eclipse.cdt.core.model.ICContainer;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.IInclude;
import org.eclipse.core.resources.IResource;

import cn.ieclipse.pde.explorer.AdapterFactory;
import cn.ieclipse.pde.explorer.Explorer;
import cn.ieclipse.pde.explorer.IExplorable;

/**
 * Adapter factory for c/c++ model
 * 
 * @author Jamling
 *         
 */
public class CdtAdapterFactory extends AdapterFactory {
    
    @Override
    public IExplorable getExplorable(Object adaptableObject) {
        String path = null;
        if (adaptableObject instanceof ICProject) {
            ICProject cele = (ICProject) adaptableObject;
            path = cele.getProject().getLocation().toOSString();
            return new Explorer(path, null);
        }
        else if (adaptableObject instanceof ICContainer) {
            ICContainer cele = (ICContainer) adaptableObject;
            path = cele.getResource().getLocation().toOSString();
            return new Explorer(path, null);
        }
        else if (adaptableObject instanceof IInclude) {
            IInclude cele = (IInclude) adaptableObject;
            path = cele.getParent().getResource().getLocation().toOSString();
            return new Explorer(null, path);
        }
        else if (adaptableObject instanceof ICElement) {
            ICElement cele = (ICElement) adaptableObject;
            IResource res = cele.getResource();
            if (res == null) {
                res = cele.getAdapter(IResource.class);
            }
            if (res != null) {
                path = res.getLocation().toOSString();
                return new Explorer(null, path);
            }
        }
        return null;
    }
    
}
