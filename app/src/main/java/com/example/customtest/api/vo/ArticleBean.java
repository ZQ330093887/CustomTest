package com.example.customtest.api.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZQiong on 2018/9/30.
 */
public class ArticleBean implements Serializable {

    /**
     * code : 0
     * msg : success
     * data : [{"id":62,"title":"中秋节由来","summary":"一片关于中秋节由来的文章","traffic":5,"createBy":"2018-09-24 12:25:15","categoryId":2,"categoryName":"中秋","articlePictureId":62,"pictureUrl":"./while.jpg","top":false},{"id":61,"title":"中秋节","summary":"白居易 异乡文章","traffic":2,"createBy":"2018-09-24 12:12:53","categoryId":1,"categoryName":"散文","articlePictureId":61,"pictureUrl":"./while.jpg","top":false}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 62
         * title : 中秋节由来
         * summary : 一片关于中秋节由来的文章
         * traffic : 5
         * createBy : 2018-09-24 12:25:15
         * categoryId : 2
         * categoryName : 中秋
         * articlePictureId : 62
         * pictureUrl : ./while.jpg
         * top : false
         */

        private int id;
        private String title;
        private String summary;
        private int traffic;
        private String createBy;
        private int categoryId;
        private String categoryName;
        private int articlePictureId;
        private String pictureUrl;
        private boolean top;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getTraffic() {
            return traffic;
        }

        public void setTraffic(int traffic) {
            this.traffic = traffic;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getArticlePictureId() {
            return articlePictureId;
        }

        public void setArticlePictureId(int articlePictureId) {
            this.articlePictureId = articlePictureId;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        public boolean isTop() {
            return top;
        }

        public void setTop(boolean top) {
            this.top = top;
        }
    }
}
