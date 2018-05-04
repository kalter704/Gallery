package com.yad.vasilii.gallery.domain.models;

public class Image {

    private final String mPreviewImageUrl;

    private final String mLargeImageUrl;

    public Image(String previewImageUrl, String largeImageUrl) {
        mPreviewImageUrl = previewImageUrl;
        mLargeImageUrl = largeImageUrl;
    }

    public String getPreviewImageUrl() {
        return mPreviewImageUrl;
    }

    public String getLargeImageUrl() {
        return mLargeImageUrl;
    }

    public static class Builder {

        private String previewImageUrl;

        private String largeImageUrl;

        public Image build() {
            return new Image(previewImageUrl, largeImageUrl);
        }

        public Builder withPreviewImageUrl(String url) {
            previewImageUrl = url;
            return this;
        }

        public Builder withLargeImageUrl(String url) {
            largeImageUrl = url;
            return this;
        }

    }
}
