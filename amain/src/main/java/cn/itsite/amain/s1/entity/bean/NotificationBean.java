package cn.itsite.amain.s1.entity.bean;

/**
 * Created by leguang on 2017/8/11 0011.
 * Email：langmanleguang@qq.com
 */

public class NotificationBean {

    /**
     * msg_id : uudds09150460963012700
     * body : {"play_sound":"true","play_vibrate":"true","play_lights":"true","after_open":"go_app","title":"主机布防状态变更通知","text":"主机布防由在家布防变更为撤防状态","ticker":"主机布防状态变更通知"}
     * random_min : 0
     * display_type : notification
     * extra : {"ctype":"gw_defense_satus","cstatus":"-1","cidx":"-1"}
     */

    private String msg_id;
    private BodyBean body;
    private int random_min;
    private String display_type;
    private ExtraBean extra;

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getRandom_min() {
        return random_min;
    }

    public void setRandom_min(int random_min) {
        this.random_min = random_min;
    }

    public String getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(String display_type) {
        this.display_type = display_type;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public static class BodyBean {
        /**
         * play_sound : true
         * play_vibrate : true
         * play_lights : true
         * after_open : go_app
         * title : 主机布防状态变更通知
         * text : 主机布防由在家布防变更为撤防状态
         * ticker : 主机布防状态变更通知
         */

        private String play_sound;
        private String play_vibrate;
        private String play_lights;
        private String after_open;
        private String title;
        private String text;
        private String ticker;

        public String getPlay_sound() {
            return play_sound;
        }

        public void setPlay_sound(String play_sound) {
            this.play_sound = play_sound;
        }

        public String getPlay_vibrate() {
            return play_vibrate;
        }

        public void setPlay_vibrate(String play_vibrate) {
            this.play_vibrate = play_vibrate;
        }

        public String getPlay_lights() {
            return play_lights;
        }

        public void setPlay_lights(String play_lights) {
            this.play_lights = play_lights;
        }

        public String getAfter_open() {
            return after_open;
        }

        public void setAfter_open(String after_open) {
            this.after_open = after_open;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }
    }

    public static class ExtraBean {
        /**
         * ctype : gw_defense_satus
         * cstatus : -1
         * cidx : -1
         */

        private String ctype;
        private String cstatus;
        private String cidx;

        public String getCtype() {
            return ctype;
        }

        public void setCtype(String ctype) {
            this.ctype = ctype;
        }

        public String getCstatus() {
            return cstatus;
        }

        public void setCstatus(String cstatus) {
            this.cstatus = cstatus;
        }

        public String getCidx() {
            return cidx;
        }

        public void setCidx(String cidx) {
            this.cidx = cidx;
        }
    }
}
