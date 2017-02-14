package com.tangcco170205_ftp.bean;

/**
 * Created by Administrator on 2017/2/14.
 */

public class AllDataBean {



        /**
         * title : 教会真理
         * actor : 杨培兹
         * director : 基督教会
         * category : 219,36//属于哪个主题
         * movid : 2169
         * click : 15452
         * pic : http://img.tv.ziyuan.jidu.tv:8081/upload/image/movie/20150110/80db3921c0c36377.jpg
         */

        private String title;
        private String actor;
        private String director;
        private String category;
        private int movid;
        private int click;
        private String pic;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getMovid() {
            return movid;
        }

        public void setMovid(int movid) {
            this.movid = movid;
        }

        public int getClick() {
            return click;
        }

        public void setClick(int click) {
            this.click = click;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

}
