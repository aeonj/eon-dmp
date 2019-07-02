import eon.hg.fap.common.qrcode.QRCodeUtil;

public class TestQRCode {
    public static void main(String[] args) throws Exception {
        String text = "http://www.aeon.com";
        QRCodeUtil.encode(text, "d:/100_100.png", "d:/123.png", true);
        // System.out.println(QRCodeUtil.decode("d:/122.png"));
    }
}
