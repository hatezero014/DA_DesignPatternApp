package com.example.designpattern.Share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFUtil {

    public static void createPDF(Context context, TextView textView, String fileName) {
        if (textView == null || TextUtils.isEmpty(textView.getText())) {
            Toast.makeText(context, "TextView is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(textView.getWidth(), textView.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        textView.draw(canvas);
        document.finishPage(page);

        File pdfFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);

        try {
            document.writeTo(new FileOutputStream(pdfFile));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to create PDF", Toast.LENGTH_SHORT).show();
            return;
        }

        document.close();

        // Share PDF
        sharePDF(context, pdfFile);
    }

    private static void sharePDF(Context context, File pdfFile) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, "com.example.designpattern.Share.fileprovider", pdfFile));
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(shareIntent, "Share PDF"));
    }
}
