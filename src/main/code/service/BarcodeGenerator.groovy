package service

import io.toro.gloop.annotation.GloopObjectParameter
import io.toro.gloop.annotation.GloopParameter
import io.toro.gloop.object.property.GloopModel
import io.toro.martini.api.APIResponse

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.Writer
import com.google.zxing.common.BitMatrix

import groovy.console.ui.ObjectBrowser

import com.google.zxing.client.j2se.MatrixToImageWriter


/**
 * Generate a barcode from the given text and format name. 
 * For a list of supported formats, please see <a href="https://github.com/zxing/zxing#supported-formats">here</a>.
 *
 * @author charles.turla
 *
 */
class BarcodeGenerator {

    /**
     * Generate a base64 encoded barcode image
     * @param text - String value of the text to encode
     * @param formatName - format name, see https://github.com/zxing/zxing#supported-formats
     * @param width - width of the barcode image, defaults to 100
     * @param height - height of the barcode image, defaults to 100
     */
    @GloopObjectParameter("output{\n  response#io.toro.martini.api.APIResponse*{\n    barcode#model.Barcode{\n    }\n  }\n}\n")
    public static GloopModel generateBarcodeFrom(String text,
                                                 @GloopParameter(choices = ["AZTEC", "CODABAR", "CODE_39", "CODE_93", "CODE_128",
                                                         "DATA_MATRIX", "EAN_8", "EAN_13", "ITF", "MAXICODE", "PDF_417",
                                                         "QR_CODE", "RSS_14", "RSS_EXPANDED", "UPC_A", "UPC_E", "UPC_EAN_EXTENSION"])
                                                         String formatName,
                                                 @GloopParameter(defaultValue = "100") int width,
                                                 @GloopParameter(defaultValue = "100") int height) {

        try {
            //identify the com.google.zxing.BarcodeFormat object from the given name
            BarcodeFormat barcodeFormat = identifyFormat(formatName);

            Writer writer = new MultiFormatWriter()
            BitMatrix matrix = writer.encode(text, barcodeFormat, width, height)
            ByteArrayOutputStream baos = new ByteArrayOutputStream()
            MatrixToImageWriter.writeToStream(matrix, 'jpg', baos)
            String base64EncodedImage = Base64.getEncoder().encodeToString(baos.toByteArray())

            //map com.torocloud.model.Barcode properties
            GloopModel barcodeModel = new GloopModel('barcode')
            barcodeModel.setReference('model.Barcode')
            barcodeModel.put('text', text)
            barcodeModel.put('format', barcodeFormat.name())
            barcodeModel.put('image', base64EncodedImage)

            //map io.toro.martinit.api.APIResponse properties
            GloopModel response = new GloopModel('response')
            response.setReference('io.toro.martini.api.APIResponse')
            response.setAllowExtraProperties(true)
            response.put('message', "Successfully created ${barcodeFormat.name()}")
            response.addChild(barcodeModel)

            def output = new GloopModel('output')
            output.addChild(response)

            return output
        } catch (Exception ex) {
            ex.printStackTrace()

            GloopModel response = new GloopModel('response')
            response.setReference('io.toro.martini.api.APIResponse')
            response.setAllowExtraProperties(true)
            response.put('result', 'error')
            response.put('message', 'Failed to create barcode image')

            def output = new GloopModel('output')
            output.addChild(response)
            return output
        }
    }

    private static BarcodeFormat identifyFormat(String formatName) {
        if (BarcodeFormat.AZTEC.name() == formatName) {
            return BarcodeFormat.AZTEC
        } else if (BarcodeFormat.CODABAR.name() == formatName) {
            return BarcodeFormat.CODABAR
        } else if (BarcodeFormat.CODE_39.name() == formatName) {
            return BarcodeFormat.CODE_39
        } else if (BarcodeFormat.CODE_93.name() == formatName) {
            return BarcodeFormat.CODE_93
        } else if (BarcodeFormat.CODE_128.name() == formatName) {
            return BarcodeFormat.CODE_128
        } else if (BarcodeFormat.DATA_MATRIX.name() == formatName) {
            return BarcodeFormat.DATA_MATRIX
        } else if (BarcodeFormat.EAN_8.name() == formatName) {
            return BarcodeFormat.EAN_8
        } else if (BarcodeFormat.EAN_13.name() == formatName) {
            return BarcodeFormat.EAN_13
        } else if (BarcodeFormat.ITF.name() == formatName) {
            return BarcodeFormat.ITF
        } else if (BarcodeFormat.MAXICODE.name() == formatName) {
            return BarcodeFormat.MAXICODE
        } else if (BarcodeFormat.PDF_417.name() == formatName) {
            return BarcodeFormat.PDF_417
        } else if (BarcodeFormat.QR_CODE.name() == formatName) {
            return BarcodeFormat.QR_CODE
        } else if (BarcodeFormat.RSS_14.name() == formatName) {
            return BarcodeFormat.RSS_14
        } else if (BarcodeFormat.RSS_EXPANDED.name() == formatName) {
            return BarcodeFormat.RSS_EXPANDED
        } else if (BarcodeFormat.UPC_A.name() == formatName) {
            return BarcodeFormat.UPC_A
        } else if (BarcodeFormat.UPC_E.name() == formatName) {
            return BarcodeFormat.UPC_E
        } else if (BarcodeFormat.UPC_EAN_EXTENSION.name() == formatName) {
            return BarcodeFormat.UPC_EAN_EXTENSION
        } else {
            return null
        }
    }

}
