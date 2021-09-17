package closure

import bean.ProductFlavor

class Android {
    private int mCompileSdkVersion
    private String mBuildToolsVersion
    private ProductFlavor mProductFlavor

    Android() {
        this.mProductFlavor = new ProductFlavor()
    }

    void compileSdkVersion(int compileSdkVersion) {
        this.mCompileSdkVersion = compileSdkVersion
    }

    void buildToolsVersion(String buldToolsVersion) {
        this.mBuildToolsVersion = buldToolsVersion
    }

    void defaultConfig(Closure closure) {
        closure.setDelegate(mProductFlavor)
        closure.setResolveStrategy(Closure.DELEGATE_FIRST)
        closure.call()
    }


    @Override
    public String toString() {
        return "Android{" +
                "mCompileSdkVersion=" + mCompileSdkVersion +
                ", mBuildToolsVersion='" + mBuildToolsVersion + '\'' +
                ", mProductFlavor=" + mProductFlavor +
                '}';
    }
}
