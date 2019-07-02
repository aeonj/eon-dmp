import eon.hg.fap.common.ip.IPSeeker;

public class IPtest {
	public static void main(String[] args) {
		// 指定纯真数据库的文件名，所在文件夹
		IPSeeker ip = new IPSeeker("QQWry.Dat", "f:/");
		String temp = "180.97.33.108";
		// 测试IP 58.240.109.294
		System.out.println(ip.getIPLocation(temp).getCountry() + ":"
				+ ip.getIPLocation(temp).getArea());
	}

}
