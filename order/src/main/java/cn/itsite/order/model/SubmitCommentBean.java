package cn.itsite.order.model;

import com.bilibili.boxing.model.entity.BaseMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/19 0019 14:58
 *
 * 提交评论的容器
 */
public class SubmitCommentBean {

    private ArrayList<BaseMedia> medias;

    private String imgUrl;

    private int evaLevel;//0-差评；1-中评；2-好评

    private String evaDescription;

    private List<String> files;

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public ArrayList<BaseMedia> getMedias() {
        return medias;
    }

    public void setMedias(ArrayList<BaseMedia> medias) {
        this.medias = medias;
    }

    public int getEvaLevel() {
        return evaLevel;
    }

    public void setEvaLevel(int evaLevel) {
        this.evaLevel = evaLevel;
    }

    public String getEvaDescription() {
        return evaDescription;
    }

    public void setEvaDescription(String evaDescription) {
        this.evaDescription = evaDescription;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
