package com.example.cardiaryclient.network.utils

import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.example.cardiaryclient.utils.Constants
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadUtility(private var activity: Activity) {

    private var dialog: ProgressDialog? = null
    private var serverURL: String = "${Constants.carServiceBaseurl}/cars/1/photos"
    private val client = OkHttpClient()

    fun uploadFile(sourceFileUri: Uri, uploadFileName: String? = null) {
        val pathFromUri = URIPathHelper().getPath(activity, sourceFileUri)
        uploadFile(File(pathFromUri!!), uploadFileName)
    }

    fun uploadFile(sourceFile: File, uploadFileName: String? = null) {
        Thread {
            val mimeType = getMimeType(sourceFile)
            if (mimeType == null) {
                Log.e("file error", "Not able to get mime type")
                return@Thread
            }

            val fileName: String = uploadFileName ?: sourceFile.name
            toggleProgressDialog(true)
            try {
                val requestBody: RequestBody =
                    MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("photo", fileName, sourceFile.asRequestBody(mimeType.toMediaTypeOrNull()))
                        .build()

                val request: Request = Request.Builder()
                    .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZXdvazk5MDkyQGdyaWRtaXJlLmNvbSIsImF1ZCI6ImNhci1kaWFyeS1hcHAiLCJzdWJfaWQiOjQsInJvbGVzIjpbImFkbWluIiwibWFuYWdlciJdLCJpc3MiOiJjYXItZGlhcnktYXBpIiwiZXhwIjoxNjIyNjQwOTkyLCJpYXQiOjE2MjE3NzY5OTIsImp0aSI6IjkyYWMzMGY2LTc5YjQtNDVmNi1iYmY3LTQ0MWVkM2I3MjQ5MiJ9.74H_1vvCi8vbqIj3Pysu4flQT9Qgy2hPM63do7f0h8x6E92xfcRfmnoBrSP9ewXQJfDp3e7GhncRuN5WsY7aeg")
                    .url(serverURL)
                    .patch(requestBody)
                    .build()

                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    Log.d("File upload", "Success for uploading $fileName")
                    showToast("File $fileName uploaded successfully!")
                } else {
                    Log.e("File upload", "Failed uploading $fileName. Reason: ${response.message}")
                    showToast("Fail to upload $fileName")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e("File upload", "failed")
                showToast("File uploading failed")
            }
            toggleProgressDialog(false)
        }.start()
    }

    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    private fun showToast(message: String) {
        activity.runOnUiThread {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun toggleProgressDialog(show: Boolean) {
        activity.runOnUiThread {
            if (show) {
                dialog = ProgressDialog.show(activity, "", "Uploading file...", true)
            } else {
                dialog?.dismiss()
            }
        }
    }
}