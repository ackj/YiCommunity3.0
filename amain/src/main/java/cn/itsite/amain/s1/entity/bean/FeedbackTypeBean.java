package cn.itsite.amain.s1.entity.bean;

import java.util.List;

/**
 * Author： Administrator on 2017/8/10 0010.
 * Email： liujia95me@126.com
 */
public class FeedbackTypeBean {

    /**
     * data : {"types":[{"code":"efefe","name":"投诉"},{"code":"rfrg","name":"意见"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<TypesBean> types;

        public List<TypesBean> getTypes() {
            return types;
        }

        public void setTypes(List<TypesBean> types) {
            this.types = types;
        }

        public static class TypesBean {
            /**
             * code : efefe
             * name : 投诉
             */

            private String code;
            private String name;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
