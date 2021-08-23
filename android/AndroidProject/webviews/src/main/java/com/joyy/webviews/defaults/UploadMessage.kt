package com.joyy.webviews.defaults

import android.content.Intent

/**
 * Time:2021/8/23 14:10
 * Author: flannery
 * Description:
 */
object UploadMessage {
//    private val UPLOAD_START_CODE = 2001
//    var uploadMessage: ValueCallback<Uri>? = null
//    val uploadMessageAboveL: ValueCallback<Array<Uri>>? = null

    fun openImageChooserActivity(acceptType: Array<String>) =
        Intent(Intent.ACTION_GET_CONTENT)
            .addCategory(Intent.CATEGORY_OPENABLE)
            .setType("*/*")
            .putExtra(Intent.EXTRA_MIME_TYPES, acceptType)

    fun openImageChooserActivity(acceptType: String) =
        Intent.createChooser(
            Intent(Intent.ACTION_GET_CONTENT).addCategory(Intent.CATEGORY_OPENABLE)
                .setType(acceptType),
            "Image Chooser"
        )

    /*

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null == uploadMessage && null == uploadMessageAboveL) {
            return;
        }
        Uri result = (data == null || resultCode != RESULT_OK) ? null : data.getData();
        if (uploadMessageAboveL != null) {
            onActivityResultAboveL(requestCode, resultCode, data);
        } else if (uploadMessage != null) {
            uploadMessage.onReceiveValue(result);
            uploadMessage = null;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != UploadMessage.UPLOAD_START_CODE || uploadMessageAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }
     */
}