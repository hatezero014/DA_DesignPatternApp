package com.example.designpattern.Share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.github.kbiakov.codeview.CodeView;

public class PDFUtil {

    public static void createPDF(Context context, CodeView codeView, String fileName) {
        String code = stripHtml(codeView.getOptions().getCode());
        if (code == null || code.isEmpty()) {
            Toast.makeText(context, "CodeView is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        PdfDocument document = new PdfDocument();

        Paint paint = new Paint();
        paint.setTextSize(12);
        paint.setTypeface(Typeface.MONOSPACE);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float lineHeight = fontMetrics.descent - fontMetrics.ascent;

        int pageHeight = 1120;
        int pageWidth = 792;
        int linesPerPage = (int) (pageHeight / lineHeight);

        String[] lines = code.split("\n");
        int totalPages = (int) Math.ceil((double) lines.length / linesPerPage);

        for (int i = 0; i < totalPages; i++) {
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i + 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();

            int lineNumber = i * linesPerPage;
            int endLine = Math.min(lineNumber + linesPerPage, lines.length);

            for (int j = lineNumber; j < endLine; j++) {
                canvas.drawText(lines[j], 10, (j - lineNumber) * lineHeight - fontMetrics.ascent + 10, paint);
            }

            document.finishPage(page);
        }

        File pdfFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);

        try {
            document.writeTo(new FileOutputStream(pdfFile));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to create PDF", Toast.LENGTH_SHORT).show();
            return;
        }

        document.close();

        sharePDF(context, pdfFile);
    }

    private static void sharePDF(Context context, File pdfFile) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, "com.example.designpattern.Share.fileprovider", pdfFile));
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(shareIntent, "Share PDF"));
    }

    private static String stripHtml(String html) {
        return html.replaceAll("<[^>]*>", "");
    }
}
