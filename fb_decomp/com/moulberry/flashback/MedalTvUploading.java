/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  net.minecraft.util.Util
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.client.methods.CloseableHttpResponse
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpPut
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.entity.ContentType
 *  org.apache.http.entity.StringEntity
 *  org.apache.http.impl.client.CloseableHttpClient
 *  org.apache.http.impl.client.HttpClients
 *  org.apache.http.message.BasicHeader
 *  org.apache.http.util.EntityUtils
 *  org.jetbrains.annotations.Nullable
 */
package com.moulberry.flashback;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.moulberry.flashback.Flashback;
import com.moulberry.flashback.exporting.ExportSettings;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import net.minecraft.util.Util;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.Nullable;

public class MedalTvUploading {
    @Nullable
    public static CannotUploadReason checkCanUpload(ExportSettings exportSettings) {
        switch (exportSettings.container()) {
            case MP4: 
            case MKV: 
            case WEBP: 
            case GIF: {
                break;
            }
            default: {
                return CannotUploadReason.UNSUPPORTED_FORMAT;
            }
        }
        try {
            Path output = exportSettings.output();
            if (!Files.exists(output, new LinkOption[0]) || !Files.isRegularFile(output, new LinkOption[0])) {
                return CannotUploadReason.FILE_MISSING;
            }
            long size = Files.size(output);
            if (size <= 0L) {
                return CannotUploadReason.ERROR_CHECKING_FILE;
            }
            if (size > 2000000000L) {
                return CannotUploadReason.OVER_2GB;
            }
        }
        catch (IOException e) {
            return CannotUploadReason.ERROR_CHECKING_FILE;
        }
        return null;
    }

    public static UploadStatus upload(ExportSettings exportSettings) {
        byte[] bytes;
        UploadStatus uploadStatus = new UploadStatus();
        try {
            bytes = Files.readAllBytes(exportSettings.output());
        }
        catch (IOException e) {
            uploadStatus.throwable = e;
            uploadStatus.finished = true;
            return uploadStatus;
        }
        String title = exportSettings.name();
        if (title == null) {
            title = exportSettings.output().getFileName().toString();
        }
        String titleF = title;
        Util.ioPool().execute(() -> {
            try (CloseableHttpClient httpClient = HttpClients.custom().setUserAgent("Flashback (+https://modrinth.com/mod/flashback)").build();){
                Gson gson = new Gson();
                String uploadUrl = MedalTvUploading.createUploadRequest(gson, httpClient, uploadStatus, titleF, bytes.length, exportSettings);
                if (uploadUrl == null || uploadStatus.shouldCancel) {
                    return;
                }
                MedalTvUploading.sendContent(httpClient, uploadStatus, uploadUrl, bytes, exportSettings);
            }
            catch (Throwable t) {
                uploadStatus.throwable = t;
            }
            finally {
                uploadStatus.finished = true;
            }
        });
        return uploadStatus;
    }

    @Nullable
    private static String createUploadRequest(Gson gson, CloseableHttpClient httpClient, UploadStatus uploadStatus, String title, long length, ExportSettings exportSettings) throws IOException, URISyntaxException {
        JsonObject resultJson;
        HttpPost post = new HttpPost("https://medal.tv/flashback-upload");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("contentLength", (Number)length);
        jsonObject.addProperty("contentType", exportSettings.container().mimeType());
        jsonObject.addProperty("contentTitle", title);
        String requestContent = gson.toJson((JsonElement)jsonObject);
        post.setEntity((HttpEntity)new StringEntity(requestContent, ContentType.APPLICATION_JSON));
        CloseableHttpResponse response = httpClient.execute((HttpUriRequest)post);
        try {
            String resultStr = EntityUtils.toString((HttpEntity)response.getEntity(), (String)"UTF-8");
            resultJson = (JsonObject)gson.fromJson(resultStr, JsonObject.class);
        }
        catch (JsonSyntaxException e) {
            uploadStatus.errorMessage = "FlashbackUpload did not return a valid JSON response";
            return null;
        }
        if (resultJson.has("error")) {
            Flashback.LOGGER.error("Error while creating medal upload request: {}", (Object)resultJson);
            uploadStatus.errorMessage = "FlashbackUpload Error: " + resultJson.get("error").getAsString();
            return null;
        }
        if (!resultJson.has("shareUrl")) {
            Flashback.LOGGER.error("Incomplete response from medal upload request: {}", (Object)resultJson);
            uploadStatus.errorMessage = "FlashbackUpload Error: Missing shareUrl";
            return null;
        }
        if (!resultJson.has("signedUrl")) {
            Flashback.LOGGER.error("Incomplete response from medal upload request: {}", (Object)resultJson);
            uploadStatus.errorMessage = "FlashbackUpload Error: Missing signedUrl";
            return null;
        }
        uploadStatus.progressPercentage = 10;
        uploadStatus.shareUrl = Util.parseAndValidateUntrustedUri((String)resultJson.get("shareUrl").getAsString());
        return resultJson.get("signedUrl").getAsString();
    }

    private static void sendContent(CloseableHttpClient httpClient, final UploadStatus uploadStatus, String contentUrl, final byte[] data, ExportSettings exportSettings) throws IOException {
        HttpPut put = new HttpPut(contentUrl);
        final String contentType = exportSettings.container().mimeType();
        put.setEntity(new HttpEntity(){

            public boolean isRepeatable() {
                return false;
            }

            public boolean isChunked() {
                return false;
            }

            public long getContentLength() {
                return data.length;
            }

            public Header getContentType() {
                return new BasicHeader("Content-Type", contentType);
            }

            public Header getContentEncoding() {
                return null;
            }

            public InputStream getContent() {
                return new ByteArrayInputStream(data);
            }

            public void writeTo(OutputStream outputStream) throws IOException {
                int minChunkSize = 1000000;
                if (uploadStatus.shouldCancel) {
                    throw new RuntimeException("Upload cancelled");
                }
                int position = 0;
                int limit = data.length;
                while (uploadStatus.progressPercentage < 89) {
                    int remainingProgress = 90 - uploadStatus.progressPercentage;
                    int fractionalBytes = (limit - position + (remainingProgress - 1)) / remainingProgress;
                    int toWrite = Math.min(limit - position, Math.max(1000000, fractionalBytes));
                    outputStream.write(data, position, toWrite);
                    uploadStatus.progressPercentage = (int)(10L + 80L * (long)(position += toWrite) / (long)limit);
                }
                if (position < limit) {
                    outputStream.write(data, position, limit - position);
                    uploadStatus.progressPercentage = 90;
                }
            }

            public boolean isStreaming() {
                return false;
            }

            public void consumeContent() {
            }
        });
        CloseableHttpResponse response = httpClient.execute((HttpUriRequest)put);
        if (response.getStatusLine().getStatusCode() != 200) {
            Flashback.LOGGER.error("Error response from Google Cloud Storage: {}", (Object)EntityUtils.toString((HttpEntity)response.getEntity()));
            uploadStatus.errorMessage = "SendContent: Error while sending content to Google Cloud Storage";
            return;
        }
        uploadStatus.progressPercentage = 100;
    }

    public static enum CannotUploadReason {
        UNSUPPORTED_FORMAT,
        FILE_MISSING,
        ERROR_CHECKING_FILE,
        OVER_2GB;

    }

    public static class UploadStatus {
        public long startTime = System.currentTimeMillis();
        public int progressPercentage = 0;
        public boolean finished = false;
        public boolean shouldCancel = false;
        public String errorMessage = null;
        public Throwable throwable = null;
        public URI shareUrl = null;
    }
}

