package com.hisun.kugga.duke.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author: zhou_xiong
 */
@Data
public class Content {
    private String id;
    private String type;
    private Data data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Data {
        private String text;
        private Integer level;
        private String style;
        private String caption;
        private String alignment;
        private List<String> items;
        private List<List<String>> content;
        private File file;

        public List<String> getItems() {
            return items;
        }

        public void setItems(List<String> items) {
            this.items = items;
        }

        public List<List<String>> getContent() {
            return content;
        }

        public void setContent(List<List<String>> content) {
            this.content = content;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getAlignment() {
            return alignment;
        }

        public void setAlignment(String alignment) {
            this.alignment = alignment;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }
    }

    public static class File {
        private String url;
        private String caption;
        private Boolean withBorder;
        private Boolean stretched;
        private Boolean withBackground;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public Boolean getWithBorder() {
            return withBorder;
        }

        public void setWithBorder(Boolean withBorder) {
            this.withBorder = withBorder;
        }

        public Boolean getStretched() {
            return stretched;
        }

        public void setStretched(Boolean stretched) {
            this.stretched = stretched;
        }

        public Boolean getWithBackground() {
            return withBackground;
        }

        public void setWithBackground(Boolean withBackground) {
            this.withBackground = withBackground;
        }
    }
}
