# Barcode-Generator
An API that allows you to generate different barcode images for the given text.

### Prerequisites

  - Apache Maven 3+
  - [Martini Desktop](https://www.torocloud.com/martini/download)

### Building the Martini Package

```
$ mvn clean package
```
This will create a ZIP file named `barcode-generator.zip` containing all the files (services, configurations, etc.) needed under the `target` folder. 
This ZIP file is what we call a [Martini Package](https://docs.torocloud.com/martini/latest/developing/package/) 
which then you can import into Martini Desktop to get started. You can learn more about how to import a Martini Package 
by visiting our [documentation](https://docs.torocloud.com/martini/latest/developing/package/importing/).

### Usage
This package exposes REST APIs for creating barcode image and listing all supported formats.
You can find the [Gloop REST API](https://docs.torocloud.com/martini/latest/developing/gloop/api/rest/) file 
at `/code/api/BarcodeGeneratorAPI.api` after importing the package to your Martini Desktop application.

### Operations

The base url is `<host>/api/barcode` where `host` is the location where the Martini instance is deployed. By default, it's `localhost:8080`.

`POST /generate`

Generates a barcode image for the given text and barcode format and returns a base64 encoded image. 

**Request Body**

```json
{
    "text": "sample",
    "barcodeFormat": "QR_CODE",
    "width": "100",
    "height": "100"
}
```
* `text` - the text to encode as barcode
* `barcodeFormat` - the barcode format
* `width` - the width of the barcode image
* `height` - the height of the barcode image

**Sample Request**

**cURL**
```
curl --location --request POST 'http://localhost:8080/api/barcode/generate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "text": "hello-world",
    "barcodeFormat": "QR_CODE",
    "width": "100",
    "height": "100"
}'
```

**Sample Response**

```
{
    "result": "OK",
    "message": "Successfully created QR_CODE",
    "warnings": [],
    "barcode": {
        "text": "sample",
        "format": "QR_CODE",
        "image": "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"
    }
}
```

`GET /formats`

Returns a list of supported barcode formats.

**Sample Request**

**curl**
```
curl --location --request GET 'http://localhost:8080/api/barcode/formats'
```

**Response**

```json
[
    "AZTEC",
    "CODABAR",
    "CODE_39",
    "CODE_93",
    "CODE_128",
    "DATA_MATRIX",
    "EAN_8",
    "EAN_13",
    "ITF",
    "MAXICODE",
    "PDF_417",
    "QR_CODE",
    "RSS_14",
    "RSS_EXPANDED",
    "UPC_A",
    "UPC_E",
    "UPC_EAN_EXTENSION"
]
```
