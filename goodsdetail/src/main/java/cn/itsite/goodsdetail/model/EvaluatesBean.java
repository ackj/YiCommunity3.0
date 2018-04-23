package cn.itsite.goodsdetail.model;

import java.io.Serializable;
import java.util.List;

public class EvaluatesBean implements Serializable {

        /**
         * member : {"name":"Óã·ÇÓã","icon":"http://aglhzmall.image.alimmdn.com/member/20180104091724544143.jpg","grade":0,"gradeN":"","level":0,"levelN":""}
         * productDes : dafa
         * evaTime : 2018-04-19
         * evaDes : goodgood
         * evaPictures : ["http://aglhzmall.image.alimmdn.com/goods/20170406091521850464.jpg","http://aglhzmall.image.alimmdn.com/goods/20170406091521850464.jpg"]
         */

        private MemberBean member;
        private String productDes;
        private String evaTime;
        private String evaDes;
        private int evaLevel;
        private List<String> evaPictures;

    public int getEvaLevel() {
        return evaLevel;
    }

    public void setEvaLevel(int evaLevel) {
        this.evaLevel = evaLevel;
    }

    public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public String getProductDes() {
            return productDes;
        }

        public void setProductDes(String productDes) {
            this.productDes = productDes;
        }

        public String getEvaTime() {
            return evaTime;
        }

        public void setEvaTime(String evaTime) {
            this.evaTime = evaTime;
        }

        public String getEvaDes() {
            return evaDes;
        }

        public void setEvaDes(String evaDes) {
            this.evaDes = evaDes;
        }

        public List<String> getEvaPictures() {
            return evaPictures;
        }

        public void setEvaPictures(List<String> evaPictures) {
            this.evaPictures = evaPictures;
        }

        public static class MemberBean implements Serializable{
            /**
             * name : Óã·ÇÓã
             * icon : http://aglhzmall.image.alimmdn.com/member/20180104091724544143.jpg
             * grade : 0
             * gradeN :
             * level : 0
             * levelN :
             */

            private String name;
            private String icon;
            private int grade;
            private String gradeN;
            private int level;
            private String levelN;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getGrade() {
                return grade;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }

            public String getGradeN() {
                return gradeN;
            }

            public void setGradeN(String gradeN) {
                this.gradeN = gradeN;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getLevelN() {
                return levelN;
            }

            public void setLevelN(String levelN) {
                this.levelN = levelN;
            }
        }
    }