package cn.itsite.acommon.data.bean;

import java.io.Serializable;

/**
 * Created by liujia on 03/05/2018.
 */

public class UserInfoBean implements Serializable{

    /**
     * isSuccess : true
     * status : true
     * memberInfo : {"id":"237ac0dc-24c3-45e1-b675-6dfb6144063e","mobile":"135****9720","email":"","sex":0,"qq":"","nickName":"uirrv0913","point":0,"money":"0","token":"tk_a772c0a3-da88-488a-a1e0-7a7881d76811","level":0,"levelName":"","grade":0,"gradeName":""}
     */

    private boolean isSuccess;
    private boolean status;
    private MemberInfoBean memberInfo;

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public MemberInfoBean getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfoBean memberInfo) {
        this.memberInfo = memberInfo;
    }

    public static class MemberInfoBean {
        /**
         * id : 237ac0dc-24c3-45e1-b675-6dfb6144063e
         * mobile : 135****9720
         * email :
         * sex : 0
         * qq :
         * nickName : uirrv0913
         * point : 0
         * money : 0
         * token : tk_a772c0a3-da88-488a-a1e0-7a7881d76811
         * level : 0
         * levelName :
         * grade : 0
         * gradeName :
         */

        private String icon;
        private String id;
        private String mobile;
        private String email;
        private int sex;
        private String qq;
        private String nickName;
        private int point;
        private String money;
        private String token;
        private int level;
        private String levelName;
        private int grade;
        private String gradeName;
        private String face;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }
    }
}
