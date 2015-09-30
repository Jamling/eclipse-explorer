package cn.ieclipse.pde.explorer;

import java.io.File;

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
            this.folder = file.substring(0, file.lastIndexOf(File.separator));
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
