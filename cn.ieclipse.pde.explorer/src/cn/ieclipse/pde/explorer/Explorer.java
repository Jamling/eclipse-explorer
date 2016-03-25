/*
 * Copyright 2012-2013 Jamling(li.jamling@gmail.com).
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

import java.io.File;

/**
 * Default {@link IExplorable} class
 * 
 * @author Jamling
 *         
 */
public class Explorer implements IExplorable {
    private String folder = null;
    private String file = null;
    
    public Explorer(String folder, String file) {
        if (folder == null && file == null) {
            throw new IllegalArgumentException("both folder and file is null!");
        }
        this.folder = folder;
        if (file != null) {
            this.file = file;
            int pos = file.lastIndexOf(File.separator);
            this.folder = file.substring(0, pos);
            // fix space file name
            if (pos < file.length()) {
                String fn = file.substring(pos + 1);
                if (fn.contains(" ")) {
                    this.file = null;
                }
            }
        }
    }
    
    public Explorer(String file) {
        this(null, file);
    }
    
    @Override
    public <T> T getAdapter(Class<T> adapter) {
        return null;
    }
    
    @Override
    public String getFolder() {
        return folder;
    }
    
    @Override
    public String getFile() {
        return file;
    }
    
}
