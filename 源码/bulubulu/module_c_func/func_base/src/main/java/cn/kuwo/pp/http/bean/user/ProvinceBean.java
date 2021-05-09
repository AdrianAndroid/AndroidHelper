package cn.kuwo.pp.http.bean.user;

import java.util.List;

/**
 * @author shihc
 */
public class ProvinceBean {
    private String id;
    private String name;
    private List<CityBean> child;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getChild() {
        return child;
    }

    public void setChild(List<CityBean> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return name;
    }

    static class CityBean {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
